package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.phoneprovider.receivers.PhoneCallReceived;
import com.omnipaste.phoneprovider.receivers.SmsMessageReceived;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.Unit;
import rx.Observable;

@Singleton
public class PhoneReceiversProvider implements Provider<Unit> {
  private final PhoneCallReceived phoneCallReceived;
  private final SmsMessageReceived smsMessageReceived;

  @Inject
  public PhoneReceiversProvider(PhoneCallReceived phoneCallReceived, SmsMessageReceived smsMessageReceived) {
    this.phoneCallReceived = phoneCallReceived;
    this.smsMessageReceived = smsMessageReceived;
  }

  @Override
  public Observable<Unit> init(String deviceId) {
    phoneCallReceived.init(deviceId);
    smsMessageReceived.init(deviceId);

    return Observable.empty();
  }

  @Override
  public void destroy() {
    phoneCallReceived.destroy();
    smsMessageReceived.destroy();
  }
}
