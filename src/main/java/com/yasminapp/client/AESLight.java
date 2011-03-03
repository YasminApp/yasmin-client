/*
 * Copyright (c) 2000 - 2011 The Legion Of The Bouncy Castle
 * (http://www.bouncycastle.org)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// Original Java source:
// http://www.bouncycastle.org/viewcvs/viewcvs.cgi/java/crypto/src/org/bouncycastle/crypto/engines/AESEngine.java?rev=1.5

package com.yasminapp.client;

/**
 * an implementation of the AES (Rijndael), from FIPS-197.
 * <p>
 * For further details see: <a
 * href="http://csrc.nist.gov/encryption/aes/">http:/
 * /csrc.nist.gov/encryption/aes/</a>.
 *
 * This implementation is based on optimizations from Dr. Brian Gladman's paper
 * and C code at <a
 * href="http://fp.gladman.plus.com/cryptography_technology/rijndael/"
 * >http://fp.gladman.plus.com/cryptography_technology/rijndael/</a>
 *
 * There are three levels of tradeoff of speed vs memory Because java has no
 * preprocessor, they are written as three separate classes from which to choose
 *
 * The fastest uses 8Kbytes of static tables to precompute round calculations, 4
 * 256 word tables for encryption and 4 for decryption.
 *
 * The middle performance version uses only one 256 word table for each, for a
 * total of 2Kbytes, adding 12 rotate operations per round to compute the values
 * contained in the other tables from the contents of the first.
 *
 * The slowest version uses no static tables at all and computes the values in
 * each round.
 * <p>
 * This file contains the slowest, lightweight version with no static tables.
 *
 */
public class AESLight extends AES implements Encrypter, Decrypter {
  protected AESLight(byte[] key, boolean encrypt) {
    super(key, encrypt);
  }

  public static Encrypter encrypter(byte[] key) {
    return new AESLight(key, true);
  }

  public static Decrypter decrypter(byte[] key) {
    return new AESLight(key, false);
  }

