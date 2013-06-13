package com.omnipasteapp.omnipaste.services;

import android.app.Activity;
import android.app.Service;
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
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity(intent);
  }
}
