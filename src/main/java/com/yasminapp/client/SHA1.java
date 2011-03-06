package com.yasminapp.client;

import static java.util.Arrays.fill;

/**
 *  SHA-1 Implementation in Java. Derived from the example
 * implementation provided in RFC3174.
 */
public class SHA1 {
  private static final int K0 = 0x5A827999;
  private static final int K1 = 0x6ED9EBA1;
  private static final int K2 = 0x8F1BBCDC;
  private static final int K3 = 0xCA62C1D6;
  private static final long MAX_BYTES = 0x7FFFFFFFFFFFFFFL;

  private int H0 = 0x67452301;
  private int H1 = 0xEFCDAB89;
  private int H2 = 0x98BADCFE;
  private int H3 = 0x10325476;
  private int H4 = 0xC3D2E1F0;

  // Current message length, in bytes
  private long len = 0;

  private byte[] msgBlock = new byte[64];
  private int blockIndex = 0;

  private int rotateLeft(int v, int n) {
    return v << n | (v >>> (32 - n));
  }

  public void update(byte[] input) {
    if (len + input.length > MAX_BYTES) {
      throw new RuntimeException("Cannot process messages with more than " + MAX_BYTES + " bytes");
    }
    for (byte b : input) {
      msgBlock[blockIndex++] = b;
    }
    len += input.length;
    if (blockIndex == 64) {
      processBlock();
    }
  }

  public byte[] finish() {
    // doPadding

    msgBlock[blockIndex++] = (byte) 0x80;
    if (blockIndex > 56) {
      fill(msgBlock, blockIndex, 64, (byte) 0);
      processBlock();
    }
    fill(msgBlock, blockIndex, 56, (byte) 0);

    // last 8 bytes are message length, in bits
    long bits = len * 8;
    msgBlock[56] = (byte) (bits >>> 56);
    msgBlock[57] = (byte) (bits >>> 48);
    msgBlock[58] = (byte) (bits >>> 40);
    msgBlock[59] = (byte) (bits >>> 32);
    msgBlock[60] = (byte) (bits >>> 24);
    msgBlock[61] = (byte) (bits >>> 16);
    msgBlock[62] = (byte) (bits >>> 8);
    msgBlock[63] = (byte) bits;

    processBlock();

    msgBlock = null;
    len = 0;

    // unpack the 5 ints into 20 bytes

    byte[] digest = new byte[20];
    digest[0] = (byte) (H0 >>> 24);
    digest[1] = (byte) (H0 >>> 16);
    digest[2] = (byte) (H0 >>> 8);
    digest[3] = (byte) H0;

    digest[4] = (byte) (H1 >>> 24);
    digest[5] = (byte) (H1 >>> 16);
    digest[6] = (byte) (H1 >>> 8);
    digest[7] = (byte) H1;

    digest[8] = (byte) (H2 >>> 24);
    digest[9] = (byte) (H2 >>> 16);
    digest[10] = (byte) (H2 >>> 8);
    digest[11] = (byte) H2;

    digest[12] = (byte) (H3 >>> 24);
    digest[13] = (byte) (H3 >>> 16);
    digest[14] = (byte) (H3 >>> 8);
    digest[15] = (byte) H3;

    digest[16] = (byte) (H4 >>> 24);
    digest[17] = (byte) (H4 >>> 16);
    digest[18] = (byte) (H4 >>> 8);
    digest[19] = (byte) H4;

    return digest;
  }

  private void processBlock() {
    int temp;
    int[] W = new int[80]; // word sequence
    int A, B, C, D, E; // word buffers
    int i = 0;

    // unpack 512 (16*32) bits into W
    for (i = 0; i < 64; i+=4) {
      W[i] = (msgBlock[i] & 0xFF) << 24;
      W[i] |= (msgBlock[i+1] & 0xFF) << 16;
      W[i] |= (msgBlock[i+2] & 0xFF) << 8;
      W[i] |= (msgBlock[i+3] & 0xFF);
    }

    for (i = 16; i < 80; i++) {
      W[i] = rotateLeft(W[i - 3] ^ W[i - 8] ^ W[i - 14] ^ W[i - 16], 1);
    }

    A = H0;
    B = H1;
    C = H2;
    D = H3;
    E = H4;

    for (i=0; i<20; i++) {
      temp = rotateLeft(A, 5) + ((B & C) | ((~B) & D)) + E + W[i] + K0;
      E = D;
      D = C;
      C = rotateLeft(B, 30);
      B = A;
      A = temp;
    }

    for (; i<40; i++) {
      temp = rotateLeft(A, 5) + (B ^ C ^ D) + E + W[i] + K1;
      E = D;
      D = C;
      C = rotateLeft(B, 30);
      B = A;
      A = temp;
    }

    for (; i<60; i++) {
      temp = rotateLeft(A, 5) + ((B & C) | (B & D) | (C & D)) + E + W[i] + K2;
      E = D;
      D = C;
      C = rotateLeft(B, 30);
      B = A;
      A = temp;
    }

    for (; i<80; i++) {
      temp = rotateLeft(A, 5) + (B ^ C ^ D) + E + W[i] + K3;
      E = D;
      D = C;
      C = rotateLeft(B, 30);
      B = A;
      A = temp;
    }

    H0 += A;
    H1 += B;
    H2 += C;
    H3 += D;
    H4 += E;

    blockIndex = 0;
  }
}
