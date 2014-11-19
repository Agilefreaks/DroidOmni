package com.omnipaste.droidomni.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.subjects.PublishSubject;

@Singleton
public class OmniServiceConnection implements ServiceConnection {
  private final Context context;
  private final PublishSubject<Object> connectionSubject = PublishSubject.create();
  private Messenger omniServiceMessenger;
  private Messenger omniMessenger = new Messenger(new OmniIncomingHandler(connectionSubject));

  @Inject
  public OmniServiceConnection(Context context) {
    this.context = context;
  }

  @Override
  public void onServiceConnected(ComponentName componentName, IBinder service) {
    omniServiceMessenger = new Messenger(service);

    Message message = Message.obtain(null, OmniServiceIncomingHandler.MSG_REGISTER_CLIENT);
    message.replyTo = omniMessenger;
    try {
      omniServiceMessenger.send(message);
    } catch (RemoteException ignore) {
    }
  }

  @Override
  public void onServiceDisconnected(ComponentName componentName) {
    omniServiceMessenger = null;
  }

  public Observable<Object> startOmniService() {
    context.bindService(OmniService.getIntent(), this, Context.BIND_AUTO_CREATE);
    return connectionSubject;
  }

  public Observable<Object> stopOmniService() {
    context.unbindService(this);
    return connectionSubject;
  }

  public void restartOmniService() {
    stopOmniService();
    startOmniService();
  }
}
