package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.adapter.AboutAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AboutPresenter extends Presenter {

  private AboutAdapter aboutAdapter;

  @Inject
  public AboutPresenter(AboutAdapter aboutAdapter) {
    this.aboutAdapter = aboutAdapter;
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
