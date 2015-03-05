package com.omnipaste.phoneprovider.receivers;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class PhoneCallReceived extends BaseReceiver<PhoneCallDto> {
  private final PhoneCalls phoneCalls;

  @Inject
  public PhoneCallReceived(NotificationProvider notificationProvider, PhoneCalls phoneCalls) {
    super(notificationProvider);
    this.phoneCalls = phoneCalls;
  }

  @Override
  protected void execute(NotificationDto notificationDto) {
    phoneCalls.get(notificationDto.getId()).subscribe(new Action1<PhoneCallDto>() {
      @Override
      public void call(PhoneCallDto phoneCallDto) {
        subject.onNext(phoneCallDto);
      }
    });
  }

  @Override
  protected NotificationDto.Type getType() {
    return NotificationDto.Type.PHONE_CALL_RECEIVED;
  }
}
