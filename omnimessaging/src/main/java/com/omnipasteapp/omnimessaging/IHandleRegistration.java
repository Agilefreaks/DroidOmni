package com.omnipasteapp.omnimessaging;

public interface IHandleRegistration {
  void handleRegistrationSuccess(String registrationId, int appVersion);

  void handleRegistrationError(String error);
}
