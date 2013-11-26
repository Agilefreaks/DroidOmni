package com.omnipasteapp.omniclipboard.messaging;

import android.content.Context;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;

import javax.inject.Inject;

public class GoogleMessagingService implements IMessagingService, IHandleRegistration, IHandleUnregister {
  private IConfigurationService _configurationService;
  private GoogleCloudMessaging _gcm;
  private Context _context;

  @Inject
  public GoogleMessagingService(IConfigurationService configurationService, Context context, GoogleCloudMessaging gcm) {
    _configurationService = configurationService;
    _gcm = gcm;
    _context = context;
  }

  @Override
  public boolean connect(String channel, IMessageHandler messageHandler) {
    AsyncRegistrationTask asyncGetRegistrationIdTask = new AsyncRegistrationTask(_context, _gcm, this);
    asyncGetRegistrationIdTask.execute(
        _configurationService.getCommunicationSettings().getRegistrationId(),
        _configurationService.getAppVersion());

    return false;
  }

  @Override
  public void disconnect(String channel) {
    AsyncUnregisterTask asyncUnregisterTask = new AsyncUnregisterTask(_gcm, this);
    asyncUnregisterTask.execute();
  }

  @Override
  public void handleRegistrationSuccess(String registrationId, int appVersion) {
    // store configuration locally
    _configurationService.getCommunicationSettings().setRegistrationId(registrationId);
    _configurationService.updateCommunicationSettings();
    _configurationService.updateAppVersion(appVersion);

    // post to devices on api
  }

  @Override
  public void handleRegistrationError(String error) {
  }

  @Override
  public void handleUnregisterSuccess() {
    _configurationService.getCommunicationSettings().setRegistrationId(null);
    _configurationService.updateCommunicationSettings();

    // post to device on api
  }

  @Override
  public void handleUnregisterError(String error) {
  }
}
