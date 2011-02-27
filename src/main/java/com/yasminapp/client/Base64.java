package com.yasminapp.client;

public class Base64 {
  private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
      .toCharArray();

  private static final byte[] VALUES = new byte[128];
  static {
    for (int i = 0; i < ALPHABET.length; i++) {
      VALUES[ALPHABET[i]] = (byte) i;
    }
  }

  public static String encode(byte[] buf) {
    int size = buf.length;
    char[] ar = new char[((size + 2) / 3) * 4];
    int a = 0;
    int i = 0;
    while (i < size) {
      byte b0 = buf[i++];
      byte b1 = (i < size) ? buf[i++] : 0;
      byte b2 = (i < size) ? buf[i++] : 0;

      int mask = 0x3F;
      ar[a++] = ALPHABET[(b0 >> 2) & mask];
      ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
      ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
      ar[a++] = ALPHABET[b2 & mask];
    }
    switch (size % 3) {
    case 1:
      ar[--a] = '=';
    case 2:
      ar[--a] = '=';
    }
    return new String(ar);
  }

  public static byte[] decode(String s) {
    int delta = s.endsWith("==") ? 2 : s.endsWith("=") ? 1 : 0;
    byte[] buffer = new byte[s.length() * 3 / 4 - delta];
    int mask = 0xFF;
    int index = 0;
    for (int i = 0; i < s.length(); i += 4) {
      int c0 = VALUES[s.charAt(i) & 0x7f];
      int c1 = VALUES[s.charAt(i + 1)];
      buffer[index++] = (byte) (((c0 << 2) | (c1 >> 4)) & mask);
      if (index >= buffer.length) {
        return buffer;
      }
      int c2 = VALUES[s.charAt(i + 2) & 0x7f];
      buffer[index++] = (byte) (((c1 << 4) | (c2 >> 2)) & mask);
      if (index >= buffer.length) {
        return buffer;
      }
      int c3 = VALUES[s.charAt(i + 3) & 0x7f];
      buffer[index++] = (byte) (((c2 << 6) | c3) & mask);
    }
    return buffer;
  }
}
