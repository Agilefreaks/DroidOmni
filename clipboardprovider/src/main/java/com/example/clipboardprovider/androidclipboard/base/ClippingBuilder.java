package com.example.clipboardprovider.androidclipboard.base;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;

import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.models.Sender;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ClippingBuilder {
  private ClipboardManager clipboardManager;

  public ClippingBuilder(ClipboardManager clipboardManager) {
    this.clipboardManager = clipboardManager;
  }

  public Clipping build() {
    //noinspection ConstantConditions
    return new Clipping("", getPrimaryClip().getItemAt(0).getText().toString(), Sender.Local);
  }

  private ClipData getPrimaryClip() {
    ClipData result = clipboardManager.getPrimaryClip();

    if (result == null) {
      result = new EmptyClipData();
    }

    return result;
  }
}
