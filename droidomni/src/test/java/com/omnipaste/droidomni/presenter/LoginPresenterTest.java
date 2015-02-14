package com.omnipaste.droidomni.presenter;

import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.droidomni.ui.Navigator;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
  private LoginPresenter subject;

  @Mock public SessionService mockSessionService;
  @Mock public Navigator mockNavigator;

  @Before
  public void context() {
    subject = new LoginPresenter(mockSessionService, mockNavigator);
  }

  @Test
  public void lLoginWhenLoginSuccessWillNavigateToLauncherActivity() throws Exception {
    PublishSubject<AccessTokenDto> publishSubject = PublishSubject.create();
    when(mockSessionService.login("code")).thenReturn(publishSubject);

    subject.setScheduler(Schedulers.immediate());
    subject.setObserveOnScheduler(Schedulers.immediate());
    subject.login("code");
    publishSubject.onNext(new AccessTokenDto());

    verify(mockNavigator).openLauncherActivity();
  }

  @Test
  public void loginWhenLoginFailsWillCallLoginFailedOnView() throws Exception {
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