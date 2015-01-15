package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.EmptyDto;
import com.omnipaste.phoneprovider.listeners.OmniPhoneStateListener;
import com.omnipaste.phoneprovider.listeners.OmniSmsListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class OmniProviderFacade implements Provider<EmptyDto> {
  private final OmniPhoneStateListener phoneStateListener;
  private final OmniSmsListener omniSmsListener;
  private boolean subscribed = false;

  @Inject
  public OmniProviderFacade(
    OmniPhoneStateListener phoneStateListener,
    OmniSmsListener omniSmsListener) {
    this.phoneStateListener = phoneStateListener;
    this.omniSmsListener = omniSmsListener;
  }

  @Override
  public Observable<EmptyDto> init(final String deviceId) {
    if (!subscribed) {
      phoneStateListener.start(deviceId);
      omniSmsListener.start(deviceId);

      subscribed = true;
    }

    return Observable.empty();
  }

  @Override
  public void destroy() {
    phoneStateListener.stop();
    omniSmsListener.stop();
    subscribed = false;
  }
}
