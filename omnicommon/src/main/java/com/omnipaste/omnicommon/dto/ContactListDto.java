package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class ContactListDto {
  private final String identifier;
  private final String destinationIdentifier;
  private final String contacts;

  public ContactListDto(String identifier, String destinationIdentifier, String contacts) {
    this.identifier = identifier;
    this.destinationIdentifier = destinationIdentifier;
    this.contacts = contacts;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getDestinationIdentifier() {
    return destinationIdentifier;
  }

  public String getContacts() {
    return contacts;
  }
}

