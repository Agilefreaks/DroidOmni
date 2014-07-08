package com.omnipaste.droidomni;

import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.clipboardprovider.ClipboardProviderModule;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.activities.OmniActivity_;
import com.omnipaste.droidomni.controllers.ActionBarController;
import com.omnipaste.droidomni.controllers.ActionBarControllerImpl;
import com.omnipaste.droidomni.controllers.ClippingsFragmentController;
import com.omnipaste.droidomni.controllers.ClippingsFragmentControllerImpl;
import com.omnipaste.droidomni.controllers.MainActivityController;
import com.omnipaste.droidomni.controllers.MainActivityControllerImpl;
import com.omnipaste.droidomni.controllers.OmniActivityController;
import com.omnipaste.droidomni.controllers.OmniActivityControllerImpl;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment_;
import com.omnipaste.droidomni.providers.GcmNotificationProvider;
import com.omnipaste.droidomni.services.DeviceServiceImpl;
import com.omnipaste.droidomni.services.FragmentService;
import com.omnipaste.droidomni.services.FragmentServiceImpl;
import com.omnipaste.droidomni.services.LocalConfigurationService;
import com.omnipaste.droidomni.services.LoginService;
import com.omnipaste.droidomni.services.NotificationService;
import com.omnipaste.droidomni.services.NotificationServiceImpl;
import com.omnipaste.droidomni.services.OmniService_;
import com.omnipaste.droidomni.services.SessionService;
import com.omnipaste.droidomni.services.SessionServiceImpl;
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
import com.omnipaste.notificationsprovider.NotificationsProviderModule;
import com.omnipaste.omniapi.OmniApiModule;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.omnicommon.services.ConfigurationService;
import com.omnipaste.phoneprovider.PhoneProviderModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        DroidOmniApplication_.class,
        // activities
        MainActivity_.class,
        OmniActivity_.class,
        // controllers
        MainActivityControllerImpl.class,
        OmniActivityControllerImpl.class,
        ClippingsFragmentControllerImpl.class,
        // services
        DeviceServiceImpl.class,
        LoginService.class,
        SessionServiceImpl.class,
        OmniService_.class,
        // others
        ClippingView_.class,
        ClippingsFragment_.class
    },
    includes = {
        OmniApiModule.class,
        ClipboardProviderModule.class,
        PhoneProviderModule.class,
        NotificationsProviderModule.class
    }
)
public class MainModule {
  private Context context;

  public MainModule(Context context) {
    this.context = context.getApplicationContext();
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
  public NotificationManager providesNotificationManager() {
    return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
  }

  @Provides
  @Singleton
  public Context providesContext() {
    return context;
  }

  @Singleton
  @Provides
  public ConfigurationService providesConfigurationService(Context context) {
    return new LocalConfigurationService(context);
  }

  @Singleton
  @Provides
  public NotificationProvider providesNotificationProvider() {
    return new GcmNotificationProvider();
  }

  @Provides
  public GoogleCloudMessaging providesGoogleCloudMessaging(Context context) {
    return GoogleCloudMessaging.getInstance(context);
  }

  @Provides
  public MainActivityController providesMainActivityController(MainActivityControllerImpl mainActivityController) {
    return mainActivityController;
  }

  @Provides
  public OmniActivityController providesOmniActivityController(OmniActivityControllerImpl omniActivityController) {
    return omniActivityController;
  }

  @Provides
  public ClippingsFragmentController providesClippingsFragmentController() {
    return new ClippingsFragmentControllerImpl();
  }

  @Provides
  @Singleton
  public ActionBarController providesActionBarController() {
    return new ActionBarControllerImpl();
  }

  @Provides
  public SessionService providesSessionService(SessionServiceImpl sessionService) {
    return sessionService;
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
