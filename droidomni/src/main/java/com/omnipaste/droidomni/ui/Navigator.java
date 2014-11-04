package com.omnipaste.droidomni.ui;

import android.content.Context;
import android.content.Intent;

import com.omnipaste.droidomni.di.ActivityContext;
import com.omnipaste.droidomni.ui.activity.LauncherActivity_;
import com.omnipaste.droidomni.ui.activity.LoginActivity_;

import javax.inject.Inject;

public class Navigator {
  private Context context;

  @Inject
  public Navigator(@ActivityContext Context activityContext) {
    this.context = activityContext;
  }

  public void openLoginActivity() {
    Intent intent = new Intent(context, LoginActivity_.class);
    startActivityNoHistory(intent);
  }

  public void openLauncherActivity() {
    Intent intent = new Intent(context, LauncherActivity_.class);
    startActivityNoHistory(intent);
  }

  public void openOmniActivity() {
  }

  private void startActivityNoHistory(Intent intent) {
    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);
  }

  private void startActivity(Intent intent) {
    context.startActivity(intent);
  }
}
