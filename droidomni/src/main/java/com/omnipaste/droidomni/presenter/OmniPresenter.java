package com.omnipaste.droidomni.presenter;

import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.ui.fragment.ActivityFragment_;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OmniPresenter extends Presenter<OmniPresenter.View> {
  public interface View {
    void replaceFragment(Fragment activityFragment);

    void addFragment(Fragment fragment);
  }

  @Inject
  public OmniPresenter() {
  }

  @Override
  public void initialize() {
    replaceFragment(ActivityFragment_.builder().build());
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override public void destroy() {
  }

  private void replaceFragment(Fragment fragment) {
    View view = getView();
    if (view == null) {
      return;
    }

    view.replaceFragment(fragment);
  }

  private void addFragment(Fragment fragment) {
    View view = getView();
    if (view == null) {
      return;
    }

    view.addFragment(fragment);
  }
}
