package com.omnipaste.droidomni.presenter;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.service.DeviceService;
import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LauncherPresenterTest extends InstrumentationTestCase {
  private LauncherPresenter subject;
  private SessionService mockSessionService;
  private Navigator mockNavigator;
  private DeviceService mockDeviceService;

  @Override public void setUp() throws Exception {
    super.setUp();

    mockSessionService = mock(SessionService.class);
    mockNavigator = mock(Navigator.class);
    mockDeviceService = mock(DeviceService.class);
    subject = new LauncherPresenter(mock(Context.class), mockNavigator, mockSessionService, mockDeviceService);
  }

  public void testInitializeWhenNotLoggedItWillNavigateToLoginView() throws Exception {
    when(mockSessionService.isLogged()).thenReturn(false);

    subject.initialize();

    verify(mockNavigator).openLoginActivity();
  }

  public void testInitializeWhenLoggedInWillSetRegisteredDeviceOnSession() {
    RegisteredDeviceDto registeredDeviceDto = new RegisteredDeviceDto();

    when(mockSessionService.isLogged()).thenReturn(true);
    PublishSubject<RegisteredDeviceDto> publishSubject = PublishSubject.create();
    when(mockDeviceService.init()).thenReturn(publishSubject);

    LauncherPresenter.View mockView = mock(LauncherPresenter.View.class);
    subject.setView(mockView);

    subject.setScheduler(Schedulers.immediate());
    subject.setObserveOnScheduler(Schedulers.immediate());
    subject.initialize();
    publishSubject.onNext(registeredDeviceDto);

    verify(mockSessionService).setRegisteredDeviceDto(registeredDeviceDto);
    verify(mockNavigator).openOmniActivity();
    verify(mockView).finish();
  }
}