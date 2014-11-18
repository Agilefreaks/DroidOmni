package com.omnipaste.droidomni.interaction;

import com.omnipaste.droidomni.service.subscriber.ClipboardSubscriber;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Refresh {
  private ClipboardSubscriber clipboardSubscriber;

  @Inject
  public Refresh(ClipboardSubscriber clipboardSubscriber) {
    this.clipboardSubscriber = clipboardSubscriber;
  }

  public void all() {
    omniClipboard();
  }

  public void omniClipboard() {
    clipboardSubscriber.refresh();
  }
}
