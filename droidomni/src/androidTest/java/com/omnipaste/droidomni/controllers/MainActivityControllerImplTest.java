package com.omnipaste.droidomni.controllers;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity;
import com.omnipaste.droidomni.events.DeviceInitErrorEvent;
import com.omnipaste.droidomni.fragments.DeviceInitErrorFragment_;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.services.FragmentService;
import com.omnipaste.droidomni.services.SessionService;

import org.apache.http.HttpStatus;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MainActivityControllerImplTest extends InstrumentationTestCase {
  private MainActivityControllerImpl mainActivityController;
  private MainActivity mainActivity = new MainActivity();


  @Mock
  public SessionService mockSessionService;

  @Mock
  public FragmentService mockFragmentService;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    MockitoAnnotations.initMocks(this);

    mainActivityController = new MainActivityControllerImpl(mockSessionService);
    mainActivityController.fragmentService = mockFragmentService;
    mainActivityController.run(mainActivity, new Bundle());
  }

  public void testOnHandleDeviceInitErrorEventWhenExceptionIsRefit400WillLogout() throws Exception {
    mainActivityController.onEventMainThread(
        new DeviceInitErrorEvent(
            RetrofitError.httpError("", new Response("", HttpStatus.SC_BAD_REQUEST, "", new ArrayList<Header>(), null), null, null))
    );

    verify(mockSessionService, times(1)).logout();
    verify(mockFragmentService, times(1)).setFragment(eq(mainActivity), eq(R.id.main_container), isA(LoginFragment_.class));
  }

  public void testOnHandleDeviceInitErrorEventWHenExceptionWillSetFragment() throws Exception {
    mainActivityController.onEventMainThread(new DeviceInitErrorEvent(new Exception()));

    verify(mockFragmentService, times(1)).setFragment(eq(mainActivity), eq(R.id.main_container), isA(DeviceInitErrorFragment_.class));
  }
}