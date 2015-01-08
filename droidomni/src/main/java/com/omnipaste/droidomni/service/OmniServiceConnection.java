package com.omnipaste.droidomni.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.omnipaste.omnicommon.rx.Schedulable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class OmniServiceConnection extends Schedulable implements ServiceConnection {
  private final Context context;
  private final SessionService sessionService;
  private final Messenger omniMessenger = new Messenger(new OmniIncomingHandler(this));
  private final PublishSubject<State> serviceStateObservable;
  private final AtomicBoolean stopping = new AtomicBoolean(false);
  private Messenger omniServiceMessenger;
  private Throwable lastError;

  public enum State {
    started,
    stopped,
    error,
    timeout
  }

  @Inject
  public OmniServiceConnection(Context context, SessionService sessionService) {
    this.context = context;
    this.sessionService = sessionService;

    serviceStateObservable = PublishSubject.create();
  }

  public OmniServiceConnection(Context context, SessionService sessionService, PublishSubject<State> serviceStateObservable) {
    this.context = context;
    this.sessionService = sessionService;

    this.serviceStateObservable = serviceStateObservable;
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

  public void stopOmniService(final Action0 action) {
    if (stopping.get()) {
      return;
    }

    stopping.set(true);

    stopOmniService()
        .timeout(500, TimeUnit.MILLISECONDS, Observable.just(OmniServiceConnection.State.timeout), observeOnScheduler)
        .takeFirst(new Func1<OmniServiceConnection.State, Boolean>() {
          @Override public Boolean call(OmniServiceConnection.State state) {
            return state == OmniServiceConnection.State.stopped ||
                state == OmniServiceConnection.State.error ||
                state == OmniServiceConnection.State.timeout;
          }
        })
        .doOnCompleted(new Action0() {
          @Override public void call() {
            sessionService.setDeviceDto(null);
            stopping.set(false);
            action.call();
          }
        })
        .subscribe();
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
