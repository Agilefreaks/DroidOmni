package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.omniclipboard.OmniClipboardManager;

import javax.inject.Inject;

import dagger.Lazy;

public class ClipboardProvider implements IClipboardProvider {
  @Inject
  public Lazy<OmniClipboardManager> omniClipboardManager;

  @Inject
  public ClipboardProvider() {
  }

  public void enable() {
    omniClipboardManager.get();
  }

  public void disable() {
  }
}
