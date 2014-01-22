package com.omnipaste.clipboardprovider.omniclipboard;

import com.omnipaste.omnicommon.dto.ClippingDto;

import rx.Observable;

public interface IOmniClipboardManager {
  public Observable<String> getObservable();

  public Observable<ClippingDto> getPrimaryClip();

  public void setPrimaryClip(ClippingDto clippingDto);
}
