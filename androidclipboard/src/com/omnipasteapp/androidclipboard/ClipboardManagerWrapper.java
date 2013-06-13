package com.omnipasteapp.androidclipboard;

import android.text.ClipboardManager;
import com.google.inject.Inject;

public class ClipboardManagerWrapper extends Thread {

  public static final int POLLING_INTERVAL = 1000;

  public interface OnClipChangedListener {
    void onPrimaryClipChanged();
  }

  @Inject
  private ClipboardManager systemClipboardManager;

  private volatile boolean isCanceled;
  private OnClipChangedListener listener;
  private String oldClip;

  public ClipboardManagerWrapper(){
    isCanceled = false;
    oldClip = null;
  }

  @Override
  public void run() {
    oldClip = getCurrentClip();
    while (!isCanceled) {
      try {
        Thread.sleep(POLLING_INTERVAL);
      } catch (InterruptedException ignored) {
      }

      if(clippingChanged()){
        fireClippingChanged();
      }
    }
  }

  public void setOnClipChangedListener(OnClipChangedListener listener){
    this.listener = listener;
  }

  public String getCurrentClip(){
    return systemClipboardManager.getText().toString();
  }

  public void setClip(String text){
    systemClipboardManager.setText(text);
  }

  public boolean clippingChanged() {
    if (hasClipping()) {
      String newClip = getCurrentClip();

      return !newClip.equals(oldClip);
    }

    return false;
  }

  public boolean hasClipping(){
    return systemClipboardManager.hasText();
  }

  public void dispose(){
    isCanceled = true;
    oldClip = null;
    listener = null;
    interrupt();
  }

  private void fireClippingChanged(){
    if(listener != null){
      listener.onPrimaryClipChanged();
    }
  }
}