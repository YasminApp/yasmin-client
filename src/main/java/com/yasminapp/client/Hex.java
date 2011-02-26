package com.yasminapp.client;

public class Hex {
  protected static final byte[] ENCODING_TABLE = {
    (byte) '0', (byte) '1', (byte) '2', (byte) '3', 
    (byte) '4', (byte) '5', (byte) '6', (byte) '7', 
    (byte) '8', (byte) '9', (byte) 'A', (byte) 'B', 
    (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F'
  };
  protected static final byte[] DECODING_TABLE = new byte[128];

  /**
   * Initialize the decoding table.
   */
  protected static void initialiseDecodingTable() {
    for (int i = 0; i < ENCODING_TABLE.length; i++) {
      DECODING_TABLE[ENCODING_TABLE[i]] = (byte) i;
    }

    // deal with lower case letters as well
    DECODING_TABLE['a'] = DECODING_TABLE['A'];
    DECODING_TABLE['b'] = DECODING_TABLE['B'];
    DECODING_TABLE['c'] = DECODING_TABLE['C'];
    DECODING_TABLE['d'] = DECODING_TABLE['D'];
    DECODING_TABLE['e'] = DECODING_TABLE['E'];
    DECODING_TABLE['f'] = DECODING_TABLE['F'];
  }

  static {
    initialiseDecodingTable();
  }

  public static String toHex(byte[] data) {
    return toHex(data, 0, data.length);
  }

  public static String toHex(byte[] data, final int off, final int length) {
    StringBuilder sb = new StringBuilder(length * 2);
    for (int i = off; i < (off + length); i++) {
      int v = data[i] & 0xff;
      sb.append(ENCODING_TABLE[(v >>> 4)]);
      sb.append(ENCODING_TABLE[(v & 0xf)]);
    }
    return sb.toString();
  }
  
  private static boolean ignore(final char c) {
    return (c == '\n' || c == '\r' || c == '\t' || c == ' ');
  }
  
  public static byte[] fromHex(String data) {
    byte[] bytes = new byte[data.length() / 2];
    char[] chars = data.toCharArray();

    byte b = 0;
    int offset = 0;
    boolean first=false;
    
    for (int i=0; i< chars.length; i++) {
      if (ignore(chars[i]))
        continue;

      if (first) {
        b = DECODING_TABLE[((byte) chars[i++]) & 0x7f];
        first = true;
        continue;
      }
      bytes[offset] = (byte) ((b << 4) | DECODING_TABLE[((byte) chars[i++]) & 0x7f] & 0xff);
    }
    return bytes;
  }
}
