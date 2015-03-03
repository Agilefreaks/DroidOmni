package com.omnipaste.omnicommon.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class ContactDto {
  private String id;
  private Long contactId;
  private String deviceId;
  private String firstName;
  private String lastName;
  private String name;
  private String middleName;
  private String image;
  private List<NumberDto> phoneNumbers;

  public ContactDto() {
    this.phoneNumbers = new ArrayList<>();
  }

  public ContactDto(Long contactId) {
    this();
    this.contactId = contactId;
  }

  public String getId() {
    return id;
  }

  public ContactDto setId(String id) {
    this.id = id;
    return this;
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

  @SuppressWarnings("StringEquality")
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ContactDto)) {
      return false;
    }

    ContactDto other = (ContactDto) o;

    return this.getFirstName() == other.getFirstName() &&
      this.getLastName() == other.getLastName() &&
      this.getImage() == other.getImage() &&
      equalsPhoneNumbers(other);
  }

  private boolean equalsLastName(ContactDto other) {
    String otherLastName = other.getLastName();
    return firstName != null && otherLastName != null && firstName.equals(otherLastName);
  }

  private boolean equalsPhoneNumbers(ContactDto other) {
    if (!(this.phoneNumbers.size() == other.phoneNumbers.size())) {
      return false;
    }

    for(int index = 0; index < this.getPhoneNumbers().size(); index++) {
      if(!this.getPhoneNumbers().get(index).equals(other.getPhoneNumbers().get(index))) {
        return false;
      }
    }

    return true;
  }
}
