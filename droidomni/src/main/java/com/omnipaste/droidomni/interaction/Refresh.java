package com.omnipaste.droidomni.interaction;

import com.omnipaste.clipboardprovider.ClipboardProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Refresh {
  private ClipboardProvider clipboardProvider;

  @Inject
  public Refresh(ClipboardProvider clipboardProvider) {
    this.clipboardProvider = clipboardProvider;
  }

  public void all() {
    omniClipboard();
  }

  public void omniClipboard() {
    clipboardProvider.refreshOmni();
  }
}
