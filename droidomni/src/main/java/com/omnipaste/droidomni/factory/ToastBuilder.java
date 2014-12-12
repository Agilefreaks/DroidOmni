package com.omnipaste.droidomni.factory;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ToastBuilder {
  private Context context;

  @Inject
  public ToastBuilder(Context context) {
    this.context = context;
  }

  public void buildShort(int resId) {
    build(resId, Toast.LENGTH_SHORT);
  }

  public void buildLong(int resId) {
    build(resId, Toast.LENGTH_LONG);
  }

  public void build(int resId, int length) {
    Toast toast = Toast.makeText(context, resId, length);
    toast.show();
  }
}
