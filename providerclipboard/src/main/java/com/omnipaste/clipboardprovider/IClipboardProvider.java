package com.omnipaste.clipboardprovider;

import com.omnipaste.omnicommon.dto.ClippingDto;

import rx.Observable;

public interface IClipboardProvider {
  Observable<ClippingDto> subscribe(final String channel, final String identifier);

  void unsubscribe();
}
