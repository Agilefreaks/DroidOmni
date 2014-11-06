package com.omnipaste.droidomni.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.omnipaste.droidomni.service.OmniService;

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
  }

  @Inject
  public OmniPresenter(Context context) {
    this.context = context;
  }

  @Override
  public void initialize() {
    context.bindService(OmniService.getIntent(), serviceConnection, Context.BIND_AUTO_CREATE);
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

}