  @Override
  protected void encryptBlock(int[][] KW) {
    int r, r0, r1, r2, r3;

    C0 ^= KW[0][0];
    C1 ^= KW[0][1];
    C2 ^= KW[0][2];
    C3 ^= KW[0][3];

    for (r = 1; r < ROUNDS - 1;) {
      r0 = mcol((S[C0 & 255] & 255) ^ ((S[(C1 >> 8) & 255] & 255) << 8)
          ^ ((S[(C2 >> 16) & 255] & 255) << 16) ^ (S[(C3 >> 24) & 255] << 24))
          ^ KW[r][0];
      r1 = mcol((S[C1 & 255] & 255) ^ ((S[(C2 >> 8) & 255] & 255) << 8)
          ^ ((S[(C3 >> 16) & 255] & 255) << 16) ^ (S[(C0 >> 24) & 255] << 24))
          ^ KW[r][1];
      r2 = mcol((S[C2 & 255] & 255) ^ ((S[(C3 >> 8) & 255] & 255) << 8)
          ^ ((S[(C0 >> 16) & 255] & 255) << 16) ^ (S[(C1 >> 24) & 255] << 24))
          ^ KW[r][2];
      r3 = mcol((S[C3 & 255] & 255) ^ ((S[(C0 >> 8) & 255] & 255) << 8)
          ^ ((S[(C1 >> 16) & 255] & 255) << 16) ^ (S[(C2 >> 24) & 255] << 24))
          ^ KW[r++][3];
      C0 = mcol((S[r0 & 255] & 255) ^ ((S[(r1 >> 8) & 255] & 255) << 8)
          ^ ((S[(r2 >> 16) & 255] & 255) << 16) ^ (S[(r3 >> 24) & 255] << 24))
          ^ KW[r][0];
      C1 = mcol((S[r1 & 255] & 255) ^ ((S[(r2 >> 8) & 255] & 255) << 8)
          ^ ((S[(r3 >> 16) & 255] & 255) << 16) ^ (S[(r0 >> 24) & 255] << 24))
          ^ KW[r][1];
      C2 = mcol((S[r2 & 255] & 255) ^ ((S[(r3 >> 8) & 255] & 255) << 8)
          ^ ((S[(r0 >> 16) & 255] & 255) << 16) ^ (S[(r1 >> 24) & 255] << 24))
          ^ KW[r][2];
      C3 = mcol((S[r3 & 255] & 255) ^ ((S[(r0 >> 8) & 255] & 255) << 8)
          ^ ((S[(r1 >> 16) & 255] & 255) << 16) ^ (S[(r2 >> 24) & 255] << 24))
          ^ KW[r++][3];
    }

    r0 = mcol((S[C0 & 255] & 255) ^ ((S[(C1 >> 8) & 255] & 255) << 8)
        ^ ((S[(C2 >> 16) & 255] & 255) << 16) ^ (S[(C3 >> 24) & 255] << 24))
        ^ KW[r][0];
    r1 = mcol((S[C1 & 255] & 255) ^ ((S[(C2 >> 8) & 255] & 255) << 8)
        ^ ((S[(C3 >> 16) & 255] & 255) << 16) ^ (S[(C0 >> 24) & 255] << 24))
        ^ KW[r][1];
    r2 = mcol((S[C2 & 255] & 255) ^ ((S[(C3 >> 8) & 255] & 255) << 8)
        ^ ((S[(C0 >> 16) & 255] & 255) << 16) ^ (S[(C1 >> 24) & 255] << 24))
        ^ KW[r][2];
    r3 = mcol((S[C3 & 255] & 255) ^ ((S[(C0 >> 8) & 255] & 255) << 8)
        ^ ((S[(C1 >> 16) & 255] & 255) << 16) ^ (S[(C2 >> 24) & 255] << 24))
        ^ KW[r++][3];

    // the final round is a simple function of S

    C0 = (S[r0 & 255] & 255) ^ ((S[(r1 >> 8) & 255] & 255) << 8)
        ^ ((S[(r2 >> 16) & 255] & 255) << 16) ^ (S[(r3 >> 24) & 255] << 24) ^ KW[r][0];
    C1 = (S[r1 & 255] & 255) ^ ((S[(r2 >> 8) & 255] & 255) << 8)
        ^ ((S[(r3 >> 16) & 255] & 255) << 16) ^ (S[(r0 >> 24) & 255] << 24) ^ KW[r][1];
    C2 = (S[r2 & 255] & 255) ^ ((S[(r3 >> 8) & 255] & 255) << 8)
        ^ ((S[(r0 >> 16) & 255] & 255) << 16) ^ (S[(r1 >> 24) & 255] << 24) ^ KW[r][2];
    C3 = (S[r3 & 255] & 255) ^ ((S[(r0 >> 8) & 255] & 255) << 8)
        ^ ((S[(r1 >> 16) & 255] & 255) << 16) ^ (S[(r2 >> 24) & 255] << 24) ^ KW[r][3];

  }

