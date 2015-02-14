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
  }

  @Test
  public void initializeWhenContactsNotSyncedItWillCallFind() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);

    contactsPresenter.initialize();

    verify(mockContactsRepository, times(1)).find(0, ContactsPresenter.MAX_CONTACTS_FETCH);
  }

  @Test
  public void initializeWillCallFindUntilItReturnedArraySizeLowerThenMaxFetch() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);
    when(mockContactsRepository.find(0, ContactsPresenter.MAX_CONTACTS_FETCH)).thenReturn(mockContacts(ContactsPresenter.MAX_CONTACTS_FETCH));
    when(mockContactsRepository.find(10, ContactsPresenter.MAX_CONTACTS_FETCH)).thenReturn(mockContacts(ContactsPresenter.MAX_CONTACTS_FETCH - 1));

    contactsPresenter.initialize();

    verify(mockContactsRepository).find(0, ContactsPresenter.MAX_CONTACTS_FETCH);
    verify(mockContactsRepository).find(10, ContactsPresenter.MAX_CONTACTS_FETCH);
  }

  @Test
  public void initializeWillSetContactsSynced() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);

    contactsPresenter.initialize();

    verify(mockContactsSynced).set(true);
  }

  @Test
  public void initializeWillUpdateContactsSyncIndex() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);
    when(mockContactsRepository.find(0, ContactsPresenter.MAX_CONTACTS_FETCH)).thenReturn(mockContacts(ContactsPresenter.MAX_CONTACTS_FETCH));
    when(mockContactsRepository.find(10, ContactsPresenter.MAX_CONTACTS_FETCH)).thenReturn(mockContacts(ContactsPresenter.MAX_CONTACTS_FETCH - 1));

    contactsPresenter.initialize();

    verify(mockContactsSyncIndex).set(19);
  }

  @Test
  public void initializeWillSaveContacts() {
    when(mockContactsSynced.get()).thenReturn(false);
    when(mockContactsSyncIndex.get()).thenReturn(0);
    List<ContactDto> contacts1 = mockContacts(ContactsPresenter.MAX_CONTACTS_FETCH);
    when(mockContactsRepository.find(0, ContactsPresenter.MAX_CONTACTS_FETCH)).thenReturn(contacts1);
    List<ContactDto> contacts2 = mockContacts(ContactsPresenter.MAX_CONTACTS_FETCH - 1);
    when(mockContactsRepository.find(10, ContactsPresenter.MAX_CONTACTS_FETCH)).thenReturn(contacts2);

    contactsPresenter.initialize();

    verify(mockContacts).create(contacts1);
    verify(mockContacts).create(contacts2);
  }

  @Test
  public void initializeWhenContactsSyncedWillNotContactsStartSync() {
    when(mockContactsSynced.get()).thenReturn(true);

    contactsPresenter.initialize();

    verify(mockContactsRepository, never()).find(anyInt(), anyInt());
  }

  private List<ContactDto> mockContacts(int howMany) {
    ArrayList<ContactDto> contacts = new ArrayList<>(howMany);

    for(int i = 0; i < howMany; i++) {
      contacts.add(new ContactDto((long) i));
    }

    return contacts;
  }
}