package com.omnipaste.omnicommon.dto;

import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
public class UserDto {
  private Date contactsUpdatedAt;
  private String email;

  public UserDto() {
  }

  public Date getContactsUpdatedAt() {
    return contactsUpdatedAt;
  }

  public void setContactsUpdatedAt(Date contactsUpdatedAt) {
    this.contactsUpdatedAt = contactsUpdatedAt;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
