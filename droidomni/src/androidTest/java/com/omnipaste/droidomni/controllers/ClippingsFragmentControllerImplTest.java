package com.omnipaste.droidomni.controllers;

import android.support.v7.app.ActionBar;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment;
import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment_;
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
    controller.subscribe(mockObserver);

    controller.setClipping(clippingDto);

    verify(mockObserver, times(1)).onNext(clippingDto);
  }

  public void testOnActivityCreateWithAllClippingsFragmentWillSetNavigationModeStandard() throws Exception {
    ClippingsFragment clippingsFragment = ClippingsFragment_.builder().build();
    controller.run(clippingsFragment, null);

    controller.onActivityCreate();

    verify(mockActionBarController, times(1)).setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  }
}
