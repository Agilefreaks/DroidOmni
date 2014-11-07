package com.omnipaste.droidomni.service;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.omnipaste.droidomni.DroidOmniApplication;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoogleAnalyticsService {

  @Inject
  public GoogleAnalyticsService() {
  }

  public void trackHit(String path) {
    Tracker tracker = DroidOmniApplication.getTracker();
    tracker.setScreenName(path);
    tracker.send(new HitBuilders.AppViewBuilder().build());
  }
}
