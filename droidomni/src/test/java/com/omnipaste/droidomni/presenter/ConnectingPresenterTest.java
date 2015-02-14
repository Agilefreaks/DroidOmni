package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.interaction.CreateDevice;
import com.omnipaste.droidomni.interaction.GetAccounts;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omniapi.resource.v1.user.Devices;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.prefs.BooleanPreference;
import com.omnipaste.omnicommon.prefs.StringPreference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectingPresenterTest {
  private ConnectingPresenter subject;

  @Mock public SessionService mockSessionService;
  @Mock public GetAccounts mockGetAccounts;
  @Mock public Navigator mockNavigator;
  @Mock public OmniServiceConnection mockOmniServiceConnection;

  @Before
  public void context() {
    subject = new ConnectingPresenter(
      mockNavigator,
      mockSessionService,
      mockGetAccounts,
      mockOmniServiceConnection,
      mock(Devices.class),
      mock(CreateDevice.class),
      mock(StringPreference.class),
      "device identifier",
      mock(BooleanPreference.class),
      mock(BooleanPreference.class),
      mock(BooleanPreference.class));
  }

  @Test
  public void initializeWhenNotLoggedInWillTryEmails() {
    String[] emails = new String[]{"matei@calin.com", "tudor@email.com"};
    PublishSubject<AccessTokenDto> publishSubject = PublishSubject.create();
    when(mockSessionService.isLogged()).thenReturn(false);
    when(mockGetAccounts.fromGoogle()).thenReturn(emails);
    when(mockSessionService.login(emails)).thenReturn(publishSubject);

    subject.initialize();
    publishSubject.onError(new Exception());

    verify(mockSessionService).login(emails);
  }
}