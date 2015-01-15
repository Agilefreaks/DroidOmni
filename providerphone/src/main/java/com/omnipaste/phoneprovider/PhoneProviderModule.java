package com.omnipaste.phoneprovider;

import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.omnipaste.omniapi.resource.v1.PhoneCalls;
import com.omnipaste.omniapi.resource.v1.SmsMessages;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.actions.EndPhoneCallRequested;
import com.omnipaste.phoneprovider.actions.SendSmsMessageRequested;
import com.omnipaste.phoneprovider.actions.StartPhoneCallRequested;
import com.omnipaste.phoneprovider.receivers.PhoneCallReceived;
import com.omnipaste.phoneprovider.receivers.SmsMessageReceived;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false)
public class PhoneProviderModule {
  @Singleton @Provides
  public StartPhoneCallRequested provideStartPhoneCallRequested(NotificationProvider notificationProvider, Context context, PhoneCalls phoneCalls) {
    StartPhoneCallRequested startPhoneCallRequested = new StartPhoneCallRequested(notificationProvider);
    startPhoneCallRequested.setPhoneCall(phoneCalls);
    startPhoneCallRequested.setContext(context);
    return startPhoneCallRequested;
  }

  @Singleton @Provides
  public SendSmsMessageRequested provideSendSmsMessageRequested(NotificationProvider notificationProvider, SmsMessages smsMessages, SmsManager smsManager) {
    SendSmsMessageRequested sendSmsMessageRequested = new SendSmsMessageRequested(notificationProvider);
    sendSmsMessageRequested.setSmsManager(smsManager);
    sendSmsMessageRequested.setSmsMessages(smsMessages);
    return sendSmsMessageRequested;
  }

  @Singleton @Provides
  public EndPhoneCallRequested provideEndPhoneCallRequested(NotificationProvider notificationProvider, TelephonyManager telephonyManager) {
    EndPhoneCallRequested endPhoneCallRequested = new EndPhoneCallRequested(notificationProvider);
    endPhoneCallRequested.setTelephonyManager(telephonyManager);
    return endPhoneCallRequested;
  }

  @Singleton @Provides
  public PhoneCallReceived providePhoneCallReceived(NotificationProvider notificationProvider, PhoneCalls phoneCalls) {
    PhoneCallReceived phoneCallReceived = new PhoneCallReceived(notificationProvider);
    phoneCallReceived.setPhoneCalls(phoneCalls);
    return phoneCallReceived;
  }

  @Singleton @Provides
  public SmsMessageReceived provideSmsMessageReceived(NotificationProvider notificationProvider, SmsMessages smsMessages) {
    SmsMessageReceived smsMessageReceived = new SmsMessageReceived(notificationProvider);
    smsMessageReceived.setSmsMessages(smsMessages);
    return smsMessageReceived;
  }
}
