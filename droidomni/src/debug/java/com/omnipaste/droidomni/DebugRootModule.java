package com.omnipaste.droidomni;

import dagger.Module;

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
