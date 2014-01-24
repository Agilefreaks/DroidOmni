package com.omnipaste.clipboardprovider;

import com.omnipaste.omnicommon.dto.ClippingDto;

import rx.Observable;

public interface IClipboardProvider {
  public Observable<ClippingDto> getObservable(final String channel, final String identifier);
}
