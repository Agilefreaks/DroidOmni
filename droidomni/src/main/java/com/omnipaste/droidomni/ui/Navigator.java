package com.omnipaste.droidomni.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.omnipaste.droidomni.presenter.AboutPresenter;
import com.omnipaste.droidomni.presenter.ConnectingPresenter;
import com.omnipaste.droidomni.presenter.ErrorPresenter;
import com.omnipaste.droidomni.presenter.LauncherPresenter;
import com.omnipaste.droidomni.presenter.LoginPresenter;
import com.omnipaste.droidomni.presenter.OmniPresenter;
import com.omnipaste.droidomni.presenter.SettingsPresenter;

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
    startActivityNoHistory(LoginPresenter.getIntent(context));
  }

  public void openLauncherActivity() {
    startActivityNoHistory(LauncherPresenter.getIntent(context));
  }

  public void openConnectingActivity() {
    startActivityNoHistory(ConnectingPresenter.getIntent(context));
  }

  public void openOmniActivity() {
    startActivityNoHistory(OmniPresenter.getIntent(context));
  }

  public void openSettings() {
    startActivity(SettingsPresenter.getIntent(context));
  }

  public void openAbout() {
    startActivity(AboutPresenter.getIntent(context));
  }

  public void openErrorActivity(Throwable throwable) {
    startActivity(ErrorPresenter.getIntent(context, throwable));
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
