package com.omnipaste.droidomni.presenter;

import com.google.android.gms.common.ConnectionResult;
import com.omnipaste.droidomni.service.PlayService;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LauncherPresenterTest {
  private LauncherPresenter subject;

  @Mock public SessionService mockSessionService;
  @Mock public Navigator mockNavigator;
  @Mock public PlayService mockPlayService;

  @Before
  public void context() {
    subject = new LauncherPresenter(mockPlayService, mockNavigator, mockSessionService);
  }

  @Test
  public void initializeWhenConnectedWillStartOmniActivity() throws Exception {
    when(mockPlayService.status()).thenReturn(ConnectionResult.SUCCESS);
    when(mockSessionService.isConnected()).thenReturn(true);

    subject.initialize();

    verify(mockNavigator).openOmniActivity();
  }

  @Test
  public void initializeWhenNotConnectedWillStartConnectingActivity() throws Exception {
    when(mockPlayService.status()).thenReturn(ConnectionResult.SUCCESS);
    when(mockSessionService.isConnected()).thenReturn(false);

    subject.initialize();

    verify(mockNavigator).openConnectingActivity();
  }
}