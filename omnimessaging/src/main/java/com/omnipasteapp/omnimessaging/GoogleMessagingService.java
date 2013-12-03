package com.omnipasteapp.omnimessaging;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipasteapp.omniapi.IOmniApi;
import com.omnipasteapp.omniapi.resources.IDeleteDeviceCompleteHandler;
import com.omnipasteapp.omniapi.resources.ISaveDeviceCompleteHandler;
import com.omnipasteapp.omnicommon.interfaces.IClipboardProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;

import javax.inject.Inject;

public class GoogleMessagingService implements IMessagingService, IHandleRegistration, IHandleUnregister, ISaveDeviceCompleteHandler, IDeleteDeviceCompleteHandler {
  private IConfigurationService _configurationService;
  private GoogleCloudMessaging _gcm;
  private Context _context;
  private String _registrationId;

  @Inject
  public IClipboardProvider clipboardProvider;

  @Inject
  public IOmniApi omniApi;

  @Inject
  public GoogleMessagingService(IConfigurationService configurationService, Context context, GoogleCloudMessaging gcm) {
    _configurationService = configurationService;
    _gcm = gcm;
    _context = context;
  }

  @Override
  public boolean connect(String channel) {
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

    _registrationId = registrationId;

    // post to devices on api
    omniApi.devices().saveAsync(_registrationId, this);
  }

  @Override
  public void handleRegistrationError(String error) {
  }

  @Override
  public void handleUnregisterSuccess() {
    _configurationService.getCommunicationSettings().setRegistrationId(null);
    _configurationService.updateCommunicationSettings();

    // post to device on api
    omniApi.devices().deleteAsync(_registrationId, this);
  }

  @Override
  public void handleUnregisterError(String error) {
  }

  public String getRegistrationId() {
    return _registrationId;
  }

  @Override
  public void handleMessage(Bundle extras) {
    String fromRegistrationId = extras.getString("registration_id");
    clipboardProvider.handle(fromRegistrationId, getRegistrationId());
  }

  @Override
  public void saveSuccess() {
  }

  @Override
  public void deleteSuccess() {
  }

  @Override
  public void callFailed(String reason) {
  }
}
