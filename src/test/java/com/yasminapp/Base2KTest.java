package com.yasminapp;

import org.junit.Assert;
import org.junit.Test;

import com.yasminapp.client.Base2K;


public class Base2KTest {
  // 256 random bytes
  public static final byte[] TEST_DATA = {
    /*0000000*/ (byte) 0xc8,(byte) 0x82,(byte) 0x2e,(byte) 0x1e,(byte) 0xbd,(byte) 0x61,(byte) 0x45,(byte) 0x61,
    /*0000008*/ (byte) 0x94,(byte) 0x26,(byte) 0x9b,(byte) 0x68,(byte) 0x75,(byte) 0xa0,(byte) 0x04,(byte) 0x6b,
    /*0000010*/ (byte) 0x9a,(byte) 0x1e,(byte) 0xd1,(byte) 0x84,(byte) 0x68,(byte) 0x96,(byte) 0x3e,(byte) 0xa1,
    /*0000019*/ (byte) 0xb4,(byte) 0x46,(byte) 0x39,(byte) 0xde,(byte) 0x82,(byte) 0x92,(byte) 0x59,(byte) 0x90,
    /*0000020*/ (byte) 0x17,(byte) 0x5f,(byte) 0xd9,(byte) 0x60,(byte) 0xbe,(byte) 0x3c,(byte) 0x73,(byte) 0x25,
    /*0000028*/ (byte) 0x40,(byte) 0xb8,(byte) 0x64,(byte) 0xda,(byte) 0xfa,(byte) 0x74,(byte) 0xe9,(byte) 0xe7,
    /*0000030*/ (byte) 0x1a,(byte) 0x7d,(byte) 0x8d,(byte) 0xcf,(byte) 0xfb,(byte) 0x55,(byte) 0x05,(byte) 0x24,
    /*0000038*/ (byte) 0x10,(byte) 0x85,(byte) 0x6b,(byte) 0xba,(byte) 0xeb,(byte) 0x41,(byte) 0x44,(byte) 0x61,
    /*0000040*/ (byte) 0x5b,(byte) 0xf7,(byte) 0xc0,(byte) 0x9c,(byte) 0x79,(byte) 0xac,(byte) 0x75,(byte) 0x53,
    /*0000048*/ (byte) 0xc7,(byte) 0xcf,(byte) 0xdf,(byte) 0xf7,(byte) 0x3b,(byte) 0xa4,(byte) 0xc6,(byte) 0xf9,
    /*0000050*/ (byte) 0xaa,(byte) 0xe4,(byte) 0xc1,(byte) 0x71,(byte) 0xae,(byte) 0x08,(byte) 0xde,(byte) 0x3b,
    /*0000058*/ (byte) 0xc2,(byte) 0x7f,(byte) 0x30,(byte) 0x44,(byte) 0x32,(byte) 0x1e,(byte) 0x8e,(byte) 0x63,
    /*0000060*/ (byte) 0xf7,(byte) 0x4a,(byte) 0x8e,(byte) 0xaa,(byte) 0x42,(byte) 0x39,(byte) 0x8f,(byte) 0xbf,
    /*0000068*/ (byte) 0x3e,(byte) 0xb5,(byte) 0xe7,(byte) 0xc9,(byte) 0xa5,(byte) 0xd3,(byte) 0x30,(byte) 0x3c,
    /*0000070*/ (byte) 0x24,(byte) 0xf2,(byte) 0x97,(byte) 0x11,(byte) 0x5c,(byte) 0x27,(byte) 0xb2,(byte) 0x9a,
    /*0000078*/ (byte) 0xc1,(byte) 0xa8,(byte) 0x0e,(byte) 0x48,(byte) 0x80,(byte) 0xc1,(byte) 0x6a,(byte) 0xc7,
    /*0000080*/ (byte) 0x76,(byte) 0xaa,(byte) 0xd0,(byte) 0xb2,(byte) 0xce,(byte) 0x83,(byte) 0x32,(byte) 0x2a,
    /*0000088*/ (byte) 0x72,(byte) 0xd4,(byte) 0x73,(byte) 0x40,(byte) 0x93,(byte) 0x92,(byte) 0x6b,(byte) 0x5d,
    /*0000090*/ (byte) 0xa2,(byte) 0x55,(byte) 0x13,(byte) 0x7b,(byte) 0x43,(byte) 0xff,(byte) 0xe5,(byte) 0x1d,
    /*0000098*/ (byte) 0x0a,(byte) 0x13,(byte) 0x69,(byte) 0xe4,(byte) 0xef,(byte) 0xde,(byte) 0x69,(byte) 0xc0,
    /*00000a0*/ (byte) 0x17,(byte) 0x31,(byte) 0xcc,(byte) 0xb3,(byte) 0x00,(byte) 0x86,(byte) 0x7d,(byte) 0xa9,
    /*00000a8*/ (byte) 0x18,(byte) 0x7c,(byte) 0xa7,(byte) 0x4c,(byte) 0xbf,(byte) 0x1c,(byte) 0xef,(byte) 0x34,
    /*00000b0*/ (byte) 0xa1,(byte) 0x0e,(byte) 0xdb,(byte) 0x44,(byte) 0x4e,(byte) 0xd1,(byte) 0xac,(byte) 0x77,
    /*00000b8*/ (byte) 0x5b,(byte) 0x49,(byte) 0xf4,(byte) 0xa9,(byte) 0xc3,(byte) 0x5a,(byte) 0x62,(byte) 0x0b,
    /*00000c0*/ (byte) 0x16,(byte) 0xab,(byte) 0xb2,(byte) 0x9d,(byte) 0x05,(byte) 0xb7,(byte) 0xd2,(byte) 0x21,
    /*00000c8*/ (byte) 0x90,(byte) 0x75,(byte) 0x6e,(byte) 0xfd,(byte) 0x01,(byte) 0x0e,(byte) 0x5c,(byte) 0x42,
    /*00000d0*/ (byte) 0x86,(byte) 0x37,(byte) 0x39,(byte) 0xaa,(byte) 0x74,(byte) 0xdc,(byte) 0xdc,(byte) 0x53,
    /*00000d8*/ (byte) 0xec,(byte) 0x82,(byte) 0xdb,(byte) 0xf7,(byte) 0x22,(byte) 0xe8,(byte) 0xe5,(byte) 0xc2,
    /*00000e0*/ (byte) 0x30,(byte) 0xff,(byte) 0xc1,(byte) 0x40,(byte) 0x25,(byte) 0x33,(byte) 0x97,(byte) 0x92,
    /*00000e8*/ (byte) 0x58,(byte) 0x40,(byte) 0x0c,(byte) 0xb8,(byte) 0x9a,(byte) 0x55,(byte) 0xd6,(byte) 0x4c,
    /*00000f0*/ (byte) 0xfd,(byte) 0x2a,(byte) 0x0a,(byte) 0xb2,(byte) 0x80,(byte) 0x9f,(byte) 0x0b,(byte) 0x6c,
    /*00000f8*/ (byte) 0x13,(byte) 0x86,(byte) 0x3a,(byte) 0xb9,(byte) 0x6e,(byte) 0x48,(byte) 0xa1,(byte) 0xe4
  };

