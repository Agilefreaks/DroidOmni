package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.EmptyDto;
import com.omnipaste.phoneprovider.actions.EndPhoneCallRequested;
import com.omnipaste.phoneprovider.actions.SendSmsMessageRequested;
import com.omnipaste.phoneprovider.actions.StartPhoneCallRequested;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class PhoneActionsProvider implements Provider<EmptyDto> {
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
  public Observable<EmptyDto> init(String identifier) {
    sendSmsMessageRequested.init();
    startPhoneCallRequested.init();
    endPhoneCallRequested.init();

    return Observable.empty();
  }

  @Override
  public void destroy() {
    sendSmsMessageRequested.destroy();
    startPhoneCallRequested.destroy();
    endPhoneCallRequested.destroy();
  }
}
