package com.omnipaste.omnicommon.dto;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ContactDtoTest {
  private ContactDto contactDto1;
  private ContactDto contactDto2;

  @Before
  public void context() {
    contactDto1 = new ContactDto().setFirstName("Ion").setLastName("din Deal").setImage("42").addNumber(new NumberDto("123", "work"));
    contactDto2 = new ContactDto().setFirstName("Ion").setLastName("din Deal").setImage("42").addNumber(new NumberDto("123", "work"));
  }

  @Test
  public void equalsWithTheSameFirstNameLastNameImageAndPhoneNumbersWillReturnTrue() {
    assertThat(contactDto1, equalTo(contactDto2));
  }

  @Test
  public void equalsDifferentFirstNameWillReturnFalse() {
    contactDto1.setFirstName(contactDto2.getFirstName() + "modification");

    assertThat(contactDto1, not(equalTo(contactDto2)));
  }

  @Test
  public void equalsDifferentLastNameWillReturnFalse() {
    contactDto1.setLastName(contactDto2.getLastName() + "modification");

    assertThat(contactDto1, not(equalTo(contactDto2)));
  }

  @Test
  public void equalsDifferentImageWillReturnFalse() {
    contactDto1.setImage(contactDto2.getImage() + "modification");

    assertThat(contactDto1, not(equalTo(contactDto2)));
  }

  @Test
  public void equalsDifferentPhoneNumbersCountWillReturnFalse() {
    contactDto1.addNumber(new NumberDto("123", "work"));

    assertThat(contactDto1, not(equalTo(contactDto2)));
  }

  @Test
  public void equalsDifferentPhoneNumbersWillReturnFalse() {
    contactDto1.getPhoneNumbers().get(0).setNumber(contactDto2.getPhoneNumbers().get(0).getNumber() + "modification");

    assertThat(contactDto1, not(equalTo(contactDto2)));
  }

  @Test
  public void equalsFirstNameIsNullReturnsFalse() {
    contactDto1.setFirstName(null);

    assertThat(contactDto1, not(equalTo(contactDto2)));
  }

  @Test
  public void equalsFirstNameIsNullOnBoth() {
    contactDto1.setFirstName(null);
    contactDto2.setFirstName(null);

    assertThat(contactDto1, equalTo(contactDto2));
  }
}