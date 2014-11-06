package com.omnipaste.droidomni.di;

import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.clipboardprovider.ClipboardProviderModule;
import com.omnipaste.droidomni.DroidOmniApplication_;
import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.droidomni.providers.GcmNotificationProvider;
import com.omnipaste.droidomni.service.OmniService_;
import com.omnipaste.droidomni.ui.UiModule;
import com.omnipaste.eventsprovider.EventsProviderModule;
import com.omnipaste.omniapi.OmniApiModule;
import com.omnipaste.omnicommon.OmniCommonModule;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.phoneprovider.PhoneProviderModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    includes = {
        OmniCommonModule.class,
        OmniApiModule.class,
        PrefsModule.class,
        UiModule.class
    },
    injects = {
        DroidOmniApplication_.class,
        OmniService_.class,
        ClipboardProviderModule.class,
        PhoneProviderModule.class,
        EventsProviderModule.class,
    },
    complete = false,
    library = true
)
public class RootModule {
  private final Context context;

  public RootModule(Context context) {
    this.context = context;
  }

  @Provides Context provideApplicationContext() {
    return context;
  }

  @Provides
  @Singleton
  public GoogleCloudMessaging provideGoogleCloudMessaging() {
    return GoogleCloudMessaging.getInstance(context);
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
  public AccountManager provideAccountManager() {
    return (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
  }

  @Singleton
  @Provides
  public NotificationProvider providesNotificationProvider() {
    return new GcmNotificationProvider();
  }

  @Provides
  @Singleton
  public Resources provideResources() {
    return context.getResources();
  }
}
