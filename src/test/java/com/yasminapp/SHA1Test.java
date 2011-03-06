package com.yasminapp;

import org.junit.Assert;
import org.junit.Test;

import com.yasminapp.client.SHA1;

import static com.yasminapp.client.Hex.fromHex;

public class SHA1Test {
  @Test
  public void testVector1() {
    check("abc", "A9 99 3E 36 47 06 81 6A BA 3E 25 71 78 50 C2 6C 9C D0 D8 9D");
  }

  @Test
  public void testVector2() {
    check("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq",
        "84 98 3E 44 1C 3B D2 6E BA AE 4A A1 F9 51 29 E5 E5 46 70 F1");
  }

  @Test
  public void testVector3() {
    check("a", "34 AA 97 3C D4 C4 DA A4 F6 1E EB 2B DB AD 27 31 65 34 01 6F");
  }

  @Test
  public void testVector4() {
    check("0123456701234567012345670123456701234567012345670123456701234567",
        "DE A3 56 A2 CD DD 90 C7 A7 EC ED C5 EB B5 63 93 4F 46 04 52");
  }

  void check(String message, String expectedHash) {
    byte[] input = message.getBytes();
    byte[] expectedResult = fromHex(expectedHash);
    SHA1 sha1 = new SHA1();
    sha1.update(input);
    byte[] result = sha1.finish();
    Assert.assertArrayEquals(expectedResult, result);
  }
}
