package com.yasminapp.client;

public interface Decrypter {
  public void decryptBlock(byte[] in, int inOff, byte[] out, int outOff);
}