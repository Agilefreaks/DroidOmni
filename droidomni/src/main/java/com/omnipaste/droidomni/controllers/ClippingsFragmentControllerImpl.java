package com.omnipaste.droidomni.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;

import com.google.common.collect.EvictingQueue;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.ClippingsPagerAdapter;
import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.droidomni.events.OmniClipboardRefresh;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment;
import com.omnipaste.droidomni.services.NotificationService;
import com.omnipaste.droidomni.services.NotificationServiceImpl;
import com.omnipaste.droidomni.services.OmniService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.subjects.ReplaySubject;

public class ClippingsFragmentControllerImpl implements ClippingsFragmentController {
  private final EventBus eventBus = EventBus.getDefault();
  private ClippingsFragment fragment;
  private ReplaySubject<ClippingDto> clippingsSubject;
  private EvictingQueue<ClippingDto> clippings;
  private Messenger omniServiceMessenger;

  private ServiceConnection serviceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
      omniServiceMessenger = new Messenger(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
      omniServiceMessenger = null;
    }
  };

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

    if (clippingsFragment.getActivity() != null) {
      clippingsFragment.getActivity().bindService(OmniService.getIntent(), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    if (savedInstance != null) {
      Parcelable[] parcelableArray = savedInstance.getParcelableArray(ClippingsFragment.CLIPPINGS_PARCEL);

      if (parcelableArray != null && parcelableArray instanceof ClippingDto[]) {
        Collections.addAll(clippings, (ClippingDto[]) parcelableArray);

        for (ClippingDto clipping : clippings) {
          clippingsSubject.onNext(clipping);
        }
      }
    }

    this.fragment = clippingsFragment;
  }

  @Override
  public void stop() {
    eventBus.unregister(this);
    fragment.getActivity().unbindService(serviceConnection);
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

  @SuppressWarnings("UnusedDeclaration")
  public void onEventMainThread(OmniClipboardRefresh event) {
    try {
      omniServiceMessenger.send(Message.obtain(null, OmniService.MSG_REFRESH_OMNI_CLIPBOARD));
    } catch (RemoteException ignored) {
    }
  }

  public void setClipping(ClippingDto clippingDto) {
    clippingsSubject.onNext(clippingDto);
  }

  public void notifyClipping(ClippingDto clipping) {
    Notification notification;
    if (clipping.getType() == ClippingDto.ClippingType.UNKNOWN) {
      notification = notificationService.buildSimpleNotification(DroidOmniApplication.getAppContext(), clipping.getContent());
    } else {
      notification = notificationService.buildSmartActionNotification(DroidOmniApplication.getAppContext(), clipping);
    }

    notificationManager.notify(NotificationServiceImpl.NOTIFICATION_ID, notification);
  }
}
