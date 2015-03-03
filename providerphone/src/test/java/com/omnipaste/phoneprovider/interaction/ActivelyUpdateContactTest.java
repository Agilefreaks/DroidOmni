package com.omnipaste.phoneprovider.interaction;

import com.omnipaste.omniapi.resource.v1.user.Contacts;
import com.omnipaste.omnicommon.dto.ContactDto;
import com.omnipaste.phoneprovider.ContactsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivelyUpdateContactTest {
  private ActivelyUpdateContact activelyUpdateContact;

  @Mock public Contacts mockContacts;
  @Mock public ContactsRepository mockContactRepository;

  @Before
  public void context() {
    activelyUpdateContact = new ActivelyUpdateContact(mockContacts, mockContactRepository);
  }

  @Test
  public void fromPhoneNumberWhenThereIsContactInRepositoryReturnsNewContact() {
    when(mockContactRepository.findByPhoneNumber("42")).thenReturn(null);

    assertThat(activelyUpdateContact.fromPhoneNumber("42"), isA(ContactDto.class));
  }
}