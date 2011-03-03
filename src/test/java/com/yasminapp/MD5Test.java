package com.yasminapp;


import org.junit.Assert;
import org.junit.Test;

import com.yasminapp.client.Hex;
import com.yasminapp.client.MD5;


public class MD5Test {
  /*
   * Compile with -DTEST to create a self-contained executable test program.
   * The test program should print out the same values as given in section
   * A.5 of RFC 1321, reproduced below.
   */
  String[][] TEST_VECTORS = {
      {"", "d41d8cd98f00b204e9800998ecf8427e"},
      {"945399884.61923487334tuvga", "0cc175b9c0f1b6a831c399e269772661"},
      {"abc", "900150983cd24fb0d6963f7d28e17f72"},
      {"message digest", "f96b697d7cb7938d525a2f31aaf161d0"},
      {"abcdefghijklmnopqrstuvwxyz", "c3fcd3d76192e4007dfb496cca67e13b"},
      {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "d174ab98d277d9f5a5611c2c9f419d9f"},
      {"12345678901234567890123456789012345678901234567890123456789012345678901234567890", "57edf4a22be3c955ac49da2e2107b67a"}
  };

  @Test
  public void testMD5() {
    for (int i=0; i<TEST_VECTORS.length;i++) {
      byte[] input = TEST_VECTORS[i][0].getBytes();
      byte[] expectedResult = Hex.fromHex(TEST_VECTORS[i][1]);
      MD5 md5 = new MD5();
      md5.update(input);
      byte[] result = md5.finish();
      Assert.assertArrayEquals(expectedResult, result);
    }
  }
}
