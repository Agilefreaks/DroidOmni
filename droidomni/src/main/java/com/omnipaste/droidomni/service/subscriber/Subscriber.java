package com.omnipaste.droidomni.service.subscriber;

public interface Subscriber {
  public void start(String deviceIdentifier);

  public void stop();
}
