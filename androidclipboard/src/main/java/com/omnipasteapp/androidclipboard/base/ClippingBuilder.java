package com.omnipasteapp.androidclipboard.base;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;

import com.omnipasteapp.omnicommon.interfaces.IClipping;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ClippingBuilder {
  private ClipboardManager clipboardManager;

  public ClippingBuilder(ClipboardManager clipboardManager) {
    this.clipboardManager = clipboardManager;
  }

  public IClipping build() {
    //noinspection ConstantConditions
    return new Clipping(getPrimaryClip().getItemAt(0).getText().toString());
  }

  private ClipData getPrimaryClip() {
    ClipData result = clipboardManager.getPrimaryClip();

    if (result == null) {
      result = new EmptyClipData();
    }

    return result;
  }
}