package com.omnipaste.droidomni.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;

import com.google.common.collect.EvictingQueue;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.ClippingsPagerAdapter;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment;
import com.omnipaste.droidomni.services.NotificationService;
import com.omnipaste.droidomni.services.NotificationServiceImpl;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.subjects.ReplaySubject;

public class ClippingsFragmentControllerImpl implements ClippingsFragmentController {
  private EventBus eventBus = EventBus.getDefault();
  private ClippingsFragment fragment;
  private ReplaySubject<ClippingDto> clippingsSubject;
  private EvictingQueue<ClippingDto> clippings;

  @Inject
  public NotificationService notificationService;

  @Inject
  public NotificationManager notificationManager;

  @Inject
  public ActionBarController actionBarController;

  public ClippingsFragmentControllerImpl() {
    clippings = EvictingQueue.create(42);
    clippingsSubject = ReplaySubject.create();
  }

  @Override
  public void run(ClippingsFragment clippingsFragment, Bundle savedInstance) {
    eventBus.register(this);

    if (savedInstance != null) {
      Collections.addAll(clippings, (ClippingDto[]) savedInstance.getParcelableArray(ClippingsFragment.CLIPPINGS_PARCEL));

      for (ClippingDto clipping : clippings) {
        clippingsSubject.onNext(clipping);
      }
    }

    this.fragment = clippingsFragment;
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @Override
  public void afterView() {
    ClippingsPagerAdapter clippingsPagerAdapter = new ClippingsPagerAdapter(fragment.getChildFragmentManager(), fragment.getActivity());
    fragment.setAdapter(clippingsPagerAdapter);
    fragment.setViewPager();

    actionBarController.setTitle(R.string.clippings_title);
  }

  @Override
  public Observable<ClippingDto> getObservable() {
    return clippingsSubject;
  }

  @Override
  public Collection<ClippingDto> getClippings() {
    return clippings;
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(ClippingAdded event) {
    ClippingDto clipping = event.getClipping();
    clippings.add(clipping);

    setClipping(clipping);
    notifyClipping(clipping);
  }

  public void setClipping(ClippingDto clippingDto) {
    clippingsSubject.onNext(clippingDto);
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
