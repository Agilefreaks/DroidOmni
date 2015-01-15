package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.Provider;
import com.omnipaste.omnicommon.dto.EmptyDto;
import com.omnipaste.phoneprovider.actions.SendSmsMessageRequested;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class PhoneProvider implements Provider<EmptyDto> {
  private final SendSmsMessageRequested sendSmsMessageRequested;

  @Inject
  public PhoneProvider(
    SendSmsMessageRequested sendSmsMessageRequested) {
    this.sendSmsMessageRequested = sendSmsMessageRequested;
  }

  @Override
  public Observable<EmptyDto> init(String identifier) {
    sendSmsMessageRequested.init();

    return Observable.empty();
  }

  @Override
  public void destroy() {
    sendSmsMessageRequested.destroy();
  }
}
