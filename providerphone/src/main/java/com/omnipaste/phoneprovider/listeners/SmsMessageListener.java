package com.omnipaste.phoneprovider.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omniapi.resource.v1.user.Contacts;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.phoneprovider.ContactsRepository;
import com.omnipaste.phoneprovider.interaction.CreateSmsMessage;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SmsMessageListener extends BroadcastReceiver implements Listener {
  private final ContactsRepository contactsRepository;
  private final SmsMessages smsMessages;
  private final CreateSmsMessage createSmsMessage;
  private final Contacts contacts;
  private String deviceId;
  private Context context;

  @Inject
  public SmsMessageListener(ContactsRepository contactsRepository,
                            SmsMessages smsMessages,
                            CreateSmsMessage createSmsMessage,
                            Contacts contacts) {
    this.contactsRepository = contactsRepository;
    this.smsMessages = smsMessages;
    this.createSmsMessage = createSmsMessage;
    this.contacts = contacts;
  }

  @Override
  public void start(Context context, String deviceId) {
    this.deviceId = deviceId;
    this.context = context;

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
    if (extras == null) {
      return;
    }

    SmsMessageDto smsMessageDto = createSmsMessage.with(extras, deviceId);
    ContactDto contactDto = contactsRepository.findByPhoneNumber(smsMessageDto.getPhoneNumber());

    if (contactDto != null) {
      smsMessageDto
        .setContactId(contactDto.getContactId())
        .setContactName(contactDto.getName());
      contacts.get(contactDto.getContactId()).subscribe();
    }

    smsMessages.post(smsMessageDto).subscribe();
  }
}
