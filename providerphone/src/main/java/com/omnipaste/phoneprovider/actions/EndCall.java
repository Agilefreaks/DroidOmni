package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EndCall extends Action {

  public EndCall(Context context) {
    super(context);
  }

  @Override
  public void execute(Bundle extras) {
    try {
      Class<? extends TelephonyManager> telephonyManagerClass = telephonyManager.getClass();
      Method getITelephony = telephonyManagerClass.getDeclaredMethod("getITelephony");
      getITelephony.setAccessible(true);

      Object telephonyService = getITelephony.invoke(telephonyManager);
      Class<?> telephonyServiceClass = telephonyService.getClass();
      Method endCall = telephonyServiceClass.getDeclaredMethod("endCall");
      endCall.invoke(telephonyService);
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignore) {
    }
  }
}
