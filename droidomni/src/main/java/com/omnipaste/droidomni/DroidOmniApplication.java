package com.omnipaste.droidomni;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.omnipaste.droidomni.prefs.GcmSenderId;
import com.omnipaste.omniapi.prefs.ApiClientId;
import com.omnipaste.omniapi.prefs.ApiClientToken;
import com.omnipaste.omniapi.prefs.ApiUrl;
import com.omnipaste.omnicommon.prefs.StringPreference;

import org.androidannotations.annotations.EApplication;

import java.util.List;

import javax.inject.Inject;

import co.bugfreak.BugFreak;
import dagger.ObjectGraph;

@EApplication
public class DroidOmniApplication extends Application {
  private static Context context;
  private static ObjectGraph objectGraph;
  private static Tracker tracker;

  @Inject @GcmSenderId
  public StringPreference gcmSenderId;

  @Inject @ApiUrl
  public StringPreference apiUrl;

  @Inject @ApiClientId
  public StringPreference apiClientId;

  @Inject @ApiClientToken
  public StringPreference apiClientToken;

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

    objectGraph = ObjectGraph.create(Modules.list(this));
    inject(this);

    context = this;

    init();
  }

  public ObjectGraph plus(List<Object> modules) {
    if (modules == null) {
      throw new IllegalArgumentException(
          "You can't plus a null module, review your getModules() implementation");
    }
    return objectGraph.plus(modules.toArray());
  }

  private void init() {
    gcmSenderId.set(BuildConfig.GMC_SENDER_ID);
    apiUrl.set(BuildConfig.API_URL);
    apiClientId.set(BuildConfig.API_CLIENT_ID);
    apiClientToken.set(BuildConfig.API_CLIENT_TOKEN);

    BugFreak.hook("2537eed2-36fd-4d9c-9ca9-54db031126fd", BuildConfig.BUGFREAK_TOKEN, this);
  }
}
