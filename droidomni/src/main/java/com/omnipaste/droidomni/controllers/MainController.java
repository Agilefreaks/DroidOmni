package com.omnipaste.droidomni.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.Helpers;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.activities.OmniActivity_;
import com.omnipaste.droidomni.events.DeviceInitEvent;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.fragments.DeviceInitFragment_;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.droidomni.services.SessionService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.util.functions.Action0;

public class MainController implements MainActivityController {
  private EventBus eventBus = EventBus.getDefault();
  private MainActivity activity;
  private SessionService sessionService;

  @Inject
  public MainController(SessionService sessionService) {
    this.sessionService = sessionService;
  }

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
    sessionService.login(event.getChannel());

    setFragment(DeviceInitFragment_.builder().build());
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(DeviceInitEvent event) {
    OmniService.start(activity, event.getRegisteredDeviceDto()).
        subscribeOn(Schedulers.io()).
        observeOn(AndroidSchedulers.mainThread()).
        doOnCompleted(new Action0() {
          @Override
          public void call() {
            activity.startActivity(new Intent(activity.getApplicationContext(), OmniActivity_.class));
            activity.finish();
          }
        }).subscribe();
  }

  private void setInitialFragment() {
    Fragment fragmentToShow;

    if (sessionService.isLogged()) {
      fragmentToShow = DeviceInitFragment_.builder().build();
    } else {
      fragmentToShow = LoginFragment_.builder().build();
    }

    setFragment(fragmentToShow);
  }

  private void setFragment(Fragment fragment) {
    Helpers.setFragment(activity, R.id.main_container, fragment);
  }
}
