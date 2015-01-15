package com.omnipaste.droidomni.service;

import com.google.gson.Gson;
import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.interaction.RSACrypto;
import com.omnipaste.omniapi.resource.v1.user.Contacts;
import com.omnipaste.omniapi.resource.v1.user.Devices;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.dto.DeviceDto;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.ContactsRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class ContactsService extends ServiceBase {
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
          return false;
        }
      })
      .flatMap(new Func1<NotificationDto, Observable<DeviceDto>>() {
        @Override
        public Observable<DeviceDto> call(NotificationDto notificationDto) {
          fetching.set(true);
          contactsSubject.onNext(new ContactSyncNotification(ContactSyncNotification.Status.Started));

          String identifier = "";

          return devices.get(identifier);
        }
      })
      .flatMap(new Func1<DeviceDto, Observable<?>>() {
        @Override
        public Observable<?> call(DeviceDto deviceDto) {
          List<ContactDto> contacts = contactsRepository.findAll();
          String encryptedContacts = "";

          try {
            String gsonContacts = new Gson().toJson(contacts);
            encryptedContacts = RSACrypto.encrypt(gsonContacts).with(deviceDto.getPublicKey());
          } catch (IOException ignore) {
          }

          Observable result =  !encryptedContacts.equals("") ?
            ContactsService.this.contacts.create(sessionService.getDeviceDto().getId(), deviceDto.getId(), encryptedContacts) :
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
