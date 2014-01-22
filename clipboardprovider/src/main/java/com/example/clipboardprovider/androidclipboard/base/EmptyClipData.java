package com.example.clipboardprovider.androidclipboard.base;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class EmptyClipData extends ClipData {
  public EmptyClipData() {
    super("", new String[]{""}, new Item(""));
  }
}
