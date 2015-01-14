package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.omnipaste.omnicommon.dto.PhoneCallDto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PhoneCallEndCall extends PhoneCallAction {

  public PhoneCallEndCall(Context context) {
    super(context);
  }

  @Override
  public void execute(PhoneCallDto phoneCallDto) {
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
