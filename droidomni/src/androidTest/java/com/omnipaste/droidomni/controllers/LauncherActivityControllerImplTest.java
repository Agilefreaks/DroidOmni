package com.omnipaste.droidomni.controllers;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.activities.LauncherActivity;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.services.FragmentService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LauncherActivityControllerImplTest extends InstrumentationTestCase {
  private LauncherActivity launcherActivity = new LauncherActivity();

  @Mock
  public SessionService mockSessionService;

  @Mock
  public FragmentService mockFragmentService;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    MockitoAnnotations.initMocks(this);

    LauncherActivityControllerImpl mainActivityController = new LauncherActivityControllerImpl();
    mainActivityController.sessionService = mockSessionService;
    mainActivityController.fragmentService = mockFragmentService;
    mainActivityController.run(launcherActivity, new Bundle());
  }
}