package com.omnipasteapp.omniclipboard.messaging;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import java.io.IOException;

public class AsyncGetRegistrationIdTask extends AsyncTask<Object, Void, Boolean> {

  private IConfigurationService _configurationService;
  private Context _context;
  private IHandleRegistrationId _handler;

  public AsyncGetRegistrationIdTask(IConfigurationService configurationService, Context context, IHandleRegistrationId handler) {
    _configurationService = configurationService;
    _context = context;
    _handler = handler;
  }

  @Override
  protected Boolean doInBackground(Object... objects) {
    String registrationId = _configurationService.getCommunicationSettings().getRegistrationId();

    int registeredVersion = _configurationService.getAppVersion();
    int currentVersion = getAppVersion();

    if (registeredVersion != currentVersion) {
      registrationId = "";
      storeAppVersion(currentVersion);
    }

    if (registrationId == null || registrationId.isEmpty()) {
      try {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(_context);

        String SENDER_ID = "930634995806";
        registrationId = gcm.register(SENDER_ID);

        sendRegistrationIdToBackend();

        storeRegistrationId(registrationId);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    _handler.setRegistrationId(registrationId);

    return registrationId != null && !registrationId.isEmpty();
  }

  private void sendRegistrationIdToBackend() {
    // Your implementation here.
  }

  private void storeRegistrationId(String regId) {
    CommunicationSettings communicationSettings = _configurationService.getCommunicationSettings();
    communicationSettings.setRegistrationId(regId);
    _configurationService.updateCommunicationSettings();
  }

  private void storeAppVersion(int appVersion) {
    _configurationService.updateAppVersion(appVersion);
  }

  private int getAppVersion() {
    try {
      PackageInfo packageInfo = _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0);
      return packageInfo.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      // should never happen
      throw new RuntimeException("Could not get package name: " + e);
    }
  }
}
