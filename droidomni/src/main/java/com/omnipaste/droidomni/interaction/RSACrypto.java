package com.omnipaste.droidomni.interaction;

import android.util.Base64;

import org.spongycastle.crypto.engines.RSAEngine;
import org.spongycastle.crypto.util.PublicKeyFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RSACrypto {
  private final String what;
  private final RSAEngine engine;

  public static RSACrypto encrypt(String what) {
    return new RSACrypto(what);
  }

  private RSACrypto(String what) {
    this.what = what;
    engine = new RSAEngine();
  }

  public String with(String key) throws IOException {
    byte[] publicKey = Base64.decode(key, Base64.DEFAULT);
    engine.init(true, PublicKeyFactory.createKey(publicKey));

    return Base64.encodeToString(transformBytes(), Base64.DEFAULT);
  }

  private byte[] transformBytes() throws IOException {
    byte[] input = what.getBytes();
    int blockSize = engine.getInputBlockSize();
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    for (int chunkPosition = 0; chunkPosition < input.length; chunkPosition += blockSize) {
      int chunkSize = Math.min(blockSize, input.length - chunkPosition);
      output.write(engine.processBlock(input, chunkPosition, chunkSize));
    }

    return output.toByteArray();
  }
}
