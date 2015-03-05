package com.omnipaste.phoneprovider.receivers;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import javax.inject.Inject;

import rx.functions.Action1;

public class SmsMessageReceived extends BaseReceiver<SmsMessageDto> {
  private final SmsMessages smsMessages;

  @Inject
  public SmsMessageReceived(NotificationProvider notificationProvider, SmsMessages smsMessages) {
    super(notificationProvider);
    this.smsMessages = smsMessages;
  }

  @Override
  protected NotificationDto.Type getType() {
    return NotificationDto.Type.SMS_MESSAGE_RECEIVED;
  }

  @Override
  protected void execute(NotificationDto notificationDto) {
    smsMessages.get(notificationDto.getId()).subscribe(
      new Action1<SmsMessageDto>() {
        @Override
        public void call(SmsMessageDto smsMessageDto) {
          subject.onNext(smsMessageDto);
        }
      });
  }
}
