package com.omnipaste.droidomni.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.omnipaste.droidomni.ui.activity.AboutActivity_;
import com.omnipaste.droidomni.ui.activity.ConnectingActivity_;
import com.omnipaste.droidomni.ui.activity.ErrorActivity;
import com.omnipaste.droidomni.ui.activity.ErrorActivity_;
import com.omnipaste.droidomni.ui.activity.LauncherActivity_;
import com.omnipaste.droidomni.ui.activity.LoginActivity_;
import com.omnipaste.droidomni.ui.activity.OmniActivity_;
import com.omnipaste.droidomni.ui.activity.SettingsActivity_;

import javax.inject.Inject;

public class Navigator {
  private Context context;

  @Inject
  public Navigator() {
  }

  public void setContext(Context context) {
    this.context = context;
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
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivityNoHistory(intent);
  }

  public void openSettings() {
    Intent intent = new Intent(context, SettingsActivity_.class);
    startActivity(intent);
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

  public void openUri(Uri uri) {
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
