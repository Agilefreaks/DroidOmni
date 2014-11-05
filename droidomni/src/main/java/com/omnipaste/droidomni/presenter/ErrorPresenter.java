package com.omnipaste.droidomni.presenter;

import android.app.Activity;

import com.omnipaste.droidomni.ui.Navigator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ErrorPresenter extends Presenter<ErrorPresenter.View> {

  public interface View {
  }

  private Navigator navigator;

  @Inject
  public ErrorPresenter(Navigator navigator) {
    this.navigator = navigator;
  }

  @Override public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.attachView((Activity) view);
    }
  }

  @Override public void initialize() {
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }
}
