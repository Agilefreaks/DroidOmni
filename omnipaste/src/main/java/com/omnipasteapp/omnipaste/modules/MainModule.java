package com.omnipasteapp.omnipaste.modules;

import android.content.Context;

import com.omnipasteapp.omniclipboard.OmniClipboardModule;
import com.omnipasteapp.androidclipboard.AndroidClipboardModule;
import com.omnipasteapp.omnicommon.OmnicommonModule;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.activities.LoginActivity_;
import com.omnipasteapp.omnipaste.activities.MainActivity_;
import com.omnipasteapp.omnipaste.backgroundServices.OmnipasteService_;
import com.omnipasteapp.omnipaste.providers.SharedPreferencesConfigurationProvider;
import com.omnipasteapp.omnipaste.services.IIntentService;
import com.omnipasteapp.omnipaste.services.IntentService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        OmnipasteService_.class,
        MainActivity_.class,
        LoginActivity_.class,
        IOmniService.class
    },
    includes = {
        OmnicommonModule.class,
        AndroidClipboardModule.class,
        OmniClipboardModule.class
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
  IIntentService providesIntentService(IntentService intentService) {
    return intentService;
  }
}
