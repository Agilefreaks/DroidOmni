package com.omnipaste.droidomni.controllers;

import android.app.NotificationManager;
import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.events.ClippingAdded;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment_;
import com.omnipaste.droidomni.services.NotificationService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.hamcrest.core.IsInstanceOf;

import rx.Observable;
import rx.Observer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClippingsFragmentControllerImplTest extends InstrumentationTestCase {
  ClippingsFragmentControllerImpl controller;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    controller = new ClippingsFragmentControllerImpl();
    controller.notificationManager = mock(NotificationManager.class);
    controller.notificationService = mock(NotificationService.class);
    controller.run(ClippingsFragment_.builder().build(), new Bundle());
  }

  public void testGetObservableWillReturnAnObservable() throws Exception {
    assertThat(controller.getObservable(), IsInstanceOf.any(Observable.class));
  }

  @SuppressWarnings("unchecked")
  public void testOnEventMainThread() throws Exception {
    Observer mockObserver = mock(Observer.class);
    ClippingDto clippingDto = new ClippingDto();
    controller.getObservable().subscribe(mockObserver);

    controller.onEventMainThread(new ClippingAdded(clippingDto));

    verify(mockObserver, times(1)).onNext(clippingDto);
  }
}
