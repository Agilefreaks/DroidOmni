package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.omnipaste.droidomni.service.PlayService;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.droidomni.ui.activity.LauncherActivity_;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LauncherPresenter extends Presenter<LauncherPresenter.View> {
  private PlayService playService;

  public interface View {
    void finish();

    void showPlayServiceErrorDialog(int status);
  }

  private Navigator navigator;
  private SessionService sessionService;

  @Inject
  protected LauncherPresenter(PlayService playService, Navigator navigator, SessionService sessionService) {
    this.playService = playService;
    this.navigator = navigator;
    this.sessionService = sessionService;
  }

  public static Intent getIntent(Context context) {
    return new Intent(context, LauncherActivity_.class);
  }

  @Override
  public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.setContext((Activity) view);
    }
  }

  @Override
  public void initialize() {
    int status = playService.status();
    if (status != ConnectionResult.SUCCESS) {
      showPlayServiceErrorDialog(status);
      return;
    }

    if (sessionService.isConnected()) {
      navigator.openOmniActivity();
      finishView();
    }
    else {
      navigator.openConnectingActivity();
      finishView();
    }
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override public void destroy() {
  }

  private void showPlayServiceErrorDialog(int status) {
    View view = getView();
    if (view == null) {
      return;
    }

    view.showPlayServiceErrorDialog(status);
  }

  private void finishView() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.finish();
  }
}
