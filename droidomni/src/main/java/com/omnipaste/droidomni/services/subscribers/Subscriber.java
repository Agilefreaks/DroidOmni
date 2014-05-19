package com.omnipaste.droidomni.services.subscribers;

public interface Subscriber {
  public void start(String deviceIdentifier);

  public void stop();
}
