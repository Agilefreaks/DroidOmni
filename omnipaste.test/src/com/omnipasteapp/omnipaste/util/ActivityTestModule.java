package com.omnipasteapp.omnipaste.util;

import android.app.Activity;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import roboguice.inject.ContextSingleton;

public class ActivityTestModule extends AbstractModule {

  @Provides
  @ContextSingleton
  Activity activityProviderKludge() {
    return new Activity();
  }

  @Override
  protected void configure() {
  }
}
