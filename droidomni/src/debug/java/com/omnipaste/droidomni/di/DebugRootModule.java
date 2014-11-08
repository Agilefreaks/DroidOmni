package com.omnipaste.droidomni.di;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.DebugGoogleCloudMessaging;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    addsTo = RootModule.class,
    overrides = true
)
public class DebugRootModule {
  @Provides
  @Singleton
  public GoogleCloudMessaging provideGoogleCloudMessaging() {
    return new DebugGoogleCloudMessaging();
  }
}
