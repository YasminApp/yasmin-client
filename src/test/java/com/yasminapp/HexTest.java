package com.yasminapp;

import org.junit.Assert;
import org.junit.Test;

import com.yasminapp.client.Hex;

public class HexTest {
  @Test
  public void testFromHex() {
    byte[] expectedBytes = {
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
        0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e,
        0x1f, };

    byte[] result = Hex.fromHex("00 01 02 03 04 05 06 \n"
        + "07 08 09 0A 0B 0C 0D 0E 0F\n101112  13\t"
        + "1415 16 17    18 19 1 a 1B 1c 1D1E1f\t\t  \n\n");
    Assert.assertArrayEquals(expectedBytes, result);
  }

  @Test
  public void testFromHex2() {
    byte[] expectedBytes = {
        (byte) 0xd4, (byte) 0x1d, (byte) 0x8c, (byte) 0xd9, (byte) 0x8f, (byte) 0x00, (byte) 0xb2,
        (byte) 0x04, (byte) 0xe9, (byte) 0x80, (byte) 0x09, (byte) 0x98, (byte) 0xec, (byte) 0xf8,
        (byte) 0x42, (byte) 0x7e };

    byte[] result = Hex.fromHex("d41d8cd98f00b204e9800998ecf8427e");
    Assert.assertArrayEquals(expectedBytes, result);
  }

  @Test
  public void testFromHexPadding() {
    byte[] expectedBytes = {(byte) 0x0d, (byte) 0x41, (byte) 0xd8};

    byte[] result = Hex.fromHex("d41d8");
    Assert.assertArrayEquals(expectedBytes, result);
  }

  @Test
  public void testToHex() {
    String expectedResult = "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F";
    String result = Hex.toHex(new byte[] {
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf,
        0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e,
        0x1f });
    Assert.assertEquals(expectedResult, result);
  }
}
