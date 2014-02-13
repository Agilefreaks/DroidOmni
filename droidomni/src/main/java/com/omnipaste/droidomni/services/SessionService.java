package com.omnipaste.droidomni.services;

public interface SessionService {
  void login(String channel);

  void logout();

  Boolean isLogged();

  String getChannel();
}
