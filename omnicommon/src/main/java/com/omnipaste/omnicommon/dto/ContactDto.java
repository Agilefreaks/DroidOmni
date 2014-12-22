package com.omnipaste.omnicommon.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class ContactDto {
  public Long id;
  public String first_name;
  public String last_name;
  public String name;
  public String middle_name;
  public String photo;
  public List<NumberDto> numbers;

  public ContactDto(Long id) {
    this.id = id;
    this.numbers = new ArrayList<>();
  }

  public String getFirst_name() {
    return first_name;
  }

  public ContactDto setFirst_name(String first_name) {
    this.first_name = first_name;
    return this;
  }

  public String getLast_name() {
    return last_name;
  }

  public ContactDto setLast_name(String last_name) {
    this.last_name = last_name;
    return this;
  }

  public String getName() {
    return name;
  }

  public ContactDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getMiddle_name() {
    return middle_name;
  }

  public ContactDto setMiddle_name(String middle_name) {
    this.middle_name = middle_name;
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
