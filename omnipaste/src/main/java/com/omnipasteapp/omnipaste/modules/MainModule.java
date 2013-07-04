package com.omnipasteapp.omnipaste.modules;

import android.content.Context;

import com.omnipasteapp.androidclipboard.AndroidClipboardModule;
import com.omnipasteapp.omnicommon.OmnicommonModule;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.ConfigurationProvider;
import com.omnipasteapp.omnipaste.activities.MainActivity_;
import com.omnipasteapp.omnipaste.backgroundServices.OmnipasteService_;
import com.omnipasteapp.omnipaste.services.IIntentService;
import com.omnipasteapp.omnipaste.services.IntentService;
import com.omnipasteapp.pubnubclipboard.PubNubClipboardModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        OmnipasteService_.class,
        MainActivity_.class,
        IOmniService.class
    },
    includes = {
        OmnicommonModule.class,
        AndroidClipboardModule.class,
        PubNubClipboardModule.class
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
  IConfigurationProvider providesConfigurationProvider() {
    return new ConfigurationProvider();
  }

  @Provides
  @Singleton
  IIntentService providesItentService(IntentService intentService) {
    return intentService;
  }
}
