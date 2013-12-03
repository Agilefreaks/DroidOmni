package com.omnipasteapp.omnimessaging;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipasteapp.omnicommon.CommonUtilities;

import java.io.IOException;

public class AsyncRegistrationTask extends AsyncTask<Object, Void, Boolean> {
  private static final String SENDER_ID = "930634995806";

  private Context _context;
  private GoogleCloudMessaging _gcm;
  private IHandleRegistration _handler;

  public AsyncRegistrationTask(Context context, GoogleCloudMessaging gcm, IHandleRegistration handler) {
    _context = context;
    _gcm = gcm;
    _handler = handler;
  }

  @Override
  protected Boolean doInBackground(Object... objects) {
    String registrationId = objects[0] != null ? (String) objects[0] : "";
    int registeredVersion = (Integer) objects[1];
    int currentVersion = CommonUtilities.getAppVersion(_context);

    if (registrationId.isEmpty() || registeredVersion != currentVersion) {
      try {
        registrationId = _gcm.register(SENDER_ID);

        _handler.handleRegistrationSuccess(registrationId, registeredVersion);
      } catch (IOException e) {
        _handler.handleRegistrationError(e.getMessage());
      }
    }
    else {
      _handler.handleRegistrationSuccess(registrationId, registeredVersion);
    }

    return !registrationId.isEmpty();
  }
}
