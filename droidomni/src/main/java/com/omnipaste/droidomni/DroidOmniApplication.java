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

  @StringRes(R.string.gcm_sender_id)
  public String gcmSenderId;

  @StringRes(R.string.api_url)
  public String apiUrl;

  @StringRes(R.string.api_client_id)
  public String apiClientId;

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
    configuration.setApiUrl(apiUrl);
    configuration.setGcmSenderId(gcmSenderId);
    configuration.setApiClientId(apiClientId);
    configurationService.setConfiguration(configuration);
  }
}
