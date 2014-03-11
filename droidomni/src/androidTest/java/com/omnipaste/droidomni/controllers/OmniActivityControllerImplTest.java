package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.services.SessionService;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OmniActivityControllerImplTest extends TestCase {
  private OmniActivityControllerImpl controller;

  @Mock
  public ActionBarController mockActionBarController;

  @Mock
  public SessionService mockSessionService;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    MockitoAnnotations.initMocks(this);

    controller = new OmniActivityControllerImpl(mockSessionService);
    controller.actionBarController = mockActionBarController;
  }

  public void testRunWillCallActionBarControllerRun() throws Exception {
    OmniActivity omniActivity = new OmniActivity();

    controller.run(omniActivity, new Bundle());

    verify(mockActionBarController, times(1)).run(omniActivity);
  }

  public void testRunWillSetSubtitle() throws Exception {
    when(mockSessionService.getChannel()).thenReturn("test@test.com");

    controller.run(new OmniActivity(), new Bundle());

    verify(mockActionBarController, times(1)).setSubtitle("test@test.com");
  }

  public void testStopWillCallActionBarControllerStop() throws Exception {
    controller.actionBarController = mockActionBarController;

    controller.stop();

    verify(mockActionBarController, times(1)).stop();
  }
}
