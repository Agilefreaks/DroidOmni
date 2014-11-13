package com.omnipaste.droidomni;

import android.accounts.AccountManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.clipboardprovider.ClipboardProviderModule;
import com.omnipaste.droidomni.gcm.GcmIntentService_;
import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.droidomni.provider.GcmNotificationProvider;
import com.omnipaste.droidomni.service.OmniService_;
import com.omnipaste.droidomni.service.smartaction.SmartActionRemove;
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
        GcmIntentService_.class,
        SmartActionRemove.class
    }
)
public final class RootModule {
  private final Context context;

  public RootModule(Context context) {
    this.context = context;
  }

  @Provides
  public Context provideApplicationContext() {
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
  public AccountManager provideAccountManager() {
    return (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
  }

  @Provides
  @Singleton
  public Resources provideResources() {
    return context.getResources();
  }

  @Provides
  public NotificationManagerCompat provideNotificationManagerCompat() {
    return NotificationManagerCompat.from(context);
  }

  @Provides
  @Singleton
  public NotificationProvider provideNotificationProvider() {
    return new GcmNotificationProvider();
  }
}
