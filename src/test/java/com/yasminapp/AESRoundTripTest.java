package com.yasminapp;

import java.util.Random;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.yasminapp.client.AES;
import com.yasminapp.client.AESLight;
import com.yasminapp.client.Decrypter;
import com.yasminapp.client.Encrypter;
import com.yasminapp.client.Hex;

import static org.junit.Assert.assertArrayEquals;

@RunWith(Theories.class)
public class AESRoundTripTest {
  public static final String[][] TEST_VECTORS;

  static {
    Random RND = new Random();
    TEST_VECTORS = new String[100][2];
    for (int i=0; i<TEST_VECTORS.length; i++) {
      byte[] key = new byte[AES.BLOCK_SIZE];
      RND.nextBytes(key);
      TEST_VECTORS[i][0] = Hex.toHex(key);

      byte[] plainText = new byte[AES.BLOCK_SIZE];
      RND.nextBytes(plainText);
      TEST_VECTORS[i][1] = Hex.toHex(plainText);
    }
  }

  @DataPoints
  public static String[][] testVectors() {
    return TEST_VECTORS;
  };

  @Theory
  public void testEncryptDecrypt(String[] keyAndPlainText) {
    byte[] key = Hex.fromHex(keyAndPlainText[0]);
    byte[] plainText = Hex.fromHex(keyAndPlainText[1]);

    System.out.println("Plaintext:  " + new String(Hex.toHex(plainText)));
    System.out.println("Key:        " + new String(Hex.toHex(key)));

    Encrypter enc = AESLight.encrypter(key);

    byte[] cipherText = new byte[AES.BLOCK_SIZE];
    enc.encryptBlock(plainText, 0, cipherText, 0);
    System.out.println("Ciphertext: " + new String(Hex.toHex(cipherText)));

    Decrypter dec = AESLight.decrypter(key);

    byte[] decrypted = new byte[AES.BLOCK_SIZE];
    dec.decryptBlock(cipherText, 0, decrypted, 0);

    System.out.println("Decrypted:  " + new String(Hex.toHex(decrypted)));

    assertArrayEquals("Decrypted data", plainText, decrypted);
    System.out.println("--------------------------------------------");
  }
}
