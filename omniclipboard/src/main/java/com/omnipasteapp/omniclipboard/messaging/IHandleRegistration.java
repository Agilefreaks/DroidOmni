package com.omnipasteapp.omniclipboard.messaging;

public interface IHandleRegistration {
  void handleRegistrationSuccess(String registrationId, int appVersion);

  void handleRegistrationError(String error);
}
