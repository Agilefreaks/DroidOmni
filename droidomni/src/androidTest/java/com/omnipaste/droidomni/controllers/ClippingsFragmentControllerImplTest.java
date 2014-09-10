package com.omnipaste.droidomni.controllers;

import android.os.Bundle;
import android.os.Parcelable;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClippingsFragmentControllerImplTest extends InstrumentationTestCase {
  private ClippingsFragmentControllerImpl controller;

  @Mock
  Observer<ClippingDto> mockObserver;

  @Mock
  ActionBarController mockActionBarController;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    MockitoAnnotations.initMocks(this);

    controller = new ClippingsFragmentControllerImpl();
    controller.actionBarController = mockActionBarController;
  }

  @SuppressWarnings("unchecked")
  public void testOnEventMainThread() throws Exception {
    ClippingDto clippingDto = new ClippingDto();
    controller.getObservable().subscribe(mockObserver);

    controller.setClipping(clippingDto);

    verify(mockObserver, times(1)).onNext(clippingDto);
  }

  public void testRunWhenSavedInstanceHasClippingsItWillFireOnNext() {
    ClippingDto clippingDto = new ClippingDto();
    controller.getObservable().subscribe(mockObserver);

    Bundle savedInstance = new Bundle();
    savedInstance.putParcelableArray(ClippingsFragment.CLIPPINGS_PARCEL, new ClippingDto[]{clippingDto});

    controller.run(new ClippingsFragment(), savedInstance);

    verify(mockObserver, times(1)).onNext(clippingDto);
  }

  public void testRunWhenSavedInstanceHasClippingsNullWillNotFail() {
    controller.getObservable().subscribe(mockObserver);

    Bundle savedInstance = new Bundle();

    controller.run(new ClippingsFragment(), savedInstance);
  }

  public void testRunWhenSavedInstanceDoesNotHaveClippingsNullWillNotFail() {
    controller.getObservable().subscribe(mockObserver);

    Bundle savedInstance = new Bundle();
    savedInstance.putParcelableArray(ClippingsFragment.CLIPPINGS_PARCEL, new Parcelable[] { });

    controller.run(new ClippingsFragment(), savedInstance);
  }
}
