package com.omnipasteapp.omnipaste.services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

import javax.inject.Inject;

public class IntentService implements IIntentService {

  private Context _context;

  @Inject
  public IntentService(Context context) {
    _context = context;
  }

  @Override
  public void startNewActivity(Class<? extends Activity> cls) {
    Intent intent = new Intent(_context, cls);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    _context.startActivity(intent);
  }

  @Override
  public void sendBroadcast(String action) {
    sendBroadcast(_context, action);
  }

  public static void sendBroadcast(Context context, String command) {
    sendBroadcast(context, command, new Intent());
  }

  public static void sendBroadcast(Context context, String command, Intent intent) {
    intent.setAction(command);

    context.sendBroadcast(intent);
  }

  public static void startService(Context context, Class<? extends Service> cls) {
    context.startService(new Intent(context, cls));
  }

  public static void startService(Context context, String action) {
    context.startService(new Intent(action));
  }

  public static void stopService(Context context, Class<? extends Service> cls) {
    context.stopService(new Intent(context, cls));
  }
}
