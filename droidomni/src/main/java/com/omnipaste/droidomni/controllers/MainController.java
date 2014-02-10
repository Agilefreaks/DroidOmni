package com.omnipaste.droidomni.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.LoginActivity_;
import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.events.DeviceInitEvent;
import com.omnipaste.droidomni.events.NavigationItemClicked;
import com.omnipaste.droidomni.fragments.ClippingsFragment_;
import com.omnipaste.droidomni.fragments.MainFragment_;
import com.omnipaste.droidomni.services.OmniService_;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import rx.util.functions.Action0;

public class MainController implements MainActivityController {
  public final static String DEVICE_IDENTIFIER_EXTRA_KEY = "device_identifier";

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

    this.activity = mainActivity;

    if (savedInstanceState == null) {
      setInitialFragment();
    }
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(DeviceInitEvent event) {
    startService(event).
        observeOn(AndroidSchedulers.mainThread()).
        doOnCompleted(new Action0() {
          @Override
          public void call() {
            setFragment(ClippingsFragment_.builder().build());
            setTitle(R.string.clippings_title);
          }
        }).subscribe();
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(NavigationItemClicked event) {
    if (event.getNavigationDrawerItem().getNavigationMenu() == NavigationMenu.SignOut) {
      stopService().
          observeOn(AndroidSchedulers.mainThread()).
          doOnCompleted(new Action0() {
            @Override
            public void call() {
              // remove channel
              Configuration configuration = configurationService.getConfiguration();
              configuration.setChannel(null);
              configurationService.setConfiguration(configuration);

              setInitialFragment();
            }
          }).subscribe();
    }
  }

  private void setFragment(Fragment fragment) {
    FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.main_container, fragment)
        .commitAllowingStateLoss();

    supportFragmentManager.executePendingTransactions();
  }

  private Observable startService(final DeviceInitEvent deviceInitEvent) {
    return Observable.create(new Observable.OnSubscribeFunc() {
      @Override
      public Subscription onSubscribe(Observer observer) {
        Intent service = new Intent(activity, OmniService_.class);
        service.putExtra(DEVICE_IDENTIFIER_EXTRA_KEY, deviceInitEvent.getRegisteredDeviceDto().getIdentifier());
        activity.startService(service);

        observer.onCompleted();

        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.immediate());
  }

  private Observable stopService() {
    return Observable.create(new Observable.OnSubscribeFunc() {
      @Override
      public Subscription onSubscribe(Observer observer) {
        Intent service = new Intent(activity, OmniService_.class);
        activity.stopService(service);

        observer.onCompleted();

        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.immediate());
  }

  private void setTitle(int clippings_title) {
    getActionBar().setTitle(clippings_title);
  }

  private void setSubtitle(String subtitle) {
    getActionBar().setSubtitle(subtitle);
  }

  private ActionBar getActionBar() {
    ActionBar actionBar = activity.getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    return actionBar;
  }

  private void setInitialFragment() {
    Configuration configuration = configurationService.getConfiguration();

    if (configuration.hasChannel()) {
      setFragment(MainFragment_.builder().build());
      setSubtitle(configuration.getChannel());
    } else {
      Intent intent = new Intent(activity, LoginActivity_.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      activity.startActivity(intent);
    }
  }
}
