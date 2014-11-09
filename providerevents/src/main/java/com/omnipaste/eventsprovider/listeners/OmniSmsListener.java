package com.omnipaste.eventsprovider.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.omnipaste.omnicommon.dto.IncomingSmsEventDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OmniSmsListener extends BroadcastReceiver implements Listener {
  private final static String EXTRAS_KEY = "pdus";
  private EventsReceiver receiver;
  private Context context;

  @Inject
  public OmniSmsListener(Context context) {
    this.context = context;
  }

  public void start(EventsReceiver receiver) {
    this.receiver = receiver;
    IntentFilter filter = new IntentFilter();
    filter.addAction("android.provider.Telephony.SMS_RECEIVED");
    filter.setPriority(999);

    context.registerReceiver(this, filter);
  }

  public void stop() {
    context.unregisterReceiver(this);
  }

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
      receiver.post(telephonyEventDto);
    }
  }
}
