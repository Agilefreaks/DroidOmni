package com.omnipaste.droidomni.di;

import android.content.Context;

import com.omnipaste.droidomni.DroidOmniApplication_;
import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.omniapi.OmniApiModule;
import com.omnipaste.omnicommon.OmniCommonModule;

import dagger.Module;
import dagger.Provides;

@Module(
    includes = {
        OmniCommonModule.class,
        OmniApiModule.class,
        PrefsModule.class
    },
    injects = {
        DroidOmniApplication_.class
    }, library = true)
public class RootModule {
  private final Context context;

  public RootModule(Context context) {
    this.context = context;
  }

  @Provides
  Context provideApplicationContext() {
    return context;
  }
}
