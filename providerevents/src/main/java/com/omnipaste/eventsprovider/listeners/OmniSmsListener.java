package com.omnipaste.eventsprovider.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.omnipaste.eventsprovider.TelephonyNotificationsProvider;
import com.omnipaste.omnicommon.dto.IncomingSmsEventDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

public class  OmniSmsListener extends BroadcastReceiver {
  private final static String EXTRAS_KEY = "pdus";

  public OmniSmsListener() {}

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle extras = intent.getExtras();
    if (extras == null)
      return;

    Object[] pdus = (Object[]) extras.get(EXTRAS_KEY);

    for (Object pdu : pdus) {
      SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
      String fromAddress = message.getOriginatingAddress();

      TelephonyEventDto telephonyEventDto = new TelephonyEventDto(
          TelephonyEventDto.TelephonyEventType.incomingSms,
          new IncomingSmsEventDto()
              .setPhoneNumber(fromAddress)
              .setContent(message.getMessageBody()));
      post(telephonyEventDto);
    }
  }

  private void post(TelephonyEventDto telephonyEventDto) {
    TelephonyNotificationsProvider receiver = TelephonyNotificationsProvider.getInstance();

    if (receiver != null) {
      receiver.post(telephonyEventDto);
    }
  }
}
