package com.yasminapp;

import org.junit.Assert;
import org.junit.Test;

import com.yasminapp.client.Base2K;


public class Base2KTest {

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
