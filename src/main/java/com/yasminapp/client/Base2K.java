package com.yasminapp.client;

public class Base2K {
  // See http://twuuenc.atxconsulting.com/index.htm/index.htm
  private static final char[] ALPHABET = concat(
      range(0x20, 0x7E), range(0xA1, 0xFF), range(0x100, 0x17F),
      range(0x180, 0x24F), range(0x250, 0x2AE), range(0x2B0, 0x2FE),
      range(0x390, 0x3CE), range(0x3D0, 0x3FF), range(0x400, 0x4FF),
      range(0x1401, 0x1676), range(0x2580, 0x259F), range(0x25A0, 0x25FF),
      range(0x2701, 0x27BE), range(0x27C0, 0x27EB), range(0x27F0, 0x27FF));

  public static String encode(byte[] buf) {
    throw new RuntimeException("Not Implemented");
  }

  public static byte[] decode(String s) {
    throw new RuntimeException("Not Implemented");
  }

  private static char[] range(int start, int stop) {
    char[] result = new char[stop - start];

    for (int i = 0; i < stop - start; i++)
      result[i] = (char) (start + i);

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
