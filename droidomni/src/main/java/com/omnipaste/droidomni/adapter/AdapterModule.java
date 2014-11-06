package com.omnipaste.droidomni.adapter;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    complete = false,
    library = true
)
public class AdapterModule {
  @Provides
  @Singleton
  public NavigationDrawerAdapter provideNavigationDrawerAdapter(Resources resources) {
    return NavigationDrawerAdapter.build(resources);
  }

  @Provides
  @Singleton
  public SecondaryNavigationDrawerAdapter provideSecondaryNavigationDrawerAdapter(Resources resources) {
    return SecondaryNavigationDrawerAdapter.build(resources);
  }

  @Provides
  @Singleton
  public AboutAdapter provideAboutAdapter(Resources resources, Context context) {
    return AboutAdapter.build(resources, context);
  }
}
