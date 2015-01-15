package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.telephony.SmsManager;

import com.omnipaste.omnicommon.dto.SmsMessageDto;

import java.lang.reflect.InvocationTargetException;

public abstract class SmsMessageAction {
  protected Context context;
  protected SmsManager smsManager;

  public static <T extends SmsMessageAction> T build(Class<T> clazz, Context context) {
    T result = null;

    try {
      result = clazz.getDeclaredConstructor(Context.class).newInstance(context);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException ignore) {
    }

    return result;
  }

  public SmsMessageAction(Context context) {
    this.context = context;
  }

  public SmsMessageAction setSmsManager(SmsManager smsManager) {
    this.smsManager = smsManager;
    return this;
  }

  public abstract void execute(SmsMessageDto smsMessageDto);
}
