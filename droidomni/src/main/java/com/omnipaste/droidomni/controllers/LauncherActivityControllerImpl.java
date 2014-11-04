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

import com.omnipaste.droidomni.activities.LauncherActivity;
import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.fragments.DeviceInitErrorFragment;
import com.omnipaste.droidomni.fragments.LoadingFragment;
import com.omnipaste.droidomni.fragments.LoadingFragment_;
import com.omnipaste.droidomni.fragments.LoginFragment;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.services.AccountsService;
import com.omnipaste.droidomni.services.FragmentService;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.omniapi.prefs.ApiClientToken;
import com.omnipaste.omniapi.resource.v1.AuthorizationCodes;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.AuthorizationCodeDto;
import com.omnipaste.omnicommon.prefs.StringPreference;

import org.apache.http.HttpStatus;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LauncherActivityControllerImpl implements MainActivityController {
  private final EventBus eventBus = EventBus.getDefault();

  private LauncherActivity activity;
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
      sendMessageToOmniService(OmniService.MSG_REGISTER_CLIENT);
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

  @Inject
  public AccountsService accountsService;

  @Inject
  public AuthorizationCodes authorizationCodes;

  @Inject @ApiClientToken
  public StringPreference apiClientToken;

  @Override
  public void run(LauncherActivity launcherActivity, Bundle savedInstanceState) {
    eventBus.register(this);
    activity = launcherActivity;

    if (savedInstanceState == null) {
      setInitialFragment();
    }
  }

  @Override
  public void stop() {
    if (omniServiceMessenger != null) {
      sendMessageToOmniService(OmniService.MSG_UNREGISTER_CLIENT);
      activity.unbindService(serviceConnection);
    }

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
    LoadingFragment loadingFragment = LoadingFragment_.builder().build();
    setFragment(loadingFragment);

    if (sessionService.isLogged()) {
      activity.startService(OmniService.getIntent());
      activity.bindService(OmniService.getIntent(), serviceConnection, Context.BIND_AUTO_CREATE);
    } else {
      String[] googleEmails = accountsService.getGoogleEmails();

      authorizationCodes.get(apiClientToken.get(), googleEmails)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              new Action1<AuthorizationCodeDto>() {
                @Override
                public void call(AuthorizationCodeDto authorizationCodeDto) {
                  sessionService.login(authorizationCodeDto.getCode(),
                      new Action1<AccessTokenDto>() {
                        @Override
                        public void call(AccessTokenDto accessTokenDto) {
                          eventBus.post(new LoginEvent());
                        }
                      },
                      new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                          LoginFragment loginFragment = LoginFragment_.builder().build();
                          setFragment(loginFragment);
                        }
                      });
                }
              },
              new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                  LoginFragment loginFragment = LoginFragment_.builder().build();
                  setFragment(loginFragment);
                }
              });
    }
  }

  private void setFragment(Fragment fragment) {
//    fragmentService.setFragment(activity, R.id.main_container, fragment);
  }

  private void sendMessageToOmniService(int message) {
    Message registerClientMessage = Message.obtain(null, message);
    registerClientMessage.replyTo = messenger;

    try {
      omniServiceMessenger.send(registerClientMessage);
    } catch (RemoteException ignored) {
    }
  }
}
