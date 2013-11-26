package com.omnipasteapp.omniclipboard.messaging;

import android.content.Context;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;

import javax.inject.Inject;

public class GoogleMessagingService implements IMessagingService, IHandleRegistrationId {
  private IConfigurationService _configurationService;
  private Context _context;

  @Inject
  public GoogleMessagingService(IConfigurationService configurationService, Context context) {
    _configurationService = configurationService;
    _context = context;
  }

  @Override
  public boolean connect(String channel, IMessageHandler messageHandler) {
    AsyncGetRegistrationIdTask asyncGetRegistrationIdTask = new AsyncGetRegistrationIdTask(_configurationService, _context, this);
    asyncGetRegistrationIdTask.execute();

    return false;
  }

  @Override
  public void disconnect(String channel) {
  }

  @Override
  public void sendAsync(String channel, String message, IMessageHandler messageHandler) {
  }

  @Override
  public void setRegistrationId(String registrationId) {

  }
}
