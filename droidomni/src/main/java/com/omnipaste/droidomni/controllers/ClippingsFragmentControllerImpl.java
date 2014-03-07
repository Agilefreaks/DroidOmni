package com.omnipaste.droidomni.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;

import com.omnipaste.droidomni.adapters.ClippingAdapter;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.droidomni.fragments.ClippingsFragment;
import com.omnipaste.droidomni.services.NotificationService;
import com.omnipaste.droidomni.services.NotificationServiceImpl;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class ClippingsFragmentControllerImpl implements ClippingsFragmentController {
  private ClippingAdapter clippingAdapter;
  private ClippingsFragment fragment;
  private NotificationService notificationService;
  private NotificationManager notificationManager;
  private EventBus eventBus = EventBus.getDefault();

  @Inject
  public ClippingsFragmentControllerImpl(NotificationService notificationService, NotificationManager notificationManager) {
    this.notificationManager = notificationManager;
    this.notificationService = notificationService;
  }

  @Override
  public void run(ClippingsFragment clippingsFragment, Bundle savedInstance) {
    this.fragment = clippingsFragment;

    eventBus.register(this);

    clippingsFragment.setRetainInstance(true);
    clippingAdapter = new ClippingAdapter();
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
  }

  @Override
  public void afterView() {
    if (fragment.clippings.getAdapter() == null) {
      fragment.clippings.setAdapter(clippingAdapter);
    }
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(ClippingAdded event) {
    ClippingDto clipping = event.getClipping();

    setClipping(clipping);

    Notification notification;
    if (clipping.getType() == ClippingDto.ClippingType.unknown) {
      notification = notificationService.buildSimpleNotification(fragment.getActivity(), clipping.getContent());
    }
    else {
      notification = notificationService.buildSmartActionNotification(fragment.getActivity(), clipping);
    }

    notificationManager.notify(NotificationServiceImpl.NOTIFICATION_ID, notification);
  }

  private void setClipping(ClippingDto clippingDto) {
    clippingAdapter.add(clippingDto);
  }
}
