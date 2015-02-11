package com.omnipaste.droidomni.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.droidomni.ui.activity.ErrorActivity;
import com.omnipaste.droidomni.ui.activity.ErrorActivity_;

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

  public static Intent getIntent(Context context, Throwable throwable) {
    Intent intent = new Intent(context, ErrorActivity_.class);
    intent.putExtra(ErrorActivity.ERROR_EXTRA_KEY, throwable);
    return intent;
  }

  @Override public void attachView(View view) {
    super.attachView(view);

    if (view instanceof Activity) {
      navigator.setContext((Activity) view);
    }
  }

  @Override public void initialize() {
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
  }
}
