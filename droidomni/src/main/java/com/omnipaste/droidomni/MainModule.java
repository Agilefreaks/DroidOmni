
package com.omnipaste.droidomni;

import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.clipboardprovider.ClipboardProviderModule;
import com.omnipaste.droidomni.activities.OmniActivity_;
import com.omnipaste.droidomni.controllers.ClippingsFragmentControllerImpl;
import com.omnipaste.droidomni.controllers.OmniActivityControllerImpl;
import com.omnipaste.droidomni.fragments.clippings.AllFragment_;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment_;
import com.omnipaste.droidomni.fragments.clippings.CloudFragment_;
import com.omnipaste.droidomni.fragments.clippings.LocalFragment_;
import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.droidomni.providers.GcmNotificationProvider;
import com.omnipaste.droidomni.service.AccountsService;
import com.omnipaste.droidomni.service.DeviceService;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.services.FragmentService;
import com.omnipaste.droidomni.services.FragmentServiceImpl;
import com.omnipaste.droidomni.services.GoogleAnalyticsService;
import com.omnipaste.droidomni.services.GoogleAnalyticsServiceImpl;
import com.omnipaste.droidomni.services.NotificationService;
import com.omnipaste.droidomni.services.NotificationServiceImpl;
import com.omnipaste.droidomni.services.OmniService_;
import com.omnipaste.droidomni.services.SmartActionService;
import com.omnipaste.droidomni.services.SmartActionServiceImpl;
import com.omnipaste.droidomni.services.subscribers.ClipboardSubscriber;
import com.omnipaste.droidomni.services.subscribers.ClipboardSubscriberImpl;
import com.omnipaste.droidomni.services.subscribers.GcmWorkaroundSubscriber;
import com.omnipaste.droidomni.services.subscribers.GcmWorkaroundSubscriberImpl;
import com.omnipaste.droidomni.services.subscribers.PhoneSubscriber;
import com.omnipaste.droidomni.services.subscribers.PhoneSubscriberImpl;
import com.omnipaste.droidomni.services.subscribers.TelephonyNotificationsSubscriber;
import com.omnipaste.droidomni.services.subscribers.TelephonyNotificationsSubscriberImpl;
import com.omnipaste.droidomni.views.ClippingView_;
import com.omnipaste.eventsprovider.EventsProviderModule;
import com.omnipaste.omniapi.OmniApiModule;
import com.omnipaste.omnicommon.OmniCommonModule;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.PhoneProviderModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        DroidOmniApplication_.class,
        // activities
        OmniActivity_.class,
        // controllers
        OmniActivityControllerImpl.class,
        ClippingsFragmentControllerImpl.class,
        // services
        DeviceService.class,
        SessionService.class,
        AccountsService.class,
        OmniService_.class,
        // Fragments
        ClippingsFragment_.class,
        AllFragment_.class,
        LocalFragment_.class,
        CloudFragment_.class,
        // others
        ClippingView_.class
    },
    includes = {
        OmniApiModule.class,
        ClipboardProviderModule.class,
        PhoneProviderModule.class,
        EventsProviderModule.class,
        OmniCommonModule.class,
        PrefsModule.class
    }
)
public class MainModule {
  private final Context context;

  public MainModule(Context context) {
    this.context = context;
  }

  @Provides
  public Context providesContext() {
    return context;
  }

  @Provides
  @Singleton
  public ClipboardManager providesClipboardManager() {
    return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
  }

  @Provides
  @Singleton
  public TelephonyManager providesTelephonyManager() {
    return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
  }

  @Provides
  @Singleton
  public SmsManager providesSmsManager() {
    return SmsManager.getDefault();
  }

  @Provides
  @Singleton
  public NotificationManager providesNotificationManager() {
    return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
  }

  @Provides
  @Singleton
  public AccountManager providesAccountManager() {
    return (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
  }

  @Singleton
  @Provides
  public NotificationProvider providesNotificationProvider() {
    return new GcmNotificationProvider();
  }

  @Provides
  @Singleton
  public GoogleCloudMessaging providesGoogleCloudMessaging() {
    return GoogleCloudMessaging.getInstance(context);
  }

  @Singleton
  @Provides
  public SmartActionService providesSmartActionService(SmartActionServiceImpl smartActionService) {
    return smartActionService;
  }

  @Provides
  @Singleton
  public NotificationService providesNotificationService(NotificationServiceImpl notificationService) {
    return notificationService;
  }

  @Provides
  @Singleton
  public FragmentService providesFragmentService() {
    return new FragmentServiceImpl();
  }

  @Provides
  @Singleton
  public AccountsService providesAccountService() {
    return new AccountsService();
  }

  @Provides
  @Singleton
  public GoogleAnalyticsService providesGoogleAnalyticsService() {
    return new GoogleAnalyticsServiceImpl();
  }

  @Provides
  @Singleton
  public ClipboardSubscriber providesClipboardSubscriber(ClipboardSubscriberImpl clipboardSubscriber) {
    return clipboardSubscriber;
  }

  @Provides
  @Singleton
  public PhoneSubscriber providesPhoneSubscriber(PhoneSubscriberImpl phoneSubscriber) {
    return phoneSubscriber;
  }

  @Provides
  @Singleton
  public TelephonyNotificationsSubscriber providesTelephonyNotificationsSubscriber(TelephonyNotificationsSubscriberImpl telephonyNotificationsSubscriber) {
    return telephonyNotificationsSubscriber;
  }

  @Provides
  @Singleton
  public GcmWorkaroundSubscriber providesGcmWorkaroundSubscriber() {
    return new GcmWorkaroundSubscriberImpl();
  }
}
