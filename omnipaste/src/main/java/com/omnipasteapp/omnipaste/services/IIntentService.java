package com.omnipasteapp.omnipaste.services;

import android.app.Activity;
import android.app.Service;

public interface IIntentService {

  void startService(Class<? extends Service> cls);

  void stopService(Class<? extends Service> cls);

  void startActivity(Class<? extends Activity> cls);

  void startNewActivity(Class<? extends Activity> cls);

  void sendBroadcast(String action);
}
