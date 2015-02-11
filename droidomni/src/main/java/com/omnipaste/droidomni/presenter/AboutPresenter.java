package com.omnipaste.droidomni.presenter;

import android.content.Context;
import android.content.Intent;

import com.omnipaste.droidomni.adapter.AboutAdapter;
import com.omnipaste.droidomni.ui.activity.AboutActivity_;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AboutPresenter extends Presenter {

  private AboutAdapter aboutAdapter;

  @Inject
  public AboutPresenter(AboutAdapter aboutAdapter) {
    this.aboutAdapter = aboutAdapter;
  }

  public static Intent getIntent(Context context) {
    return new Intent(context, AboutActivity_.class);
  }

  @Override public void initialize() {
  }

  @Override public void resume() {
  }

  @Override public void pause() {
  }

  @Override public void destroy() {
  }

  public AboutAdapter getAboutAdapter() {
    return aboutAdapter;
  }
}
