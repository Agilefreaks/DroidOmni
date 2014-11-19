package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.interaction.GetAccounts;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import junit.framework.TestCase;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConnectingPresenterTest extends TestCase {
  private ConnectingPresenter subject;
  private SessionService mockSessionService;
  private Navigator mockNavigator;
  private GetAccounts mockGetAccounts;

//  @Override
//  public void setUp() throws Exception {
//    super.setUp();
//
//    mockSessionService = mock(SessionService.class);
//    mockNavigator = mock(Navigator.class);
//    mockGetAccounts = mock(GetAccounts.class);
//    subject = new ConnectingPresenter(
//        mockNavigator,
//        mockSessionService,
//        mockGetAccounts);
//  }
//
//  public void testInitializeWhenLoggedInWillSetRegisteredDeviceOnSession() {
//    RegisteredDeviceDto registeredDeviceDto = new RegisteredDeviceDto();
//
//    when(mockSessionService.isLogged()).thenReturn(true);
//    PublishSubject<RegisteredDeviceDto> publishSubject = PublishSubject.create();
//    when(mockDeviceService.activate()).thenReturn(publishSubject);
//
//    ConnectingPresenter.View mockView = mock(ConnectingPresenter.View.class);
//    subject.attachView(mockView);
//
//    subject.setScheduler(Schedulers.immediate());
//    subject.setObserveOnScheduler(Schedulers.immediate());
//    subject.initialize();
//    publishSubject.onNext(registeredDeviceDto);
//
//    verify(mockSessionService).setRegisteredDeviceDto(registeredDeviceDto);
//    verify(mockNavigator).openOmniActivity();
//    verify(mockView).finish();
//  }
//
//  public void testInitializeWhenNotLoggedInWillTryEmails() {
//    String[] emails = new String[] { "matei@calin.com", "tudor@email.com" };
//    PublishSubject<AccessTokenDto> publishSubject = PublishSubject.create();
//    when(mockSessionService.isLogged()).thenReturn(false);
//    when(mockGetAccounts.fromGoogle()).thenReturn(emails);
//    when(mockSessionService.login(emails)).thenReturn(publishSubject);
//
//    subject.initialize();
//    publishSubject.onError(new Exception());
//
//    verify(mockSessionService).login(emails);
//  }
}