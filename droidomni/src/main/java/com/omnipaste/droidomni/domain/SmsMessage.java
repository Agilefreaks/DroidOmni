package com.omnipaste.droidomni.domain;

import com.omnipaste.omnicommon.dto.SmsMessageDto;

public class SmsMessage extends Command<SmsMessageDto> {
  public static SmsMessage add(SmsMessageDto smsMessageDto) {
    return new SmsMessage(smsMessageDto, Action.ADD);
  }

  public static SmsMessage remove(SmsMessageDto smsMessageDto) {
    return new SmsMessage(smsMessageDto, Action.REMOVE);
  }

  protected SmsMessage(SmsMessageDto item, Action action) {
    super(item, action);
  }
}
