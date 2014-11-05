package com.omnipaste.droidomni.presenter;

import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest extends InstrumentationTestCase {
  private LoginPresenter subject;
  private SessionService mockSessionService;
  private Navigator mockNavigator;

  @Override public void setUp() throws Exception {
    super.setUp();

    mockSessionService = mock(SessionService.class);
    mockNavigator = mock(Navigator.class);
    subject = new LoginPresenter(mockSessionService, mockNavigator);
  }

  public void testLoginWhenLoginSuccessWillNavigateToLauncherActivity() throws Exception {
    PublishSubject<AccessTokenDto> publishSubject = PublishSubject.create();
    when(mockSessionService.login("code")).thenReturn(publishSubject);

    subject.setScheduler(Schedulers.immediate());
    subject.setObserveOnScheduler(Schedulers.immediate());
    subject.login("code");
    publishSubject.onNext(new AccessTokenDto());

    verify(mockNavigator).openLauncherActivity();
  }

  public void testLoginWhenLoginFailsWillCallLoginFailedOnView() throws Exception {
    PublishSubject<AccessTokenDto> publishSubject = PublishSubject.create();
    when(mockSessionService.login("code")).thenReturn(publishSubject);
    LoginPresenter.View mockView = mock(LoginPresenter.View.class);

    subject.attachView(mockView);
    subject.setScheduler(Schedulers.immediate());
    subject.setObserveOnScheduler(Schedulers.immediate());
    subject.login("code");
    publishSubject.onError(new Exception());

    verify(mockView).loginFailed();
  }
}