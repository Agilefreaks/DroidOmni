package com.omnipasteapp.androidclipboard;

import com.google.inject.Inject;

@SuppressWarnings("deprecation")
public class ClipboardManagerWrapper extends Thread implements IClipboardManagerWrapper {

  public static final int POLLING_INTERVAL = 1000;

  @Inject
  private android.text.ClipboardManager _systemClipboardManager;

  private volatile boolean _isCanceled;
  private OnClipChangedListener _listener;
  private String _oldClip;

  public ClipboardManagerWrapper() {
    _isCanceled = false;
    _oldClip = null;
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
    return _systemClipboardManager.getText().toString();
  }

  @Override
  public void setClip(String text) {
    _systemClipboardManager.setText(text);
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
    return _systemClipboardManager.hasText();
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
    }
  }
}