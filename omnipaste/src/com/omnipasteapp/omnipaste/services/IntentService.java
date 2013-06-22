package com.omnipasteapp.omnipaste.services;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.inject.Inject;

public class IntentService implements IIntentService {

  private Context context;

  @Inject
  public IntentService(Context context){
    this.context = context;
  }

  @Override
  public void startService(Class<? extends Service> cls) {
    context.startService(new Intent(context, cls));
  }

  @Override
  public void stopService(Class<? extends Service> cls) {
    context.stopService(new Intent(context, cls));
  }

  @Override
  public void startActivity(Class<? extends Activity> cls) {
    context.startActivity(new Intent(context, cls));
  }

  @Override
  public void startClearActivity(Class<? extends  Activity> cls) {
    Intent intent = new Intent(context, cls);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    context.startActivity(intent);
  }

  @Override
  public void sendBroadcast(Class<? extends BroadcastReceiver> receiverClass, String command) {
    Intent intent = new Intent(context, receiverClass);
    intent.setAction(command);

    context.sendBroadcast(intent);
  }
}
