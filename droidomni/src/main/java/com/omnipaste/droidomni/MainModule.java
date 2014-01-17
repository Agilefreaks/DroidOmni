package com.omnipaste.droidomni;

import android.content.Context;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.fragments.MainFragment_;
import com.omnipaste.droidomni.services.DeviceService;
import com.omnipaste.droidomni.services.LocalConfigurationService;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        MainActivity_.class,
        LoginFragment_.class,
        MainFragment_.class,
        DroidOmniApplication_.class,
        DeviceService.class
    }
)
public class MainModule {
  private Context context;

  public MainModule(Context context) {
    this.context = context.getApplicationContext();
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

  @Provides
  public GoogleCloudMessaging providesGoogleCloudMessaging(Context context) {
    return GoogleCloudMessaging.getInstance(context);
  }
}
