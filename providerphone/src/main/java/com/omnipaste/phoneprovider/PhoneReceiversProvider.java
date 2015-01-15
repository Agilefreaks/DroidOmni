package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.EmptyDto;
import com.omnipaste.phoneprovider.receivers.PhoneCallReceived;
import com.omnipaste.phoneprovider.receivers.SmsMessageReceived;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class PhoneReceiversProvider implements Provider<EmptyDto> {
  private final PhoneCallReceived phoneCallReceived;
  private final SmsMessageReceived smsMessageReceived;

  @Inject
  public PhoneReceiversProvider(PhoneCallReceived phoneCallReceived, SmsMessageReceived smsMessageReceived) {
    this.phoneCallReceived = phoneCallReceived;
    this.smsMessageReceived = smsMessageReceived;
  }

  @Override
  public Observable<EmptyDto> init(String identifier) {
    phoneCallReceived.init();
    smsMessageReceived.init();

    return Observable.empty();
  }

  @Override
  public void destroy() {
    phoneCallReceived.destroy();
    smsMessageReceived.destroy();
  }
}
