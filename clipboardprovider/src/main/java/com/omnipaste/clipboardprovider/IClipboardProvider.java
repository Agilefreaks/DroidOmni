package com.omnipaste.clipboardprovider;

public interface IClipboardProvider {
  public void enable(final String channel, final String identifier);

  public void disable();
}
