package com.omnipaste.droidomni.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.events.DeviceInitErrorEvent;
import com.omnipaste.droidomni.events.DeviceInitEvent;
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
  private SessionService sessionService;

  @Inject
  public FragmentService fragmentService;

  @Inject
  public MainActivityControllerImpl(SessionService sessionService) {
    DroidOmniApplication.inject(this);

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
    setFragment(DeviceInitFragment_.builder().build());
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(DeviceInitEvent event) {
    OmniService.start(event.getRegisteredDeviceDto());

    activity.startActivity(OmniActivity.getIntent(activity));
    activity.finish();
  }

  @SuppressWarnings({"UnusedDeclaration"})
  public void onEventMainThread(DeviceInitErrorEvent event) {
    if (isBadRequest(event.getError())) {
      sessionService.logout();
      setFragment(LoginFragment_.builder().build());
    }
    else {
      setFragment(DeviceInitErrorFragment.build(event.getError()));
    }
  }

  private boolean isBadRequest(Throwable error) {
    RetrofitError retrofitError = error instanceof RetrofitError ? (RetrofitError)error : null;

    return retrofitError != null && retrofitError.getResponse() != null && retrofitError.getResponse().getStatus() == HttpStatus.SC_BAD_REQUEST;
  }

  private void setInitialFragment() {
    Fragment fragmentToShow;

    if (sessionService.login()) {
      fragmentToShow = DeviceInitFragment_.builder().build();
    } else {
      fragmentToShow = LoginFragment_.builder().build();
    }

    setFragment(fragmentToShow);
  }

  private void setFragment(Fragment fragment) {
    fragmentService.setFragment(activity, R.id.main_container, fragment);
  }
}
