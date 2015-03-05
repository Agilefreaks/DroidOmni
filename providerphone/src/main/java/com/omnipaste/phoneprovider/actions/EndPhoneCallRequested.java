package com.omnipaste.phoneprovider.actions;

import android.telephony.TelephonyManager;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.NotificationFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EndPhoneCallRequested extends NotificationFilter {
  private TelephonyManager telephonyManager;
  private PhoneCalls phoneCalls;

  @Inject
  public EndPhoneCallRequested(NotificationProvider notificationProvider,
                               PhoneCalls phoneCalls,
                               TelephonyManager telephonyManager) {
    super(notificationProvider);
    this.phoneCalls = phoneCalls;
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

      phoneCalls.markAsEnded(deviceId, notificationDto.getId()).subscribe();
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignore) {
    }
  }
}
