package com.omnipaste.clipboardprovider;

import com.omnipaste.omnicommon.dto.ClippingDto;

import rx.Observable;

public interface IClipboardManager {
  public Observable<ClippingDto> getObservable();

  public ClippingDto setPrimaryClip(ClippingDto clippingDto);

  public void onPrimaryClipChanged();
}
