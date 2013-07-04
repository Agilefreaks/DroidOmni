package com.omnipasteapp.omnipaste.services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

public class IntentService implements IIntentService {

  private Context _context;

  @Inject
  public IntentService(Context context) {
    _context = context;
  }

  @Override
  public void startService(Class<? extends Service> cls) {
    startService(_context, cls);
  }

  public static void startService(Context context, Class<? extends  Service> cls) {
    context.startService(new Intent(context, cls));
  }

  @Override
  public void stopService(Class<? extends Service> cls) {
    stopService(_context, cls);
  }

  public static void stopService(Context context, Class<? extends Service> cls) {
    context.stopService(new Intent(context, cls));
  }

  @Override
  public void startActivity(Class<? extends Activity> cls) {
    _context.startActivity(new Intent(_context, cls));
  }

  @Override
  public void sendBroadcast(String action) {
    sendBroadcast(_context, action);
  }

  public static void sendBroadcast(Context context, String command) {
    Intent intent = new Intent();
    intent.setAction(command);

    context.sendBroadcast(intent);
  }
}
