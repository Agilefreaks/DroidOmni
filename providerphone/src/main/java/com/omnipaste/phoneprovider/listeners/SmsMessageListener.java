package com.omnipaste.phoneprovider.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.phoneprovider.ContactsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SmsMessageListener extends BroadcastReceiver implements Listener {
  private final static String EXTRAS_KEY = "pdus";

  private final Context context;
  private final ContactsRepository contactsRepository;
  private final SmsMessages smsMessages;
  private String deviceId;

  @Inject
  public SmsMessageListener(Context context,
                            ContactsRepository contactsRepository,
                            SmsMessages smsMessages) {
    this.context = context;
    this.contactsRepository = contactsRepository;
    this.smsMessages = smsMessages;
  }

  @Override
  public void start(String deviceId) {
    this.deviceId = deviceId;

    IntentFilter filter = new IntentFilter();
    filter.addAction("android.provider.Telephony.SMS_RECEIVED");
    filter.setPriority(999);

    context.registerReceiver(this, filter);
  }

  @Override
  public void stop() {
    context.unregisterReceiver(this);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle extras = intent.getExtras();
    if (extras == null)
      return;

    StringBuilder message = new StringBuilder();
    String fromAddress = "";
    Object[] pdus = (Object[]) extras.get(EXTRAS_KEY);

    for (Object pdu : pdus) {
      SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
      message.append(smsMessage.getMessageBody());
      fromAddress = smsMessage.getOriginatingAddress();
    }

    smsMessages.post(new SmsMessageDto(deviceId)
      .setPhoneNumber(fromAddress)
      .setContactName(contactsRepository.findByPhoneNumber(fromAddress))
      .setContent(message.toString())).subscribe();
  }
}
