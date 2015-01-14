package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.omnipaste.omnicommon.dto.PhoneCallDto;

import java.lang.reflect.InvocationTargetException;

public abstract class PhoneCallAction {
  protected Context context;
  protected TelephonyManager telephonyManager;

  public static <T extends PhoneCallAction> T build(Class<T> clazz, Context context) {
    T result = null;

    try {
      result = clazz.getDeclaredConstructor(Context.class).newInstance(context);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException ignore) {
    }

    return result;
  }

  public PhoneCallAction(Context context) {
    this.context = context;
  }

  public PhoneCallAction setTelephonyManager(TelephonyManager telephonyManager) {
    this.telephonyManager = telephonyManager;
    return this;
  }

  public abstract void execute(PhoneCallDto phoneCallDto);
}
