package com.omnipaste.droidomni.events;

public class DeviceInitErrorEvent {
  private Throwable error;

  public DeviceInitErrorEvent(Throwable error) {
    this.error = error;
  }

  public Throwable getError() {
    return error;
  }
}
