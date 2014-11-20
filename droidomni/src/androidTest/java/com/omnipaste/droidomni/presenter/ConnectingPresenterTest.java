package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.interaction.GetAccounts;
import com.omnipaste.droidomni.service.OmniServiceConnection;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import junit.framework.TestCase;

import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConnectingPresenterTest extends TestCase {
  private ConnectingPresenter subject;
  private SessionService mockSessionService;
  private GetAccounts mockGetAccounts;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    Navigator mockNavigator = mock(Navigator.class);
    OmniServiceConnection mockOmniServiceConnection = mock(OmniServiceConnection.class);
    mockSessionService = mock(SessionService.class);
    mockGetAccounts = mock(GetAccounts.class);
    subject = new ConnectingPresenter(
        mockNavigator,
        mockSessionService,
        mockGetAccounts,
        mockOmniServiceConnection);
  }

  public void testInitializeWhenNotLoggedInWillTryEmails() {
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