package com.omnipaste.droidomni.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.actionbar.SimpleTabListener;
import com.omnipaste.droidomni.adapters.ClippingsPagerAdapter;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.droidomni.fragments.clippings.AllFragment;
import com.omnipaste.droidomni.fragments.clippings.AllFragment_;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment;
import com.omnipaste.droidomni.fragments.clippings.CloudFragment;
import com.omnipaste.droidomni.fragments.clippings.CloudFragment_;
import com.omnipaste.droidomni.fragments.clippings.LocalFragment;
import com.omnipaste.droidomni.fragments.clippings.LocalFragment_;
import com.omnipaste.droidomni.services.NotificationService;
import com.omnipaste.droidomni.services.NotificationServiceImpl;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class ClippingsFragmentControllerImpl extends SimpleTabListener implements ClippingsFragmentController {
  private EventBus eventBus = EventBus.getDefault();

  private ClippingsFragment fragment;
  private ClippingsPagerAdapter clippingsPagerAdapter;
  private AllFragment allClippingsFragment;
  private LocalFragment localFragment;
  private CloudFragment cloudFragment;
  private PublishSubject<ClippingDto> clippingsSubject;

  @Inject
  public NotificationService notificationService;

  @Inject
  public NotificationManager notificationManager;

  @Inject
  public ActionBarController actionBarController;

  public ClippingsFragmentControllerImpl() {
    DroidOmniApplication.inject(this);

    clippingsSubject = PublishSubject.create();

    allClippingsFragment = AllFragment_.builder().build();
    localFragment = LocalFragment_.builder().build();
    cloudFragment = CloudFragment_.builder().build();
  }

  @Override
  public void run(ClippingsFragment clippingsFragment, Bundle savedInstance) {
    clippingsFragment.setRetainInstance(true);
    eventBus.register(this);

    this.fragment = clippingsFragment;

    allClippingsFragment.setTitle(fragment.clippingsTabAll);
    localFragment.setTitle(fragment.clippingsTabLocal);
    cloudFragment.setTitle(fragment.clippingsTabCloud);

    allClippingsFragment.observe(clippingsSubject);
    localFragment.observe(clippingsSubject);
    cloudFragment.observe(clippingsSubject);

    clippingsPagerAdapter = new ClippingsPagerAdapter(clippingsFragment.getChildFragmentManager());
    clippingsPagerAdapter.addFragment(allClippingsFragment);
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @Override
  public void afterView() {
    if (fragment.clippingsPager.getAdapter() == null) {
      fragment.clippingsPager.setAdapter(clippingsPagerAdapter);
      fragment.clippingsPager.setOnPageChangeListener(this);

      actionBarController.setTitle(R.string.clippings_title);
    }
  }

  public Subscription subscribe(Observer<ClippingDto> observer) {
    return clippingsSubject.subscribe(observer);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(ClippingAdded event) {
    ClippingDto clipping = event.getClipping();

    setClipping(clipping);
    notifyClipping(clipping);
  }

  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    fragment.clippingsPager.setCurrentItem(tab.getPosition(), true);
  }

  @Override
  public void onPageSelected(int position) {
    actionBarController.setSelectedNavigationItem(position);
  }

  public void setClipping(ClippingDto clippingDto) {
    clippingsSubject.onNext(clippingDto);

    if (clippingsPagerAdapter != null &&
        clippingsPagerAdapter.getCount() == 1 &&
        cloudFragment.getActualListAdapter().getCount() > 0) {
      clippingsPagerAdapter.addFragment(localFragment);
      clippingsPagerAdapter.addFragment(cloudFragment);

      fragment.clippingsTabs.setViewPager(fragment.clippingsPager);
      fragment.clippingsTabs.setVisibility(View.VISIBLE);
    }
  }

  public void notifyClipping(ClippingDto clipping) {
    Notification notification;
    if (clipping.getType() == ClippingDto.ClippingType.unknown) {
      notification = notificationService.buildSimpleNotification(DroidOmniApplication.getAppContext(), clipping.getContent());
    } else {
      notification = notificationService.buildSmartActionNotification(DroidOmniApplication.getAppContext(), clipping);
    }

    notificationManager.notify(NotificationServiceImpl.NOTIFICATION_ID, notification);
  }
}
