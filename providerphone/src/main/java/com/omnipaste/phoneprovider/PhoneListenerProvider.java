package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.phoneprovider.listeners.PhoneStateListener;
import com.omnipaste.phoneprovider.listeners.SmsMessageListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.Unit;
import rx.Observable;

@Singleton
public class PhoneListenerProvider implements Provider<Unit> {
  private PhoneStateListener phoneStateListener;
  private SmsMessageListener smsMessageListener;
  private boolean subscribed = false;

  @Inject
  public PhoneListenerProvider(PhoneStateListener phoneStateListener,
                               SmsMessageListener smsMessageListener) {
    this.phoneStateListener = phoneStateListener;
    this.smsMessageListener = smsMessageListener;
  }

  @Override
  public Observable<Unit> init(String deviceId) {
    if (!subscribed) {
      phoneStateListener.start(deviceId);
      smsMessageListener.start(deviceId);
      subscribed = true;
    }

    return Observable.empty();
  }

  @Override
  public void destroy() {
    phoneStateListener.stop();
    smsMessageListener.stop();
    subscribed = false;
  }
}
