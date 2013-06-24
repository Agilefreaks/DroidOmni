package com.omnipasteapp.omnipaste.services;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;

public interface IIntentService {

  void startService(Class<? extends Service> cls);

  void stopService(Class<? extends Service> cls);

  void startActivity(Class<? extends Activity> cls);

  void startClearActivity(Class<? extends Activity> cls);

  void sendBroadcast(Class<? extends BroadcastReceiver> svc, String command);
}
