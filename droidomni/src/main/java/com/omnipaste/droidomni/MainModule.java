package com.omnipaste.droidomni;

import android.content.ClipboardManager;
import android.content.Context;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.clipboardprovider.ClipboardProviderModule;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.activities.OmniActivity_;
import com.omnipaste.droidomni.controllers.MainActivityController;
import com.omnipaste.droidomni.controllers.MainController;
import com.omnipaste.droidomni.controllers.OmniActivityController;
import com.omnipaste.droidomni.controllers.OmniController;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.providers.GcmNotificationProvider;
import com.omnipaste.droidomni.services.DeviceService;
import com.omnipaste.droidomni.services.LocalConfigurationService;
import com.omnipaste.droidomni.services.OmniService_;
import com.omnipaste.omniapi.OmniApiModule;
import com.omnipaste.omnicommon.providers.NotificationProvider;
import com.omnipaste.omnicommon.services.ConfigurationService;
import com.omnipaste.phoneprovider.PhoneProviderModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        MainActivity_.class,
        OmniActivity_.class,
        LoginFragment_.class,
        DroidOmniApplication_.class,
        DeviceService.class,
        OmniService_.class
    },
    includes = {
        OmniApiModule.class,
        ClipboardProviderModule.class,
        PhoneProviderModule.class
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
  public MainActivityController providesMainActivityController(MainController mainController) {
    return mainController;
  }

  @Provides
  public OmniActivityController providesOmniActivityController(OmniController omniController) {
    return omniController;
  }
}
