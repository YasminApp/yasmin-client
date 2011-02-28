/*
  Copyright (C) 1999 Aladdin Enterprises.  All rights reserved.

  This software is provided 'as-is', without any express or implied
  warranty.  In no event will the authors be held liable for any damages
  arising from the use of this software.

  Permission is granted to anyone to use this software for any purpose,
  including commercial applications, and to alter it and redistribute it
  freely, subject to the following restrictions:

  1. The origin of this software must not be misrepresented; you must not
     claim that you wrote the original software. If you use this software
     in a product, an acknowledgment in the product documentation would be
     appreciated but is not required.
  2. Altered source versions must be plainly marked as such, and must not be
     misrepresented as being the original software.
  3. This notice may not be removed or altered from any source distribution.

  L. Peter Deutsch
  ghost@aladdin.com

 */
package com.yasminapp.client;

/*
  Independent implementation of MD5 (RFC 1321).

  This code implements the MD5 Algorithm defined in RFC 1321.
  It is derived directly from the text of the RFC and not from the
  reference implementation.

  The original and principal author of md5.c is L. Peter Deutsch
  <ghost@aladdin.com>.  Other authors are noted in the change history
  that follows (in reverse chronological order):

  1999-11-04 lpd Edited comments slightly for automatic TOC extraction.
  1999-10-18 lpd Fixed typo in header comment (ansi2knr rather than md5).
  1999-05-03 lpd Original version.
 */
public class MD5 {



///*
// * For reference, here is the program that computed the T values.
// */
//#if 0
//#include <math.h>
//main()
//{
//    int i;
//    for (i = 1; i <= 64; ++i) {
//  unsigned long v = (unsigned long)(4294967296.0 * fabs(sin((double)i)));
//  printf("#define T%d 0x%08lx\n", i, v);
//    }
//    return 0;
//}
//#endif

/*
 * End of T computation program.
 */
  static final int[] T = {
      0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee, 0xf57c0faf, 0x4787c62a, 0xa8304613,
      0xfd469501, 0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be, 0x6b901122, 0xfd987193,
      0xa679438e, 0x49b40821, 0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa, 0xd62f105d,
      0x02441453, 0xd8a1e681, 0xe7d3fbc8, 0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed,
      0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a, 0xfffa3942, 0x8771f681, 0x6d9d6122,
      0xfde5380c, 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70, 0x289b7ec6, 0xeaa127fa,
      0xd4ef3085, 0x04881d05, 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665, 0xf4292244,
      0x432aff97, 0xab9423a7, 0xfc93a039, 0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
      0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1, 0xf7537e82, 0xbd3af235, 0x2ad7d2bb,
      0xeb86d391 };


  /** digest buffer */
  final int[] abcd;

  /** 64 byte accumulate block */
  byte[] block;

  /** message length in bits */
  int bits;

  final int[] X;
  int a, b, c, d;
  int t;

  public MD5() {
      bits = 0;
      block = new byte[64];
      abcd = new int[] {
          0x67452301, 0xefcdab89,
          0x98badcfe, 0x10325476
      };
      X = new int[16];
  }

  void process(byte[] block, int offset) {
    a = abcd[0];
    b = abcd[1];
    c = abcd[2];
    d = abcd[3];

    // 16 * 4 bytes (16 ints)
    for (int i = 0; i < 16; i++, offset += 4) {
      X[i] = block[offset] + (block[offset+1] << 8) + (block[offset+2] << 16) + (block[offset+3] << 24);
    }

    r1(a, b, c, d,  0,  7,  T[0]);
    r1(d, a, b, c,  1, 12,  T[1]);
    r1(c, d, a, b,  2, 17,  T[2]);
    r1(b, c, d, a,  3, 22,  T[3]);
    r1(a, b, c, d,  4,  7,  T[4]);
    r1(d, a, b, c,  5, 12,  T[5]);
    r1(c, d, a, b,  6, 17,  T[6]);
    r1(b, c, d, a,  7, 22,  T[7]);
    r1(a, b, c, d,  8,  7,  T[8]);
    r1(d, a, b, c,  9, 12, T[9]);
    r1(c, d, a, b, 10, 17, T[10]);
    r1(b, c, d, a, 11, 22, T[11]);
    r1(a, b, c, d, 12,  7, T[12]);
    r1(d, a, b, c, 13, 12, T[13]);
    r1(c, d, a, b, 14, 17, T[14]);
    r1(b, c, d, a, 15, 22, T[15]);

    r2(a, b, c, d,  1,  5, T[16]);
    r2(d, a, b, c,  6,  9, T[17]);
    r2(c, d, a, b, 11, 14, T[18]);
    r2(b, c, d, a,  0, 20, T[19]);
    r2(a, b, c, d,  5,  5, T[20]);
    r2(d, a, b, c, 10,  9, T[21]);
    r2(c, d, a, b, 15, 14, T[22]);
    r2(b, c, d, a,  4, 20, T[23]);
    r2(a, b, c, d,  9,  5, T[24]);
    r2(d, a, b, c, 14,  9, T[25]);
    r2(c, d, a, b,  3, 14, T[26]);
    r2(b, c, d, a,  8, 20, T[27]);
    r2(a, b, c, d, 13,  5, T[28]);
    r2(d, a, b, c,  2,  9, T[39]);
    r2(c, d, a, b,  7, 14, T[30]);
    r2(b, c, d, a, 12, 20, T[31]);

    r3(a, b, c, d,  5,  4, T[32]);
    r3(d, a, b, c,  8, 11, T[33]);
    r3(c, d, a, b, 11, 16, T[34]);
    r3(b, c, d, a, 14, 23, T[35]);
    r3(a, b, c, d,  1,  4, T[36]);
    r3(d, a, b, c,  4, 11, T[37]);
    r3(c, d, a, b,  7, 16, T[38]);
    r3(b, c, d, a, 10, 23, T[39]);
    r3(a, b, c, d, 13,  4, T[40]);
    r3(d, a, b, c,  0, 11, T[41]);
    r3(c, d, a, b,  3, 16, T[42]);
    r3(b, c, d, a,  6, 23, T[43]);
    r3(a, b, c, d,  9,  4, T[44]);
    r3(d, a, b, c, 12, 11, T[45]);
    r3(c, d, a, b, 15, 16, T[46]);
    r3(b, c, d, a,  2, 23, T[47]);

    r4(a, b, c, d,  0,  6, T[48]);
    r4(d, a, b, c,  7, 10, T[49]);
    r4(c, d, a, b, 14, 15, T[50]);
    r4(b, c, d, a,  5, 21, T[51]);
    r4(a, b, c, d, 12,  6, T[52]);
    r4(d, a, b, c,  3, 10, T[53]);
    r4(c, d, a, b, 10, 15, T[54]);
    r4(b, c, d, a,  1, 21, T[55]);
    r4(a, b, c, d,  8,  6, T[56]);
    r4(d, a, b, c, 15, 10, T[57]);
    r4(c, d, a, b,  6, 15, T[58]);
    r4(b, c, d, a, 13, 21, T[59]);
    r4(a, b, c, d,  4,  6, T[60]);
    r4(d, a, b, c, 11, 10, T[61]);
    r4(c, d, a, b,  2, 15, T[62]);
    r4(b, c, d, a,  9, 21, T[63]);

    abcd[0] += a; // TODO: check for reliance on unsigned overflow
    abcd[1] += b;
    abcd[2] += c;
    abcd[3] += d;

  }

