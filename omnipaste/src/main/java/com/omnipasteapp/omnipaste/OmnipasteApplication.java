package com.omnipasteapp.omnipaste;

import android.app.Application;
import android.content.res.Resources;

import com.omnipasteapp.droidbugfreak.AgileReporter;
import com.omnipasteapp.droidbugfreak.GlobalConfig;
import com.omnipasteapp.omnipaste.modules.MainModule;

import dagger.ObjectGraph;

public class OmnipasteApplication extends Application {
  private static ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();

    Resources resources = this.getResources();

    GlobalConfig.Settings.setApiKey(resources.getString(R.string.bugFreak_api_key));
    GlobalConfig.Settings.setToken(resources.getString(R.string.bugFreak_token));
    GlobalConfig.Settings.setServiceEndPoint(resources.getString(R.string.bugFreak_service_endpoint));

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
