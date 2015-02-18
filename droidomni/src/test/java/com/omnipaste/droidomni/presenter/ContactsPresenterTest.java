package com.omnipaste.droidomni.presenter;

import com.omnipaste.omniapi.resource.v1.user.Contacts;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;
import com.omnipaste.omnicommon.prefs.IntPreference;
import com.omnipaste.phoneprovider.ContactsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactsPresenterTest {
  private ContactsPresenter contactsPresenter;

  @Mock public ContactsRepository mockContactsRepository;
  @Mock public BooleanPreference mockContactsSynced;
  @Mock public IntPreference mockContactsSyncIndex;
  @Mock public Contacts mockContacts;

  @Before
  public void context() {
    contactsPresenter = new ContactsPresenter(mockContactsRepository, mockContactsSynced, mockContactsSyncIndex, mockContacts);
    contactsPresenter.setScheduler(Schedulers.immediate());
    contactsPresenter.setObserveOnScheduler(Schedulers.immediate());
  }

  @Test
  public void initializeWhenContactsNotSyncedItWillCallFind() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);
    when(mockContactsRepository.find(0)).thenReturn(Observable.<ContactDto>empty());

    contactsPresenter.initialize();

    verify(mockContactsRepository, times(1)).find(0);
  }

  @Test
  public void initializeWhenNotSyncedWillCreateContacts() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);
    PublishSubject<ContactDto> findSubject = PublishSubject.create();
    when(mockContactsRepository.find(0)).thenReturn(findSubject);

    final List<ContactDto> mockContactsOne = mockContacts(10);
    final List<ContactDto> mockContactsTwo = mockContacts(8);

    contactsPresenter.initialize();
    for (ContactDto contactDto : new ArrayList<ContactDto>() {{
      addAll(mockContactsOne);
      addAll(mockContactsTwo);
    }}) {
      findSubject.onNext(contactDto);
    }
    findSubject.onCompleted();

    verify(mockContacts).create(mockContactsOne);
    verify(mockContacts).create(mockContactsTwo);
  }

  @Test
  public void initializeWhenStopSyncIsTrueWillStop() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);
    PublishSubject<ContactDto> findSubject = PublishSubject.create();
    when(mockContactsRepository.find(0)).thenReturn(findSubject);

    final List<ContactDto> mockContactsOne = mockContacts(10);
    final List<ContactDto> mockContactsTwo = mockContacts(8);

    contactsPresenter.initialize();
    for (ContactDto contactDto : mockContactsOne) {
      findSubject.onNext(contactDto);
    }
    findSubject.onCompleted();

    verify(mockContacts).create(mockContactsOne);
    verify(mockContacts).create(mockContactsTwo);
  }


  @Test
  public void initializeWillSetContactsSynced() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);
    PublishSubject<ContactDto> findSubject = PublishSubject.create();
    when(mockContactsRepository.find(0)).thenReturn(findSubject);

    contactsPresenter.initialize();
    findSubject.onCompleted();

    verify(mockContactsSynced).set(true);
  }

  @Test
  public void initializeWhenContactsSyncedWillNotContactsStartSync() {
    when(mockContactsSynced.get()).thenReturn(true);

    contactsPresenter.initialize();

    verify(mockContactsRepository, never()).find(anyInt());
  }

  private List<ContactDto> mockContacts(int howMany) {
    ArrayList<ContactDto> contacts = new ArrayList<>(howMany);

    for (int i = 0; i < howMany; i++) {
      contacts.add(new ContactDto((long) i));
    }

    return contacts;
  }
}