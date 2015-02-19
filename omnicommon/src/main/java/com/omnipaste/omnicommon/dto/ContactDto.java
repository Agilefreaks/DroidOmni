package com.omnipaste.omnicommon.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class ContactDto {
  private Long id;
  private Long contactId;
  private String deviceId;
  private String firstName;
  private String lastName;
  private String name;
  private String middleName;
  private String image;
  private List<NumberDto> phoneNumbers;

  public ContactDto(Long contactId) {
    this.contactId = contactId;
    this.phoneNumbers = new ArrayList<>();
  }

  public ContactDto() {
  }

  public Long getContactId() {
    return contactId;
  }

  public ContactDto setContactId(Long contactId) {
    this.contactId = contactId;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public ContactDto setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public ContactDto setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getName() {
    return name;
  }

  public ContactDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getMiddleName() {
    return middleName;
  }

  public ContactDto setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public String getImage() {
    return image;
  }

  public ContactDto setImage(String image) {
    this.image = image;
    return this;
  }

  public ContactDto addNumber(NumberDto number) {
    phoneNumbers.add(number);
    return this;
  }

  public List<NumberDto> getPhoneNumbers() {
    return phoneNumbers;
  }
}
