package com.omnipaste.omnicommon.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class ContactDto {
  public Long id;
  public String givenName;
  public String familyName;
  public String displayName;
  public String middleName;
  public String photo;
  public List<NumberDto> numbers;

  public ContactDto(Long id) {
    this.id = id;
    this.numbers = new ArrayList<>();
  }

  public String getGivenName() {
    return givenName;
  }

  public ContactDto setGivenName(String givenName) {
    this.givenName = givenName;
    return this;
  }

  public String getFamilyName() {
    return familyName;
  }

  public ContactDto setFamilyName(String familyName) {
    this.familyName = familyName;
    return this;
  }

  public String getDisplayName() {
    return displayName;
  }

  public ContactDto setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public String getMiddleName() {
    return middleName;
  }

  public ContactDto setMiddleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  public String getPhoto() {
    return photo;
  }

  public ContactDto setPhoto(String photo) {
    this.photo = photo;
    return this;
  }

  public ContactDto addNumber(NumberDto number) {
    numbers.add(number);
    return this;
  }

  public List<NumberDto> getNumbers() {
    return numbers;
  }
}
