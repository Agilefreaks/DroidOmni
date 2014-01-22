package com.omnipaste.clipboardprovider.omniclipboard;

import android.content.ClipData;

import rx.Observable;

public interface IOmniClipboardManager {
  public Observable<String> getObservable();

  public ClipData getPrimaryClip();

  public void setPrimaryClip(ClipData clipData);
}
