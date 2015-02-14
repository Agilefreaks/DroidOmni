package com.omnipaste.omniapi.dto;

import com.omnipaste.omnicommon.dto.ContactDto;

import org.junit.Before;
import org.junit.Test;

import fj.data.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class RequestListTest {
  private RequestList<ContactDto> subject;
  private ContactDto firstContact;

  @Before
  public void context() {
    firstContact = new ContactDto(42L).setFirstName("Tom").setLastName("Diakite");
    ContactDto secondContact = new ContactDto(43L).setFirstName("Florence").setLastName("The Machine");
    subject = RequestList.buildFromContacts(List.list(firstContact, secondContact));
  }

  @Test
  public void buildForContactsWillHaveTheCorrectSize() {
    assertThat(subject.size(), equalTo(2));
  }

  @Test
  public void buildForContactsWillSetMethodToPOST() {
    assertThat(subject.get(0).getMethod(), equalTo("POST"));
    assertThat(subject.get(1).getMethod(), equalTo("POST"));
  }

  @Test
  public void buildForContactsWillSetPathToContacts() {
    assertThat(subject.get(0).getPath(), equalTo("/api/v1/user/contacts"));
    assertThat(subject.get(1).getPath(), equalTo("/api/v1/user/contacts"));
  }

  @Test
  public void buildForContactsWillSetBodyToJson() {
    assertThat(subject.get(0).getBody(), equalTo(firstContact));
  }
}