  @Test
  public void testEncode_TestCase1() {
    byte[] input = "The Quick Brown Fox Jumped Over The Lazy Dog".getBytes();
    String expectedResult = "˫ɟʆᓶ˼ᕬᕃbѰҺ◆ɉДᖿӦʽшӶ΢ɉ➧ǚᘣtРƍÛᗶᔛᑠÐ❓";
    String result = Base2K.encode(input);
    Assert.assertNotNull(result);
    Assert.assertEquals("Encoded value", expectedResult, result);
  }

  @Test
  public void testDecode_TestCase1() {
    String input = "˫ɟʆᓶ˼ᕬᕃbѰҺ◆ɉДᖿӦʽшӶ΢ɉ➧ǚᘣtРƍÛᗶᔛᑠÐ❓";
    byte[] expectedResult = "The Quick Brown Fox Jumped Over The Lazy Dog"
        .getBytes();
    byte[] result = Base2K.decode(input);
    Assert.assertNotNull(result);
    Assert.assertArrayEquals("Decoded value", expectedResult, result);
  }

  @Test
  public void testEncode_TestCase2() {
    byte[] input = "00000000000".getBytes();
    String expectedResult = "ǆө£ϟ8ĄᗥP";
    String result = Base2K.encode(input);
    Assert.assertNotNull(result);
    Assert.assertEquals("Encoded value", expectedResult, result);
  }

  @Test
  public void testDecode_TestCase2() {
    String input = "ǆө£ϟ8ĄᗥP";
    byte[] expectedResult = "00000000000".getBytes();
    byte[] result = Base2K.decode(input);
    Assert.assertNotNull(result);
    Assert.assertArrayEquals("Decoded value", expectedResult, result);
  }

  @Test
  public void testEncode_TestCase3() {
    byte[] input = { 'A' };
    String expectedResult = "ɍ";
    String result = Base2K.encode(input);
    Assert.assertNotNull(result);
    Assert.assertEquals("Encoded value", expectedResult, result);
  }

  @Test
  public void testDecode_TestCase3() {
    String input = "ɍ";
    byte[] expectedResult = { 'A' };
    byte[] result = Base2K.decode(input);
    Assert.assertNotNull(result);
    Assert.assertArrayEquals("Decoded value", expectedResult, result);
  }

}
