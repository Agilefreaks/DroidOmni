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
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class OmniServiceConnection implements ServiceConnection {
  private final Context context;
  private final Messenger omniMessenger = new Messenger(new OmniIncomingHandler(this));
  private final PublishSubject<State> serviceStateObservable = PublishSubject.create();
  private Messenger omniServiceMessenger;
  private Throwable lastError;

  public enum State {
    started,
    stopped,
    error
  }

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

  public Observable<State> startOmniService() {
    context.bindService(OmniService.getIntent(), this, Context.BIND_AUTO_CREATE);
    context.startService(OmniService.getIntent());

    return serviceStateObservable;
  }

  public Observable<State> stopOmniService() {
    context.stopService(OmniService.getIntent());
    context.unbindService(this);

    return serviceStateObservable;
  }

  public Observable<State> restartOmniService() {
    stopOmniService();
    serviceStateObservable
        .takeFirst(new Func1<State, Boolean>() {
          @Override public Boolean call(State state) {
            return state == State.stopped;
          }
        })
        .subscribe(new Action1<State>() {
          @Override public void call(State state) {
            startOmniService();
          }
        });

    return serviceStateObservable;
  }

  public Throwable getLastError() {
    return lastError;
  }

  public void serviceStarted() {
    serviceStateObservable.onNext(State.started);
  }

  public void serviceStopped() {
    serviceStateObservable.onNext(State.stopped);
  }

  public void serviceError(Throwable obj) {
    lastError = obj;
    serviceStateObservable.onNext(State.error);
  }
}