  int rotateLeft(int v, int n) {
    return v << n | (v >>> (32 - n));
  }

  int f(int x, int y, int z) {
    return (x & y) | ((~x) & z);
  }

  void r1(int a, int b, int c, int d, int k, int s, int ti) {
    t = a + f(b, c, d) + X[k] + ti;
    a = rotateLeft(t, s) + b;
  }

  int g(int x, int y, int z) {
    return (x & y) | (x & (~z));
  }

  void r2(int a, int b, int c, int d, int k, int s, int ti) {
    t = a + g(b, c, d) + X[k] + ti;
    a = rotateLeft(t, s) + b;
  }

  int h(int x, int y, int z) {
    return (x ^ y ^ z);
  }

  void r3(int a, int b, int c, int d, int k, int s, int ti) {
    t = a + h(b, c, d) + X[k] + ti;
    a = rotateLeft(t, s) + b;
  }

  int i(int x, int y, int z) {
    return (y ^ (x | (~z)));
  }

  void r4(int a, int b, int c, int d, int k, int s, int ti) {
    t = a + i(b, c, d) + X[k] + ti;
    a = rotateLeft(t, s) + b;
  }

  public void update(byte[] input) {
    update(input, 0, input.length);
  }

  public void update(byte[] data, int offset, int len) {
    int left = len;
    int off = (bits / 8) % 64; // offset within the current block (bytes)
    int inputBits = len * 8;
    if (len <= 0)
      return;

    bits += inputBits;

    if (off > 0) {
      // Process an initial partial block.
      int copy = (off + len > 64 ? 64 - off : len);
      System.arraycopy(data, offset, block, off, copy);

      // return if still no full block to process
      if (off + copy < 64)
        return;

      offset += copy;
      left -= copy;
      process(block, 0);
    }

    // Process full blocks
    for (; left >= 64; offset += 64, left -= 64) {
      process(data, offset);
    }

    // Process a final partial block
    if (left > 0) {
      System.arraycopy(data, offset, block, 0, left);
    }
  }

  public byte[] finish() {
    final byte[] digest = new byte[16];
    final byte pad[] = {
        (byte) 0x80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0 };

    // only counts 2^31 of bits (up to 536,870,911 bytes of data)
    // should be sufficient for now ;-)
    byte[] data = {
        (byte) (bits & 0xff),
        (byte) ((bits >> 8) & 0xff),
        (byte) ((bits >> 16) & 0xff),
        (byte) ((bits >> 24) & 0xff),
        0,
        0,
        0,
        0
    };
    int i;

    int off = (bits / 8) % 64;
    int padlen = (off < 56) ? (56 - off) : (120 - off);

    /* Pad to 56 bytes mod 64. */
    update(pad, padlen, 0);

    /* Append the length. */
    update(data, 0, 8);

    for (i = 0; i < 16; ++i) {
      digest[i] = (byte) (abcd[i >> 2] >> ((i & 3) << 3));
    }
    return digest;
  }
}

/*
void
md5_finish(md5_state_t *pms, md5_byte_t digest[16])
{
    static const md5_byte_t pad[64] = {
  0x80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    md5_byte_t data[8];
    int i;

    // Save the length before padding.
    for (i = 0; i < 8; ++i)
  data[i] = (md5_byte_t)(pms->count[i >> 2] >> ((i & 3) << 3));
    // Pad to 56 bytes mod 64.
    md5_append(pms, pad, ((55 - (pms->count[0] >> 3)) & 63) + 1);
    // Append the length.
    md5_append(pms, data, 8);
    for (i = 0; i < 16; ++i)
  digest[i] = (md5_byte_t)(pms->abcd[i >> 2] >> ((i & 3) << 3));
}
*/

