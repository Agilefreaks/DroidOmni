package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.EmptyDto;
import com.omnipaste.phoneprovider.listeners.LocalPhoneStateListener;
import com.omnipaste.phoneprovider.listeners.LocalSmsListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalProviderFacade implements Provider<EmptyDto> {

  public static LocalProviderFacade instance;

  private LocalPhoneStateListener localPhoneStateListener;
  private LocalSmsListener localSmsListener;
  private boolean subscribed = false;

  public static LocalProviderFacade getInstance() {
    return instance;
  }

  @Inject
  public LocalProviderFacade(LocalPhoneStateListener localPhoneStateListener,
                             LocalSmsListener localSmsListener) {
    this.localPhoneStateListener = localPhoneStateListener;
    this.localSmsListener = localSmsListener;

    instance = this;
  }

  @Override
  public Observable<EmptyDto> init(String deviceId) {
    if (!subscribed) {
      localPhoneStateListener.start(deviceId);
      localSmsListener.start(deviceId);
      subscribed = true;
    }

    return Observable.empty();
  }

  @Override
  public void destroy() {
    localPhoneStateListener.stop();
    localSmsListener.stop();
    subscribed = false;
  }
}
