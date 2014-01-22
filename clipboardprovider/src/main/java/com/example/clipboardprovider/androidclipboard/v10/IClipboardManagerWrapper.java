package com.example.clipboardprovider.androidclipboard.v10;

public interface IClipboardManagerWrapper extends Runnable {

  public interface OnClipChangedListener {
    void onPrimaryClipChanged();
  }

  void start();

  void setOnClipChangedListener(ClipboardManagerWrapper.OnClipChangedListener listener);

  String getCurrentClip();

  void setClip(String text);

  boolean clippingChanged();

  boolean hasClipping();

  void dispose();
}
