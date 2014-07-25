package com.omnipaste.droidomni.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.fragments.DeviceInitErrorFragment;
import com.omnipaste.droidomni.fragments.DeviceInitFragment_;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.services.FragmentService;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.droidomni.services.SessionService;

import org.apache.http.HttpStatus;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;

public class MainActivityControllerImpl implements MainActivityController {
  private EventBus eventBus = EventBus.getDefault();
  private MainActivity activity;
  private Messenger omniServiceMessenger;

  private final Messenger messenger = new Messenger(new IncomingHandler());

  private class IncomingHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case OmniService.MSG_CREATE_ERROR:
          Throwable error = (Throwable) msg.obj;

          activity.unbindService(serviceConnection);
          activity.stopService(OmniService.getIntent());

          if (isBadRequest(error)) {
            sessionService.logout();
            setFragment(LoginFragment_.builder().build());
          } else {
            setFragment(DeviceInitErrorFragment.build(error));
          }

          break;
        case OmniService.MSG_STARTED:
          activity.unbindService(serviceConnection);
          activity.startActivity(OmniActivity.getIntent(activity));
          activity.finish();

          break;
      }
    }
  }

  private ServiceConnection serviceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
      omniServiceMessenger = new Messenger(service);

      Message registerClientMessage = Message.obtain(null, OmniService.MSG_REGISTER_CLIENT);
      registerClientMessage.replyTo = messenger;

      try {
        omniServiceMessenger.send(registerClientMessage);
      } catch (RemoteException ignored) {
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
      omniServiceMessenger = null;
    }
  };

  @Inject
  public FragmentService fragmentService;

  @Inject
  public SessionService sessionService;

  @Override
  public void run(MainActivity mainActivity, Bundle savedInstanceState) {
    eventBus.register(this);
    activity = mainActivity;

    if (savedInstanceState == null) {
      setInitialFragment();
    }
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(LoginEvent event) {
    setInitialFragment();
  }

  private boolean isBadRequest(Throwable error) {
    RetrofitError retrofitError = error instanceof RetrofitError ? (RetrofitError) error : null;

    return retrofitError != null && retrofitError.getResponse() != null && retrofitError.getResponse().getStatus() == HttpStatus.SC_BAD_REQUEST;
  }

  private void setInitialFragment() {
    Fragment fragmentToShow;

    if (sessionService.login()) {
      fragmentToShow = DeviceInitFragment_.builder().build();
      activity.startService(OmniService.getIntent());
      activity.bindService(OmniService.getIntent(), serviceConnection, Context.BIND_AUTO_CREATE);
    } else {
      fragmentToShow = LoginFragment_.builder().build();
    }

    setFragment(fragmentToShow);
  }

  private void setFragment(Fragment fragment) {
    fragmentService.setFragment(activity, R.id.main_container, fragment);
  }
}
