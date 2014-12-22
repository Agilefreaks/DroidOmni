package com.omnipaste.omnicommon.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class ContactDto {
  public Long id;
  public String firstName;
  public String lastName;
  public String name;
  public String middleName;
  public String photo;
  public List<NumberDto> numbers;

  public ContactDto(Long id) {
    this.id = id;
    this.numbers = new ArrayList<>();
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