  @Override
  protected void decryptBlock(int[][] KW) {
    int r, r0, r1, r2, r3;

    C0 ^= KW[ROUNDS][0];
    C1 ^= KW[ROUNDS][1];
    C2 ^= KW[ROUNDS][2];
    C3 ^= KW[ROUNDS][3];

    for (r = ROUNDS - 1; r > 1;) {
      r0 = inv_mcol((Si[C0 & 255] & 255) ^ ((Si[(C3 >> 8) & 255] & 255) << 8)
          ^ ((Si[(C2 >> 16) & 255] & 255) << 16) ^ (Si[(C1 >> 24) & 255] << 24))
          ^ KW[r][0];
      r1 = inv_mcol((Si[C1 & 255] & 255) ^ ((Si[(C0 >> 8) & 255] & 255) << 8)
          ^ ((Si[(C3 >> 16) & 255] & 255) << 16) ^ (Si[(C2 >> 24) & 255] << 24))
          ^ KW[r][1];
      r2 = inv_mcol((Si[C2 & 255] & 255) ^ ((Si[(C1 >> 8) & 255] & 255) << 8)
          ^ ((Si[(C0 >> 16) & 255] & 255) << 16) ^ (Si[(C3 >> 24) & 255] << 24))
          ^ KW[r][2];
      r3 = inv_mcol((Si[C3 & 255] & 255) ^ ((Si[(C2 >> 8) & 255] & 255) << 8)
          ^ ((Si[(C1 >> 16) & 255] & 255) << 16) ^ (Si[(C0 >> 24) & 255] << 24))
          ^ KW[r--][3];
      C0 = inv_mcol((Si[r0 & 255] & 255) ^ ((Si[(r3 >> 8) & 255] & 255) << 8)
          ^ ((Si[(r2 >> 16) & 255] & 255) << 16) ^ (Si[(r1 >> 24) & 255] << 24))
          ^ KW[r][0];
      C1 = inv_mcol((Si[r1 & 255] & 255) ^ ((Si[(r0 >> 8) & 255] & 255) << 8)
          ^ ((Si[(r3 >> 16) & 255] & 255) << 16) ^ (Si[(r2 >> 24) & 255] << 24))
          ^ KW[r][1];
      C2 = inv_mcol((Si[r2 & 255] & 255) ^ ((Si[(r1 >> 8) & 255] & 255) << 8)
          ^ ((Si[(r0 >> 16) & 255] & 255) << 16) ^ (Si[(r3 >> 24) & 255] << 24))
          ^ KW[r][2];
      C3 = inv_mcol((Si[r3 & 255] & 255) ^ ((Si[(r2 >> 8) & 255] & 255) << 8)
          ^ ((Si[(r1 >> 16) & 255] & 255) << 16) ^ (Si[(r0 >> 24) & 255] << 24))
          ^ KW[r--][3];
    }

    r0 = inv_mcol((Si[C0 & 255] & 255) ^ ((Si[(C3 >> 8) & 255] & 255) << 8)
        ^ ((Si[(C2 >> 16) & 255] & 255) << 16) ^ (Si[(C1 >> 24) & 255] << 24))
        ^ KW[r][0];
    r1 = inv_mcol((Si[C1 & 255] & 255) ^ ((Si[(C0 >> 8) & 255] & 255) << 8)
        ^ ((Si[(C3 >> 16) & 255] & 255) << 16) ^ (Si[(C2 >> 24) & 255] << 24))
        ^ KW[r][1];
    r2 = inv_mcol((Si[C2 & 255] & 255) ^ ((Si[(C1 >> 8) & 255] & 255) << 8)
        ^ ((Si[(C0 >> 16) & 255] & 255) << 16) ^ (Si[(C3 >> 24) & 255] << 24))
        ^ KW[r][2];
    r3 = inv_mcol((Si[C3 & 255] & 255) ^ ((Si[(C2 >> 8) & 255] & 255) << 8)
        ^ ((Si[(C1 >> 16) & 255] & 255) << 16) ^ (Si[(C0 >> 24) & 255] << 24))
        ^ KW[r][3];

    // the final round's table is a simple function of Si

    C0 = (Si[r0 & 255] & 255) ^ ((Si[(r3 >> 8) & 255] & 255) << 8)
        ^ ((Si[(r2 >> 16) & 255] & 255) << 16) ^ (Si[(r1 >> 24) & 255] << 24) ^ KW[0][0];
    C1 = (Si[r1 & 255] & 255) ^ ((Si[(r0 >> 8) & 255] & 255) << 8)
        ^ ((Si[(r3 >> 16) & 255] & 255) << 16) ^ (Si[(r2 >> 24) & 255] << 24) ^ KW[0][1];
    C2 = (Si[r2 & 255] & 255) ^ ((Si[(r1 >> 8) & 255] & 255) << 8)
        ^ ((Si[(r0 >> 16) & 255] & 255) << 16) ^ (Si[(r3 >> 24) & 255] << 24) ^ KW[0][2];
    C3 = (Si[r3 & 255] & 255) ^ ((Si[(r2 >> 8) & 255] & 255) << 8)
        ^ ((Si[(r1 >> 16) & 255] & 255) << 16) ^ (Si[(r0 >> 24) & 255] << 24) ^ KW[0][3];
  }
}
