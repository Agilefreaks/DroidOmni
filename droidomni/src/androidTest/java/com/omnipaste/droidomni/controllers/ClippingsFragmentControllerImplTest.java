package com.omnipaste.droidomni.controllers;

import android.test.InstrumentationTestCase;

import com.omnipaste.omnicommon.dto.ClippingDto;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClippingsFragmentControllerImplTest extends InstrumentationTestCase {
  private ClippingsFragmentControllerImpl controller;

  @Mock
  Observer<ClippingDto> observer;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    MockitoAnnotations.initMocks(this);

    controller = new ClippingsFragmentControllerImpl();
  }

  @SuppressWarnings("unchecked")
  public void testOnEventMainThread() throws Exception {
    ClippingDto clippingDto = new ClippingDto();
    controller.subscribe(observer);

    controller.setClipping(clippingDto);

    verify(observer, times(1)).onNext(clippingDto);
  }
}
