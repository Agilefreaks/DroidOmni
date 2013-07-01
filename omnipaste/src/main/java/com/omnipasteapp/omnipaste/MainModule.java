package com.omnipasteapp.omnipaste;

import android.content.Context;

import com.omnipasteapp.omnicommon.OmnicommonModule;
import com.omnipasteapp.androidclipboard.AndroidClipboardModule;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.pubnubclipboard.PubNubClipboardModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
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
}
