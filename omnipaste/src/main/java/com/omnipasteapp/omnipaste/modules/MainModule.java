package com.omnipasteapp.omnipaste.modules;

import android.content.Context;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipasteapp.clipboardprovider.ClipboardProviderModule;
import com.omnipasteapp.omniapi.OmniapiModule;
import com.omnipasteapp.omnicommon.OmnicommonModule;
import com.omnipasteapp.omnicommon.interfaces.IClipboardProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnimessaging.OmniMessagingModule;
import com.omnipasteapp.omnipaste.GcmIntentService_;
import com.omnipasteapp.omnipaste.activities.LaunchActivity_;
import com.omnipasteapp.omnipaste.activities.LoginActivity_;
import com.omnipasteapp.omnipaste.activities.MainActivity_;
import com.omnipasteapp.omnipaste.providers.SharedPreferencesConfigurationProvider;
import com.omnipasteapp.omnipaste.services.IIntentHelper;
import com.omnipasteapp.omnipaste.services.IntentHelper;
import com.omnipasteapp.omnipaste.services.OmnipasteService_;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        OmnipasteService_.class,
        MainActivity_.class,
        LoginActivity_.class,
        LaunchActivity_.class,
        GcmIntentService_.class,
        IClipboardProvider.class
    },
    includes = {
        OmnicommonModule.class,
        OmniapiModule.class,
        OmniMessagingModule.class,
        ClipboardProviderModule.class
    })
public class MainModule {
  private Context context;

  public MainModule(Context context) {
    this.context = context.getApplicationContext();
  }

  @Provides
  @Singleton
  Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  IConfigurationProvider providesConfigurationProvider(SharedPreferencesConfigurationProvider sharedPreferencesConfigurationProvider) {
    return sharedPreferencesConfigurationProvider;
  }

  @Provides
  @Singleton
  IIntentHelper providesIntentService(IntentHelper intentService) {
    return intentService;
  }

  @Provides
  GoogleCloudMessaging providesGoogleCloudMessaging(Context context) {
    return GoogleCloudMessaging.getInstance(context);
  }
}
