package com.omnipaste.droidomni.presenter;

import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.ui.fragment.ActivityFragment_;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OmniPresenter extends Presenter<OmniPresenter.View> {

  private OmniServiceConnection omniServiceConnection;

  public interface View {
    void setFragment(Fragment activityFragment);
  }

  @Inject
  public OmniPresenter(OmniServiceConnection omniServiceConnection) {
    this.omniServiceConnection = omniServiceConnection;
  }

  @Override
  public void initialize() {
    omniServiceConnection.startOmniService();
    setActivityFragment();
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  private void setActivityFragment() {
   View view = getView();
    if (view == null) {
      return;
    }

    view.setFragment(ActivityFragment_.builder().build());
  }
}
