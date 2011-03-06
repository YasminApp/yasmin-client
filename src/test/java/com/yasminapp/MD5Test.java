package com.yasminapp;

import org.junit.Assert;
import org.junit.Test;

import com.yasminapp.client.Hex;
import com.yasminapp.client.MD5;

public class MD5Test {

  @Test
  public void testVector1() {
    check("", "d41d8cd98f00b204e9800998ecf8427e");
  }

  @Test
  public void testVector2() {
    check("945399884.61923487334tuvga", "0cc175b9c0f1b6a831c399e269772661");
  }

  @Test
  public void testVector3() {
    check("abc", "900150983cd24fb0d6963f7d28e17f72");
  }

  @Test
  public void testVector4() {
    check("message digest", "f96b697d7cb7938d525a2f31aaf161d0");
  }

  @Test
  public void testVector5() {
    check("abcdefghijklmnopqrstuvwxyz", "c3fcd3d76192e4007dfb496cca67e13b");
  }

  @Test
  public void testVector6() {
    check("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", "d174ab98d277d9f5a5611c2c9f419d9f");
  }

  @Test
  public void testVector7() {
    check("12345678901234567890123456789012345678901234567890123456789012345678901234567890", "57edf4a22be3c955ac49da2e2107b67a");
  }

  void check(String message, String expectedHash) {
    byte[] input = message.getBytes();
    byte[] expectedResult = Hex.fromHex(expectedHash);
    MD5 md5 = new MD5();
    md5.update(input);
    byte[] result = md5.finish();
    Assert.assertArrayEquals(expectedResult, result);
  }
}
