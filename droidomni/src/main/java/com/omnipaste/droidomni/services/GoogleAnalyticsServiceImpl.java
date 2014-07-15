package com.omnipaste.droidomni.services;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.omnipaste.droidomni.DroidOmniApplication;

public class GoogleAnalyticsServiceImpl implements GoogleAnalyticsService {
  @Override
  public void trackHit(String path) {
    Tracker tracker = DroidOmniApplication.getTracker();
    tracker.setScreenName(path);
    tracker.send(new HitBuilders.AppViewBuilder().build());
  }
}
