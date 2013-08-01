package com.omnipasteapp.omnipaste;

import android.app.Application;

import com.omnipasteapp.droidbugfreak.AgileReporter;
import com.omnipasteapp.droidbugfreak.GlobalConfig;
import com.omnipasteapp.omnipaste.modules.MainModule;

import dagger.ObjectGraph;

public class OmnipasteApplication extends Application {
  private static ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();

    GlobalConfig.Settings.setApiKey("apiKey");
    GlobalConfig.Settings.setToken("token");
    GlobalConfig.Settings.setServiceEndPoint("http://url.com");

    AgileReporter.hook(this);

    objectGraph = ObjectGraph.create(new MainModule(this));
  }

  public static <T> void inject(T instance) {
    objectGraph.inject(instance);
  }

  public static <T> T get(Class<T> instance) {
    return objectGraph.get(instance);
  }
}
