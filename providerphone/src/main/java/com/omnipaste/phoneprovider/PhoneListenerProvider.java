package com.omnipaste.phoneprovider;

import android.content.Context;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.phoneprovider.listeners.PhoneStateListener;
import com.omnipaste.phoneprovider.listeners.SmsMessageListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.Unit;
import rx.Observable;

@Singleton
public class PhoneListenerProvider implements Provider<Unit> {
  private final Context context;
  private final PhoneStateListener phoneStateListener;
  private final SmsMessageListener smsMessageListener;
  private boolean subscribed = false;

  @Inject
  public PhoneListenerProvider(Context context,
                               PhoneStateListener phoneStateListener,
                               SmsMessageListener smsMessageListener) {
    this.context = context;
    this.phoneStateListener = phoneStateListener;
    this.smsMessageListener = smsMessageListener;
  }

  @Override
  public Observable<Unit> init(String deviceId) {
    if (!subscribed) {
      phoneStateListener.start(context, deviceId);
      smsMessageListener.start(context, deviceId);
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
