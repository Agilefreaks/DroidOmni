package com.omnipaste.droidomni;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    addsTo = RootModule.class,
    overrides = true,
    library = true
)
public class DebugRootModule {
//  @Provides
//  @Singleton
//  public GoogleCloudMessaging provideGoogleCloudMessaging() {
//    return new DebugGoogleCloudMessaging();
//  }
}
