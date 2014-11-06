package com.omnipaste.droidomni.di;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.res.Resources;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.DroidOmniApplication_;
import com.omnipaste.droidomni.prefs.PrefsModule;
import com.omnipaste.droidomni.ui.UiModule;
import com.omnipaste.omniapi.OmniApiModule;
import com.omnipaste.omnicommon.OmniCommonModule;

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
        DroidOmniApplication_.class
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
  public AccountManager provideAccountManager() {
    return (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
  }

  @Provides
  @Singleton
  public Resources provideResources() {
    return context.getResources();
  }
}
