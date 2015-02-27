package com.omnipaste.phoneprovider.interaction;

import android.os.Bundle;
import android.telephony.SmsMessage;

import com.omnipaste.omnicommon.dto.SmsMessageDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreateSmsMessage {
  public final static String EXTRAS_KEY = "pdus";

  @Inject
  public CreateSmsMessage() {
  }

  public SmsMessageDto with(Bundle extras) {
    StringBuilder message = new StringBuilder();
    String fromAddress = "";
    Object[] pdus = (Object[]) extras.get(EXTRAS_KEY);

    for (Object pdu : pdus) {
      SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
      message.append(smsMessage.getMessageBody());
      fromAddress = smsMessage.getOriginatingAddress();
    }

    return new SmsMessageDto()
      .setPhoneNumber(fromAddress)
      .setContent(message.toString());
  }
}
