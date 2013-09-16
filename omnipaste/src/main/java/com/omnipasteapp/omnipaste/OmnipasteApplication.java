package com.omnipasteapp.omnipaste;

import android.app.Application;
import android.content.res.Resources;

import com.omnipasteapp.omnipaste.modules.MainModule;

import co.bugfreak.BugFreak;
import dagger.ObjectGraph;

public class OmnipasteApplication extends Application {
  private static ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();

    Resources resources = this.getResources();

    // hook bugfreak
    BugFreak.hook(resources.getString(R.string.bugFreak_api_key), resources.getString(R.string.bugFreak_token), this);

    objectGraph = ObjectGraph.create(new MainModule(this));
  }

  public static <T> void inject(T instance) {
    objectGraph.inject(instance);
  }

  public static <T> T get(Class<T> instance) {
    return objectGraph.get(instance);
  }
}
