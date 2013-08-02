package com.omnipasteapp.androidclipboard.v10;

import android.annotation.TargetApi;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressWarnings("deprecation")
public class ClipboardManagerWrapper extends Thread implements IClipboardManagerWrapper {

  public static final int POLLING_INTERVAL = 1000;

  private android.text.ClipboardManager _clipboardManager;
  private volatile boolean _isCanceled;
  private OnClipChangedListener _listener;
  private String _oldClip;

  public ClipboardManagerWrapper(android.text.ClipboardManager clipboardManager) {
    _isCanceled = false;
    _oldClip = null;
    _clipboardManager = clipboardManager;
  }

  @Override
  public void run() {
    _oldClip = getCurrentClip();
    while (!_isCanceled) {
      try {
        Thread.sleep(POLLING_INTERVAL);
      } catch (InterruptedException ignored) {
      }

      if (clippingChanged()) {
        fireClippingChanged();
      }
    }
  }

  @Override
  public void start() {
    if (!isAlive()) {
      super.start();

    }
  }

  @Override
  public void setOnClipChangedListener(OnClipChangedListener listener) {
    this._listener = listener;
  }

  @Override
  public String getCurrentClip() {
    return _clipboardManager.getText().toString();
  }

  @Override
  public void setClip(String text) {
    _clipboardManager.setText(text);
  }

  @Override
  public boolean clippingChanged() {
    if (hasClipping()) {
      String newClip = getCurrentClip();

      return !newClip.equals(_oldClip);
    }

    return false;
  }

  @Override
  public boolean hasClipping() {
    return _clipboardManager.hasText();
  }

  @Override
  public void dispose() {
    _isCanceled = true;
    _oldClip = null;
    _listener = null;
    interrupt();
  }

  private void fireClippingChanged() {
    if (_listener != null) {
      _listener.onPrimaryClipChanged();
      _oldClip = getCurrentClip();
    }
  }
}