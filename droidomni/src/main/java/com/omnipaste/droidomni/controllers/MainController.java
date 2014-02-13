package com.omnipaste.droidomni.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.activities.OmniActivity_;
import com.omnipaste.droidomni.events.DeviceInitEvent;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.droidomni.fragments.DeviceInitFragment_;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.util.functions.Action0;

public class MainController implements MainActivityController {
  private EventBus eventBus = EventBus.getDefault();
  private ConfigurationService configurationService;
  private MainActivity activity;

  @Inject
  public MainController(ConfigurationService configurationService) {
    this.configurationService = configurationService;
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
    OmniService.start(activity, event.getRegisteredDeviceDto()).
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
    Configuration configuration = configurationService.getConfiguration();
    Fragment fragmentToShow;

    if (configuration.hasChannel()) {
      fragmentToShow = DeviceInitFragment_.builder().build();
    } else {
      fragmentToShow = LoginFragment_.builder().build();
    }

    setFragment(fragmentToShow);
  }

  private void setFragment(Fragment fragment) {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.main_container, fragment)
        .commitAllowingStateLoss();

    supportFragmentManager.executePendingTransactions();
  }
}
