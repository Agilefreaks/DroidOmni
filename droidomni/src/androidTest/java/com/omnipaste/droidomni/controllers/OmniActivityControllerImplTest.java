package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.activities.OmniActivity;
import com.omnipaste.droidomni.services.SessionService;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OmniActivityControllerImplTest extends TestCase {
  private OmniActivityControllerImpl controller;
  private ActionBarController mockActionBarController;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockActionBarController = mock(ActionBarController.class);
    controller = new OmniActivityControllerImpl(mock(SessionService.class));
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
