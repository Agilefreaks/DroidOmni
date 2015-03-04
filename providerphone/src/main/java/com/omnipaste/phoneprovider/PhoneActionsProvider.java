package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.phoneprovider.actions.EndPhoneCallRequested;
import com.omnipaste.phoneprovider.actions.SendSmsMessageRequested;
import com.omnipaste.phoneprovider.actions.StartPhoneCallRequested;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.Unit;
import rx.Observable;

@Singleton
public class PhoneActionsProvider implements Provider<Unit> {
  private final SendSmsMessageRequested sendSmsMessageRequested;
  private final StartPhoneCallRequested startPhoneCallRequested;
  private EndPhoneCallRequested endPhoneCallRequested;

  @Inject
  public PhoneActionsProvider(SendSmsMessageRequested sendSmsMessageRequested,
                              StartPhoneCallRequested startPhoneCallRequested,
                              EndPhoneCallRequested endPhoneCallRequested) {
    this.sendSmsMessageRequested = sendSmsMessageRequested;
    this.startPhoneCallRequested = startPhoneCallRequested;
    this.endPhoneCallRequested = endPhoneCallRequested;
  }

  @Override
  public Observable<Unit> init(String deviceId) {
    sendSmsMessageRequested.init(deviceId);
    startPhoneCallRequested.init(deviceId);
    endPhoneCallRequested.init(deviceId);

    return Observable.empty();
  }

  @Override
  public void destroy() {
    sendSmsMessageRequested.destroy();
    startPhoneCallRequested.destroy();
    endPhoneCallRequested.destroy();
  }
}
