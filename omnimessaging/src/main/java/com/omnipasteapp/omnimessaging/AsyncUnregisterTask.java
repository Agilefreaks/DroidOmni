package com.omnipasteapp.omnimessaging;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class AsyncUnregisterTask extends AsyncTask<Object, Void, Boolean> {
  private GoogleCloudMessaging _gcm;
  private IHandleUnregister _handler;

  public AsyncUnregisterTask(GoogleCloudMessaging gcm, IHandleUnregister handler) {
    _gcm = gcm;
    _handler = handler;
  }

  @Override
  protected Boolean doInBackground(Object... objects) {
    try {
      _gcm.unregister();

      _handler.handleUnregisterSuccess();
    } catch (IOException e) {
      _handler.handleUnregisterError(e.getMessage());
    }

    return null;
  }
}
