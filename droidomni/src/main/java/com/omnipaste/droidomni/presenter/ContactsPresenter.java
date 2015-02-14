package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.domain.ContactSyncNotification;
import com.omnipaste.droidomni.prefs.ContactsSyncIndex;
import com.omnipaste.droidomni.prefs.ContactsSynced;
import com.omnipaste.omniapi.resource.v1.user.Contacts;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;
import com.omnipaste.omnicommon.prefs.IntPreference;
import com.omnipaste.phoneprovider.ContactsRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;

@Singleton
public class ContactsPresenter extends Presenter<ContactsPresenter.View> implements Observer<ContactSyncNotification>  {
  public static final int MAX_CONTACTS_FETCH = 10;

  private final PublishSubject<ContactSyncNotification> contactsSubject = PublishSubject.create();
  private final ContactsRepository contactsRepository;
  private final BooleanPreference contactsSynced;
  private final IntPreference contactsSyncIndex;
  private final Contacts contacts;

  private Boolean stopSync = false;

  public interface View {
  }

  @Inject
  public ContactsPresenter(
    ContactsRepository contactsRepository,
    @ContactsSynced BooleanPreference contactsSynced,
    @ContactsSyncIndex IntPreference contactsSyncIndex,
    Contacts contacts) {
    this.contactsRepository = contactsRepository;
    this.contactsSynced = contactsSynced;
    this.contactsSyncIndex = contactsSyncIndex;
    this.contacts = contacts;
  }

  @Override
  public void initialize() {
    if (contactsSynced.get()) {
      return;
    }

    contactsSubject.onNext(new ContactSyncNotification(ContactSyncNotification.Status.Started));

    int index = contactsSyncIndex.get();
    List<ContactDto> contactList = contactsRepository.find(index, MAX_CONTACTS_FETCH);

    while(contactList.size() == MAX_CONTACTS_FETCH && !stopSync) {
      index += contactList.size();
      contacts.create(contactList).subscribe();
      contactList = contactsRepository.find(index, MAX_CONTACTS_FETCH);
    }

    if (contactList.size() != 0) {
      index += contactList.size();
      contacts.create(contactList).subscribe();
    }

    contactsSyncIndex.set(index);
    contactsSynced.set(!stopSync);
    contactsSubject.onNext(new ContactSyncNotification(ContactSyncNotification.Status.Completed));
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    contactsSubject.onCompleted();
    stopSync = true;
  }

  @Override
  public void onCompleted() {
  }

  @Override
  public void onError(Throwable e) {
  }

  @Override
  public void onNext(ContactSyncNotification contactSyncNotification) {
    contactsSubject.onNext(contactSyncNotification);
  }

  public Observable<ContactSyncNotification> getObservable() {
    return contactsSubject;
  }
}
