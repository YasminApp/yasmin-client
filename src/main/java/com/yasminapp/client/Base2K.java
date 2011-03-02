package com.yasminapp.client;


public class Base2K {
  // See http://twuuenc.atxconsulting.com/index.htm

  private static final char[] CHARSET;
  private static final int[] INDEXES;
  static {
    CHARSET = concat(
      range((char) 0x20, (char) 0x7E),
      range((char) 0xA1, (char) 0xFF),
      range((char) 0x100, (char) 0x17F),
      range((char) 0x180, (char) 0x24F),
      range((char) 0x250, (char) 0x2AE),
      range((char) 0x2B0, (char) 0x2FE),
      range((char) 0x390, (char) 0x3CE),
      range((char) 0x3D0, (char) 0x3FF),
      range((char) 0x400, (char) 0x4FF),
      range((char) 0x1401, (char) 0x1676),
      range((char) 0x2580, (char) 0x259F),
      range((char) 0x25A0, (char) 0x25FF),
      range((char) 0x2701, (char) 0x27BE),
      range((char) 0x27C0, (char) 0x27EB),
      range((char) 0x27F0, (char) 0x27FF));

    INDEXES = new int[0x2800]; // top == highest value char
    for (int i=0; i < CHARSET.length; i++) {
      INDEXES[CHARSET[i]] = i;
    }
  }

  public static String encode(byte[] buf) {
    StringBuilder sb = new StringBuilder();
    int chunks = buf.length / 11;
    int offset = 0;
    // process whole chunks of 11 bytes
    for (int i=0; i < chunks; i++, offset += 11) {
      encodeChunk(buf, offset, sb);
    }
    // Handle any remainder by padding with 0's
    if (offset < buf.length) {
      byte[] tail = new byte[11];
      System.arraycopy(buf, offset, tail, 0, buf.length - offset);
      encodeChunk(tail, 0, sb);
    }
    // rstrip, null padding -> spaces
    int n = sb.length();
    while (sb.charAt(--n) == ' ');
    sb.setLength(n + 1);
    return sb.toString();
  }

  private static void encodeChunk(byte[] buf, int offset, StringBuilder output) {
    output.append(CHARSET[((buf[offset] & 0xff) << 3) | ((buf[++offset] & 0xe0) >>> 5)]);
    output.append(CHARSET[((buf[offset] & 0x1f) << 6) | ((buf[++offset] & 0xfd) >>> 2)]);
    output.append(CHARSET[((buf[offset] & 0x03) << 9) | ((buf[++offset] & 0xff) << 1) | ((buf[++offset] & 0x80) >>> 7)]);
    output.append(CHARSET[((buf[offset] & 0x7f) << 4) | ((buf[++offset] & 0xf0) >>> 4)]);
    output.append(CHARSET[((buf[offset] & 0x0f) << 7) | ((buf[++offset] & 0xfe) >>> 1)]);
    output.append(CHARSET[((buf[offset] & 0x01) << 10) | ((buf[++offset] & 0xff) << 2) | ((buf[++offset] & 0xc0) >>> 6)]);
    output.append(CHARSET[((buf[offset] & 0x3f) << 5) | ((buf[++offset] & 0xf8) >>> 3)]);
    output.append(CHARSET[((buf[offset] & 0x07) << 8) | (buf[++offset] & 0xff)]);
  }

  public static byte[] decode(String s) {
    byte[] output = new byte[(int) (Math.floor(((double) s.length() / 8) * 11))];
    char[] input = s.toCharArray();
    int offset = 0;
    int bytes = 0;
    int chunks = input.length / 8;
    // process whole chunks of 8 characters
    for (int i = 0; i < chunks; i++, offset += 8, bytes += 11) {
      decodeChunk(input, offset, output, bytes);
    }
    // handle remaining remaining chacters by padding
    if (offset < input.length) {
      char[] padded = new char[8];
      byte[] tail = new byte[11];
      System.arraycopy(input, offset, padded, 0, input.length - offset);
      decodeChunk(padded, 0, tail, 0);
      int i = 0;
      // exclude any trailing nulls
      while (tail[i] != 0) {
        output[bytes++] = tail[i++];
      }
    }
    return output;
  }

  private static void decodeChunk(char[] input, int offset, byte[] output, int pos) {
    int[] values = new int[8];
    // peform all the lookups here first so they can be referenced below
    for (int i=0; i < 8; i++) {
      values[i] = INDEXES[input[offset + i]];
    }
    output[pos++] = (byte) ((values[0] & 0x7f8) >>> 3);
    output[pos++] = (byte) (((values[0] & 0x07) << 5) | ((values[1] & 0x7c0) >>> 6));
    output[pos++] = (byte) (((values[1] & 0x3f) << 2) | ((values[2] & 0x600) >>> 9));
    output[pos++] = (byte) ((values[2] & 0x1fe) >>> 1);
    output[pos++] = (byte) (((values[2] & 0x01) << 7) | ((values[3] & 0x7f0) >>> 4));
    output[pos++] = (byte) (((values[3] & 0x0f) << 4) | ((values[4] & 0x780) >>> 7));
    output[pos++] = (byte) (((values[4] & 0x7f) << 1) | ((values[5] & 0x400) >>> 10));
    output[pos++] = (byte) ((values[5] & 0x3fc) >> 2);
    output[pos++] = (byte) (((values[5] & 0x03) << 6) | ((values[6] & 0x7e0) >>> 5));
    output[pos++] = (byte) (((values[6] & 0x1f) << 3) | ((values[7] & 0x700) >>> 8));
    output[pos++] = (byte) (values[7] & 0xff);
  }

  private static char[] range(char start, char stop) {
    char[] result = new char[stop - start];
    for (char ch = start, i = 0; ch < stop; ch++, i++)
      result[i] = ch;

    return result;
  }

  private static char[] concat(char[]... lst) {
    int totalLength = 0;
    for (char[] arr : lst) {
      totalLength += arr.length;
    }

    char[] result = new char[totalLength];
    int offset = 0;
    for (char[] arr : lst) {
      System.arraycopy(arr, 0, result, offset, arr.length);
      offset += arr.length;
    }
    return result;
  }
}
