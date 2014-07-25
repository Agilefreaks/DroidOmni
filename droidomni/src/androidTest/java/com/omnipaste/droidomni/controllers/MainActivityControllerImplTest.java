package com.omnipaste.droidomni.controllers;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.services.FragmentService;
import com.omnipaste.droidomni.services.SessionService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainActivityControllerImplTest extends InstrumentationTestCase {
  private MainActivity mainActivity = new MainActivity();

  @Mock
  public SessionService mockSessionService;

  @Mock
  public FragmentService mockFragmentService;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    MockitoAnnotations.initMocks(this);

    MainActivityControllerImpl mainActivityController = new MainActivityControllerImpl();
    mainActivityController.sessionService = mockSessionService;
    mainActivityController.fragmentService = mockFragmentService;
    mainActivityController.run(mainActivity, new Bundle());
  }
}