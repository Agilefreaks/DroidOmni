package com.omnipaste.droidomni.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.Helpers;
import com.omnipaste.droidomni.NavigationMenu;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.droidomni.events.NavigationItemClicked;
import com.omnipaste.droidomni.fragments.ClippingsFragment;
import com.omnipaste.droidomni.fragments.ClippingsFragment_;
import com.omnipaste.droidomni.services.NotificationService;
import com.omnipaste.droidomni.services.NotificationServiceImpl;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.droidomni.services.SessionService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.util.functions.Action0;

public class OmniController implements OmniActivityController {
  private final SessionService sessionService;
  private EventBus eventBus = EventBus.getDefault();
  private OmniActivity activity;
  private ClippingsFragment clippingsFragment;

  @Inject
  public NotificationService notificationService;

  @Inject
  public NotificationManager notificationManager;

  @Inject
  public OmniController(SessionService sessionService) {
    DroidOmniApplication.inject(this);
    this.sessionService = sessionService;
  }

  @Override
  public void run(OmniActivity omniActivity, Bundle savedInstance) {
    eventBus.register(this);
    activity = omniActivity;

    if (savedInstance == null) {
      setInitialFragment();
    }
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(NavigationItemClicked event) {
    if (event.getNavigationDrawerItem().getNavigationMenu() == NavigationMenu.SignOut) {
      OmniService.stop(activity).
          subscribeOn(Schedulers.io()).
          observeOn(AndroidSchedulers.mainThread()).
          doOnCompleted(new Action0() {
            @Override
            public void call() {
              sessionService.logout();

              activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity_.class));
              activity.finish();
            }
          }).subscribe();
    }
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(ClippingAdded event) {
    ClippingDto clipping = event.getClipping();

    clippingsFragment.setClipping(clipping);

    Notification notification;
    if (clipping.getType() == ClippingDto.ClippingType.unknown) {
      notification = notificationService.buildSimpleNotification(activity, clipping.getContent());
    }
    else {
      notification = notificationService.buildSmartActionNotification(activity, clipping);
    }

    notificationManager.notify(NotificationServiceImpl.NOTIFICATION_ID, notification);
  }

  private void setTitle(int title) {
    getActionBar().setTitle(title);
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
    clippingsFragment = ClippingsFragment_.builder().build();

    setFragment(clippingsFragment);
    setTitle(R.string.clippings_title);
    setSubtitle(sessionService.getChannel());
  }

  private void setFragment(Fragment fragment) {
    Helpers.setFragment(activity, R.id.omni_container, fragment);
  }
}
