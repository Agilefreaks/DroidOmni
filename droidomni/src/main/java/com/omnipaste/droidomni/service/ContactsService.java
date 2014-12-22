package com.omnipaste.droidomni.service;

import android.os.Bundle;

import com.google.gson.Gson;
import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.interaction.RSACrypto;
import com.omnipaste.eventsprovider.ContactsRepository;
import com.omnipaste.omniapi.resource.v1.Devices;
import com.omnipaste.omniapi.resource.v1.users.Contacts;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.bugfreak.BugFreak;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class ContactsService extends ServiceBase {
  private static final String IDENTIFIER_KEY = "identifier";

  private final NotificationProvider notificationProvider;
  private final ContactsRepository contactsRepository;
  private final SessionService sessionService;
  private final Contacts contacts;
  private final Devices devices;
  private final AtomicBoolean fetching = new AtomicBoolean(false);
  private final PublishSubject<ContactSyncNotification> contactsSubject = PublishSubject.create();
  private Subscription subscription;

  @Inject
  public ContactsService(
    NotificationProvider notificationProvider,
    ContactsRepository contactsRepository,
    SessionService sessionService,
    Contacts contacts,
    Devices devices) {
    this.notificationProvider = notificationProvider;
    this.contactsRepository = contactsRepository;
    this.sessionService = sessionService;
    this.contacts = contacts;
    this.devices = devices;
  }

  @Override
  public void start() {
    if (subscription != null) {
      return;
    }

    subscription = notificationProvider
      .getObservable()
      .filter(new Func1<NotificationDto, Boolean>() {
        @Override
        public Boolean call(NotificationDto notificationDto) {
          return notificationDto.getTarget() == NotificationDto.Target.CONTACTS;
        }
      })
      .flatMap(new Func1<NotificationDto, Observable<RegisteredDeviceDto>>() {
        @Override
        public Observable<RegisteredDeviceDto> call(NotificationDto notificationDto) {
          fetching.set(true);
          contactsSubject.onNext(new ContactSyncNotification(ContactSyncNotification.Status.Started));

          Bundle extra = notificationDto.getExtra();
          String identifier = extra.getString(IDENTIFIER_KEY);

          return devices.get(identifier);
        }
      })
      .flatMap(new Func1<RegisteredDeviceDto, Observable<?>>() {
        @Override
        public Observable<?> call(RegisteredDeviceDto registeredDeviceDto) {
          List<ContactDto> contacts = contactsRepository.findAll();
          String encryptedContacts = "";

          try {
            String gsonContacts = new Gson().toJson(contacts);
            encryptedContacts = RSACrypto.encrypt(gsonContacts).with(registeredDeviceDto.getPublicKey());
          } catch (IOException ignore) {
            BugFreak.beginReport(ignore);
          }

          Observable result =  !encryptedContacts.equals("") ?
            ContactsService.this.contacts.create(sessionService.getRegisteredDeviceDto().getIdentifier(), registeredDeviceDto.getIdentifier(), encryptedContacts) :
            Observable.empty();

          fetching.set(false);
          contactsSubject.onNext(new ContactSyncNotification(ContactSyncNotification.Status.Completed));
          return result;
        }
      })
      .skipWhile(new Func1<Object, Boolean>() {
        @Override
        public Boolean call(Object o) {
          return fetching.get();
        }
      })
      .doOnError(new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
          fetching.set(false);
          contactsSubject.onNext(new ContactSyncNotification(ContactSyncNotification.Status.Failed, throwable));
        }
      })
      .retry()
      .subscribe();
  }

  @Override
  public void stop() {
    subscription.unsubscribe();
    subscription = null;
  }

  public Observable<ContactSyncNotification> getObservable() {
    return contactsSubject;
  }
}
