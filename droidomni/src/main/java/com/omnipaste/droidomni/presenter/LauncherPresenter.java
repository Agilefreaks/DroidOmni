package com.omnipaste.droidomni.presenter;

import android.app.Activity;

import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LauncherPresenter extends Presenter<LauncherPresenter.View> {

  public interface View {
    void finish();
  }

  private Navigator navigator;
  private SessionService sessionService;

  @Inject
  protected LauncherPresenter(Navigator navigator, SessionService sessionService) {
    this.navigator = navigator;
    this.sessionService = sessionService;
  }

  @Override
  public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.attachView((Activity) view);
    }
  }

  @Override
  public void initialize() {
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

  private void finishView() {
    View view = getView();
    if (view == null) {
      return;
    }

    view.finish();
  }
}
