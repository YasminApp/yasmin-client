/*
 * Copyright (C) 2011 YasminApp Team. All rights reserved.
 */

package com.yasminapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class YasminApp implements EntryPoint {

  private static final int AES_BLOCK_SIZE = 16;
  private final TextArea leftWindow = new TextArea();
  private final TextArea rightWindow = new TextArea();

  private final SimplePanel leftSizePanel = new SimplePanel();
  private final SimplePanel rightSizePanel = new SimplePanel();
  private boolean encrypting;
  private TextBox passphrase;
  private TextBox key;
  private TextArea plaintext;
  private TextArea cipher;

  public YasminApp() {
    this.leftWindow.setCharacterWidth(70);
    this.leftWindow.setVisibleLines(25);
    this.rightWindow.setCharacterWidth(70);
    this.rightWindow.setVisibleLines(25);
  }

  public void onModuleLoad() {
    // Set up uncaught exception handler
    if (GWT.isScript()) {
      GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
        public void onUncaughtException(Throwable e) {
          alert("Uncaught exception: " + e);
        }
      });
    }

    Document $doc = Document.get();

    // temp variable used for null checking
    Element e;
    
    e = $doc.getElementById("passphrase").cast();
    assert(e != null);
    TextBox passphrase = TextBox.wrap(e);

    e = $doc.getElementById("key").cast();
    assert(e != null);
	TextBox key = TextBox.wrap(e);

    e = $doc.getElementById("do-encrypt").cast();
    assert(e != null);
    Button encryptButton = Button.wrap(e);
    encryptButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent e) {
        encrypt();
      }
    });

    e = $doc.getElementById("plaintext").cast();
    assert(e != null);
    final TextArea plaintext = TextArea.wrap(e);

    e = $doc.getElementById("cipher").cast();
    assert(e != null);
    final TextArea cipher = TextArea.wrap(e);

    e = $doc.getElementById("clear-encrypt").cast();
    assert(e != null);
    Button encryptClearButton = Button.wrap(e);
    encryptClearButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent e) {
        plaintext.setText("");
      }
    });

    e = $doc.getElementById("do-decrypt").cast();
    assert(e != null);
    Button decryptButton = Button.wrap(e);
    decryptButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent e) {
        decrypt();
      }
    });

    e = $doc.getElementById("clear-decrypt").cast();
    assert(e != null);
    Button decryptClearButton = Button.wrap(e);
    decryptClearButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent e) {
        cipher.setText("");
      }
    });
  }

  public void encrypt() {
    byte[] keyBytes = Hex.fromHex(key.getText());
    cipher.setValue(transform(keyBytes, plaintext.getValue(), true));
  }

  public void decrypt() {
    byte[] keyBytes = Hex.fromHex(key.getText());
    plaintext.setValue(transform(keyBytes, cipher.getValue(), false));
  }

  public static String transform(byte[] key, String plaintext, boolean encrypt) {
    AES aes = new AES();
    aes.init(encrypt, key);
    int offset = 0;
    byte[] inputBytes;
    if (encrypt) {
      inputBytes = UTF8.encode(plaintext);
      // (optional): compress
    } else {
      inputBytes = Base64.decode(plaintext);
      // (optional): decompress
    }
    int len = inputBytes.length;
    byte[] cipherBytes = new byte[len];
    while (offset + AES_BLOCK_SIZE <= len) {
      aes.processBlock(inputBytes, offset, cipherBytes, offset);
      offset += AES_BLOCK_SIZE; // AES block size
    }
    int remainder = len % AES_BLOCK_SIZE;
    if (remainder > 0) {
      byte[] padded = new byte[AES_BLOCK_SIZE];
      System.arraycopy(inputBytes, offset, padded, 0, AES_BLOCK_SIZE - remainder);
      aes.processBlock(padded, 0, cipherBytes, offset);
    }
    String result;
    if (encrypt) {
      // (optional): compress
      result = Base64.encode(cipherBytes);
    } else {
      // (optional): decompress
      result = UTF8.decode(cipherBytes);
    }
    return result;
  }

  public native void alert(String msg) /*-{
		$wnd.alert(msg);
  }-*/;
}
