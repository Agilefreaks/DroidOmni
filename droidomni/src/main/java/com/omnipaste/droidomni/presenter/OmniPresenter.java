package com.omnipaste.droidomni.presenter;

import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.service.OmniService;
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

  // TODO: this does not belong here
  public void refreshClippings() {
    try {
      omniServiceConnection.getOmniServiceMessenger().send(Message.obtain(null, OmniService.MSG_REFRESH_OMNI_CLIPBOARD));
    } catch (RemoteException ignored) {
    }
  }

  private void setActivityFragment() {
   View view = getView();
    if (view == null) {
      return;
    }

    view.setFragment(ActivityFragment_.builder().build());
  }
}
