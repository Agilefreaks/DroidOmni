package com.omnipasteapp.omnipaste;

import android.app.Application;

import com.omnipasteapp.omnipaste.modules.MainModule;

import dagger.ObjectGraph;

public class OmnipasteApplication extends Application {
  private static ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();

    objectGraph = ObjectGraph.create(new MainModule(this));
  }

  public static <T> void inject(T instance) {
    objectGraph.inject(instance);
  }

  public static <T> T get(Class<T> instance) {
    return objectGraph.get(instance);
  }
}
