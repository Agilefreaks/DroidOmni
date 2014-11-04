package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.service.SessionService;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    controller = new OmniActivityControllerImpl();
    controller.sessionService = mockSessionService;
    controller.actionBarController = mockActionBarController;
  }

  public void testRunWillCallActionBarControllerRun() throws Exception {
    OmniActivity omniActivity = new OmniActivity();

    controller.run(omniActivity, new Bundle());

    verify(mockActionBarController, times(1)).run(omniActivity);
  }

  public void testStopWillCallActionBarControllerStop() throws Exception {
    controller.actionBarController = mockActionBarController;

    controller.stop();

    verify(mockActionBarController, times(1)).stop();
  }
}
