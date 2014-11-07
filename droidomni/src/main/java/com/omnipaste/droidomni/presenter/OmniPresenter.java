package com.omnipaste.droidomni.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.service.OmniService;
import com.omnipaste.droidomni.ui.fragment.ActivityFragment_;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OmniPresenter extends Presenter<OmniPresenter.View> {

  private Messenger omniServiceMessenger;
  private ServiceConnection serviceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
      omniServiceMessenger = new Messenger(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
      omniServiceMessenger = null;
    }
  };
  private Context context;

  public interface View {
    void setFragment(Fragment activityFragment);
  }

  @Inject
  public OmniPresenter(Context context) {
    this.context = context;
  }

  @Override
  public void initialize() {
    context.bindService(OmniService.getIntent(), serviceConnection, Context.BIND_AUTO_CREATE);
    setActivityFragment();
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  public void stop() {
    context.unbindService(serviceConnection);
  }

  // TODO: this does not belong here
  public void refreshClippings() {
    try {
      omniServiceMessenger.send(Message.obtain(null, OmniService.MSG_REFRESH_OMNI_CLIPBOARD));
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
