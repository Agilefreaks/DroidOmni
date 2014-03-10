package com.omnipaste.droidomni.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

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
import rx.Observable;
import rx.subjects.PublishSubject;

public class ClippingsFragmentControllerImpl extends SimpleTabListener implements ClippingsFragmentController {
  private EventBus eventBus = EventBus.getDefault();

  private ClippingsFragment fragment;
  private ClippingsPagerAdapter clippingsPagerAdapter;
  private AllFragment allClippingsFragment;
  private LocalFragment localFragment;
  private CloudFragment cloudFragment;
  private ActionBarController actionBarController;
  private PublishSubject<ClippingDto> clippingsSubject;

  @Inject
  public NotificationService notificationService;

  @Inject
  public NotificationManager notificationManager;

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
    this.actionBarController = clippingsFragment.actionBarController;

    allClippingsFragment.observer(this.getObservable());
    localFragment.observer(this.getObservable());
    cloudFragment.observer(this.getObservable());

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

      actionBarController.addTab(R.string.clippings_tab_all, this);
      actionBarController.addTab(R.string.clippings_tab_local, this);
      actionBarController.addTab(R.string.clippings_tab_cloud, this);

      actionBarController.setTitle(R.string.clippings_title);
    }
  }

  @Override
  public Observable<ClippingDto> getObservable() {
    return clippingsSubject;
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

  private void setClipping(ClippingDto clippingDto) {
    clippingsSubject.onNext(clippingDto);

    if (clippingsPagerAdapter.getCount() == 1 &&
        cloudFragment.getActualListAdapter().getCount() > 0) {
      clippingsPagerAdapter.addFragment(localFragment);
      clippingsPagerAdapter.addFragment(cloudFragment);

      actionBarController.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }
  }

  private void notifyClipping(ClippingDto clipping) {
    Notification notification;
    if (clipping.getType() == ClippingDto.ClippingType.unknown) {
      notification = notificationService.buildSimpleNotification(fragment.getActivity(), clipping.getContent());
    } else {
      notification = notificationService.buildSmartActionNotification(fragment.getActivity(), clipping);
    }

    notificationManager.notify(NotificationServiceImpl.NOTIFICATION_ID, notification);
  }
}
