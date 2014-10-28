package com.omnipaste.droidomni;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.EApplication;

import javax.inject.Inject;

import co.bugfreak.BugFreak;
import dagger.ObjectGraph;

@EApplication
public class DroidOmniApplication extends Application {
  private static Context context;
  private static ObjectGraph objectGraph;
  private static Tracker tracker;

  public String gcmSenderId = BuildConfig.GMC_SENDER_ID;

  public String apiUrl = BuildConfig.API_URL;

  public String apiClientId = BuildConfig.API_CLIENT_ID;

  public static String apiClientToken = BuildConfig.API_CLIENT_TOKEN;

  public String bugFreakToken = BuildConfig.BUGFREAK_TOKEN;

  @Inject
  public ConfigurationService configurationService;

  public static Context getAppContext() {
    return DroidOmniApplication.context;
  }

  public static <T> void inject(T instance) {
    objectGraph.inject(instance);
  }

  public static synchronized Tracker getTracker() {
    if (tracker == null) {
      GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
      tracker = analytics.newTracker(R.xml.app_tracker);
    }

    return tracker;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    objectGraph = ObjectGraph.create(new MainModule(this));
    inject(this);

    context = getApplicationContext();

    init();
  }

  private void init() {
    Configuration configuration = configurationService.getConfiguration();
    configuration.setApiUrl(apiUrl);
    configuration.setGcmSenderId(gcmSenderId);
    configuration.setApiClientId(apiClientId);
    configurationService.setConfiguration(configuration);

    BugFreak.hook("2537eed2-36fd-4d9c-9ca9-54db031126fd", bugFreakToken, this);
  }
}
