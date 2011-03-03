package com.yasminapp.client;

public interface Encrypter {
  public void encryptBlock(byte[] in, int inOff, byte[] out, int outOff);
}