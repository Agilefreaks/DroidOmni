package com.omnipaste.phoneprovider.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omniapi.resource.v1.user.Contacts;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.phoneprovider.ContactsRepository;
import com.omnipaste.phoneprovider.interaction.CreateSmsMessage;

import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

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

    SmsMessageDto smsMessageDto = createSmsMessage.with(extras).setDeviceId(deviceId);
    final ContactDto contactDto = contactsRepository.findByPhoneNumber(smsMessageDto.getPhoneNumber());

    if (contactDto != null) {
      smsMessageDto
        .setContactId(contactDto.getContactId())
        .setContactName(contactDto.getName());

      contacts.get(contactDto.getContactId())
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends ContactDto>>() {
          @Override
          public Observable<? extends ContactDto> call(Throwable throwable) {
            Observable<ContactDto> result = Observable.empty();
            if (isNotFound(throwable)) {
              result = contactsRepository
                .find(contactDto.getContactId())
                .flatMap(new Func1<ContactDto, Observable<ContactDto>>() {
                  @Override public Observable<ContactDto> call(ContactDto contactDto) {
                    return contacts.create(contactDto);
                  }
                });
            }

            return result;
          }
        })
        .subscribe(
          new Action1<ContactDto>() {
            @Override
            public void call(ContactDto contactDto) {
            }
          }
        );
    }

    smsMessages.post(smsMessageDto).subscribe();
  }

  private boolean isNotFound(Throwable throwable) {
    return throwable instanceof RetrofitError &&
      ((RetrofitError) throwable).getResponse() != null &&
      ((RetrofitError) throwable).getResponse().getStatus() == HttpStatus.SC_NOT_FOUND;
  }
}
