package com.omnipaste.droidomni.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.omnipaste.droidomni.ui.activity.AboutActivity_;
import com.omnipaste.droidomni.ui.activity.ConnectingActivity_;
import com.omnipaste.droidomni.ui.activity.ErrorActivity;
import com.omnipaste.droidomni.ui.activity.ErrorActivity_;
import com.omnipaste.droidomni.ui.activity.LauncherActivity_;
import com.omnipaste.droidomni.ui.activity.LoginActivity_;
import com.omnipaste.droidomni.ui.activity.OmniActivity_;

import javax.inject.Inject;

public class Navigator {
  private Context context;

  @Inject
  public Navigator() {
  }

  public void attachActivity(Activity view) {
    context = view;
  }

  public void openLoginActivity() {
    Intent intent = new Intent(context, LoginActivity_.class);
    startActivityNoHistory(intent);
  }

  public void openLauncherActivity() {
    Intent intent = new Intent(context, LauncherActivity_.class);
    startActivityNoHistory(intent);
  }

  public void openConnectingActivity() {
    Intent intent = new Intent(context, ConnectingActivity_.class);
    startActivityNoHistory(intent);
  }

  public void openOmniActivity() {
    Intent intent = new Intent(context, OmniActivity_.class);
    startActivityNoHistory(intent);
  }

  public void openAbout() {
    Intent intent = new Intent(context, AboutActivity_.class);
    startActivity(intent);
  }

  public void openErrorActivity(Throwable throwable) {
    Intent intent = new Intent(context, ErrorActivity_.class);
    intent.putExtra(ErrorActivity.ERROR_EXTRA_KEY, throwable);
    startActivity(intent);
  }

  private void startActivityNoHistory(Intent intent) {
    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);
  }

  private void startActivity(Intent intent) {
    context.startActivity(intent);
  }
}
