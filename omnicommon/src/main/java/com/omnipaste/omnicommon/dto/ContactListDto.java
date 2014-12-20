package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class ContactListDto {
  private final String source_identifier;
  private final String identifier;
  private final String contacts;

  public ContactListDto(String source_identifier, String identifier, String contacts) {
    this.source_identifier = source_identifier;
    this.identifier = identifier;
    this.contacts = contacts;
  }

  public String getSourceIdentifier() {
    return source_identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getContacts() {
    return contacts;
  }
}

