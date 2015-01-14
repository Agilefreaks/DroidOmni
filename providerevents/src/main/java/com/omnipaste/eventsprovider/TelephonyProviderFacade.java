package com.omnipaste.eventsprovider;

import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;
import com.omnipaste.eventsprovider.listeners.OmniSmsListener;
import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.NotificationDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class TelephonyProviderFacade implements Provider<NotificationDto> {

  public static TelephonyProviderFacade instance;

  private OmniPhoneStateListener omniPhoneStateListener;
  private OmniSmsListener smsListener;
  private boolean subscribed = false;

  public static TelephonyProviderFacade getInstance() {
    return instance;
  }

  @Inject
  public TelephonyProviderFacade(OmniPhoneStateListener omniPhoneStateListener,
                                 OmniSmsListener smsListener) {
    this.omniPhoneStateListener = omniPhoneStateListener;
    this.smsListener = smsListener;

    instance = this;
  }

  @Override
  public Observable<NotificationDto> init(String deviceId) {
    if (!subscribed) {
      omniPhoneStateListener.start(deviceId);
      smsListener.start(deviceId);
      subscribed = true;
    }

    return Observable.empty();
  }

  @Override
  public void destroy() {
    omniPhoneStateListener.stop();
    smsListener.stop();
    subscribed = false;
  }
}
