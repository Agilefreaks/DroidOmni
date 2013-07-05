package com.omnipasteapp.omnipaste.services;

import android.app.Activity;

public interface IIntentService {

  void startNewActivity(Class<? extends Activity> cls);

  void sendBroadcast(String action);
}
