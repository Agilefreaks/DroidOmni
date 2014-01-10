package com.omnipaste.droidomni;

import android.app.Application;

import dagger.ObjectGraph;

public class DroidOmniApplication extends Application {
  private static ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();

    objectGraph = ObjectGraph.create(new MainModule());
  }

  public static <T> void inject(T instance) {
    objectGraph.inject(instance);
  }
}
