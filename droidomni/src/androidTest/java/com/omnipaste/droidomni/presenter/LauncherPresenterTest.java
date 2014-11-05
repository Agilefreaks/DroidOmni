package com.omnipaste.droidomni.presenter;

import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LauncherPresenterTest extends InstrumentationTestCase {
  private LauncherPresenter subject;
  private SessionService mockSessionService;
  private Navigator mockNavigator;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockSessionService = mock(SessionService.class);
    mockNavigator = mock(Navigator.class);
    subject = new LauncherPresenter(mockNavigator, mockSessionService);
  }

  public void testInitializeWhenConnectedWillStartOmniActivity() throws Exception {
    when(mockSessionService.isConnected()).thenReturn(true);

    subject.initialize();

    verify(mockNavigator).openOmniActivity();
  }

  public void testInitializeWhenNotConnectedWillStartConnectingActivity() throws Exception {
    when(mockSessionService.isConnected()).thenReturn(false);

    subject.initialize();

    verify(mockNavigator).openConnectingActivity();
  }
}