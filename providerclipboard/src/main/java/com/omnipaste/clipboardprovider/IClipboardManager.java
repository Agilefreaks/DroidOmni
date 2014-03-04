package com.omnipaste.clipboardprovider;

import com.omnipaste.omnicommon.dto.ClippingDto;

import rx.Observable;

public interface IClipboardManager {
  public Observable<String> getObservable();

  public Observable<ClippingDto> getPrimaryClip(String channel);

  public ClippingDto setPrimaryClip(String channel, ClippingDto clippingDto);
}
