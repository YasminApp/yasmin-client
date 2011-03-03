package com.yasminapp;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.yasminapp.client.AES;
import com.yasminapp.client.AESFast;
import com.yasminapp.client.AESLight;
import com.yasminapp.client.AESMedium;
import com.yasminapp.client.Encrypter;
import com.yasminapp.client.Hex;

import static com.yasminapp.client.Hex.fromHex;

import static org.junit.Assert.assertArrayEquals;

@RunWith(Theories.class)
public class AESECBTest {

  // http://csrc.nist.gov/publications/nistpubs/800-38a/sp800-38a.pdf
  // Section #F.1
  public static final byte[] AES128_ECB_KEY = Hex.fromHex("2b7e151628aed2a6abf7158809cf4f3c");
  public static final String[][] AES128_ECB_VECTORS = {
    {"6bc1bee22e409f96e93d7e117393172a", "3ad77bb40d7a3660a89ecaf32466ef97"},
    {"ae2d8a571e03ac9c9eb76fac45af8e51", "f5d3d58503b9699de785895a96fdbaaf"},
    {"30c81c46a35ce411e5fbc1191a0a52ef", "43b1cd7f598ece23881b00e3ed030688"},
    {"f69f2445df4f9b17ad2b417be66c3710", "7b0c785e27e8ad3f8223207104725dd4"}
  };

  @DataPoints
  public static String[][] testVectors() {
    return AES128_ECB_VECTORS;
  };

  @Theory
  public void AESLight_passes_AES128_ECB_Test_Vectors(String[] inOut) {
    byte[] plainText = fromHex(inOut[0]);
    byte[] cipherText = fromHex(inOut[1]);
    Encrypter enc = AESLight.encrypter(AES128_ECB_KEY);
    byte[] encrypted = new byte[AES.BLOCK_SIZE];
    enc.encryptBlock(plainText, 0, encrypted, 0);
    assertArrayEquals(cipherText, encrypted);
  }

  @Theory
  public void AESMedium_passes_AES128_ECB_Test_Vectors(String[] inOut) {
    byte[] plainText = fromHex(inOut[0]);
    byte[] cipherText = fromHex(inOut[1]);
    Encrypter enc = AESMedium.encrypter(AES128_ECB_KEY);
    byte[] encrypted = new byte[AES.BLOCK_SIZE];
    enc.encryptBlock(plainText, 0, encrypted, 0);
    assertArrayEquals(cipherText, encrypted);
  }

  @Theory
  public void AESFast_passes_AES128_ECB_Test_Vectors(String[] inOut) {
    byte[] plainText = fromHex(inOut[0]);
    byte[] cipherText = fromHex(inOut[1]);
    Encrypter enc = AESFast.encrypter(AES128_ECB_KEY);
    byte[] encrypted = new byte[AES.BLOCK_SIZE];
    enc.encryptBlock(plainText, 0, encrypted, 0);
    assertArrayEquals(cipherText, encrypted);
  }
}
