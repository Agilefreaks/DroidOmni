package com.omnipaste.droidomni.interaction;

import com.omnipaste.clipboardprovider.ClipboardProvider;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CopyClipping {
  private ClipboardProvider clipboardProvider;

  @Inject
  public CopyClipping(ClipboardProvider clipboardProvider) {
    this.clipboardProvider = clipboardProvider;
  }

  public void run(ClippingDto clippingDto) {
    clipboardProvider.setLocalPrimaryClip(clippingDto);
  }
}
