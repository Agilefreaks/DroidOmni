package com.omnipaste.phoneprovider.actions;

import android.telephony.TelephonyManager;

import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.NotificationFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EndPhoneCallRequested extends NotificationFilter {
  private TelephonyManager telephonyManager;

  public EndPhoneCallRequested(NotificationProvider notificationProvider) {
    super(notificationProvider);
  }

  public void setTelephonyManager(TelephonyManager telephonyManager) {
    this.telephonyManager = telephonyManager;
  }

  @Override
  protected NotificationDto.Type getType() {
    return NotificationDto.Type.END_PHONE_CALL_REQUESTED;
  }

  @Override
  protected void execute(NotificationDto notificationDto) {
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
