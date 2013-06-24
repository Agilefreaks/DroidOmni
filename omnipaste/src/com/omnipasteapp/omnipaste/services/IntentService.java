package com.omnipasteapp.omnipaste.services;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.google.inject.Inject;

public class IntentService implements IIntentService {

  private Context _context;

  @Inject
  public IntentService(Context context) {
    _context = context;
  }

  @Override
  public void startService(Class<? extends Service> cls) {
    _context.startService(new Intent(_context, cls));
  }

  @Override
  public void stopService(Class<? extends Service> cls) {
    _context.stopService(new Intent(_context, cls));
  }

  @Override
  public void startActivity(Class<? extends Activity> cls) {
    _context.startActivity(new Intent(_context, cls));
  }

  @Override
  public void startClearActivity(Class<? extends Activity> cls) {
    Intent intent = new Intent(_context, cls);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    } else {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    _context.startActivity(intent);
  }

  @Override
  public void sendBroadcast(Class<? extends BroadcastReceiver> receiverClass, String command) {
    Intent intent = new Intent(_context, receiverClass);
    intent.setAction(command);

    _context.sendBroadcast(intent);
  }
}
