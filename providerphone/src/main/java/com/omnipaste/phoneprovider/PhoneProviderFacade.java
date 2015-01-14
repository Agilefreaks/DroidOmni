package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.EmptyDto;
import com.omnipaste.phoneprovider.listeners.OmniPhoneStateListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class PhoneProviderFacade implements Provider<EmptyDto> {
  private final OmniPhoneStateListener phoneStateListener;
  private boolean subscribed = false;

  @Inject
  public PhoneProviderFacade(OmniPhoneStateListener phoneStateListener) {
    this.phoneStateListener = phoneStateListener;
  }

  @Override
  public Observable<EmptyDto> init(final String deviceId) {
    if (!subscribed) {
      phoneStateListener.start(deviceId);

      subscribed = true;
    }

    return Observable.empty();
  }

  @Override
  public void destroy() {
    subscribed = false;
    phoneStateListener.stop();
  }
}
