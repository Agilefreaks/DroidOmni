package com.omnipaste.droidomni;

import android.app.Application;

import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.res.StringRes;

import javax.inject.Inject;

import dagger.ObjectGraph;

@EApplication
public class DroidOmniApplication extends Application {
  private static ObjectGraph objectGraph;

  @StringRes
  public String gcmSenderId;

  @StringRes
  public String apiUrl;

  @Inject
  public ConfigurationService configurationService;

  public static <T> void inject(T instance) {
    objectGraph.inject(instance);
  }

  @Override
  public void onCreate() {
    super.onCreate();

    objectGraph = ObjectGraph.create(new MainModule(this));
    inject(this);

    init();
  }

  private void init() {
    Configuration configuration = configurationService.getConfiguration();
    configuration.apiUrl = apiUrl;
    configuration.gcmSenderId = gcmSenderId;
    configurationService.setConfiguration(configuration);
  }
}
