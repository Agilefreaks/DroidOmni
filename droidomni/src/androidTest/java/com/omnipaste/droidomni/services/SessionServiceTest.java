package com.omnipaste.droidomni.services;

import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;
import com.omnipaste.omnicommon.rx.Schedulable;

import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionServiceTest extends InstrumentationTestCase {
  private SessionService subject;
  private Token mockToken;
  private AccessTokenPreference mockApiAccessToken;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockToken = mock(Token.class);
    mockApiAccessToken = mock(AccessTokenPreference.class);

    subject = new SessionService(mockToken, mockApiAccessToken);
  }

  public void testIsLoggedWillReturnFalseWithNoAccessToken() throws Exception {
    when(mockApiAccessToken.get()).thenReturn(null);

    assertThat(subject.isLogged(), is(false));
  }

  public void testIsLoggedWillReturnTrueWhenAccessTokenNotNull() throws Exception {
    when(mockApiAccessToken.get()).thenReturn(new AccessTokenDto("one night", "in palermo"));

    assertThat(subject.isLogged(), is(true));
  }

  public void testLogoutWillClearAccessAndRefreshToken() throws Exception {
    subject.logout();

    verify(mockApiAccessToken).delete();
  }

  @SuppressWarnings("unchecked")
  public void testLoginWillCallOnErrorOnFailed() throws Exception {
    Action1<Throwable> onError = (Action1<Throwable>) mock(Action1.class);
    Throwable error = mock(Throwable.class);

    PublishSubject<AccessTokenDto> tokenCreate = PublishSubject.create();
    when(mockToken.create("wrong code")).thenReturn(tokenCreate);

    setImmediateSchedulers(subject);
    subject.login("wrong code", null, onError);
    tokenCreate.onError(error);

    verify(onError).call(error);
  }

  @SuppressWarnings("unchecked")
  public void testWillSetAccessTokenAndRefreshTokenOnSuccess() throws Exception {
    Action1<AccessTokenDto> onSuccess = (Action1<AccessTokenDto>) mock(Action1.class);
    Action1<Throwable> onError = (Action1<Throwable>) mock(Action1.class);
    AccessTokenDto accessTokenDto = new AccessTokenDto("access", "refresh");

    PublishSubject<AccessTokenDto> tokenCreate = PublishSubject.create();
    when(mockToken.create("helpless")).thenReturn(tokenCreate);

    setImmediateSchedulers(subject);
    subject.login("helpless", onSuccess, onError);
    tokenCreate.onNext(accessTokenDto);

    verify(onSuccess).call(accessTokenDto);
    verify(mockApiAccessToken).set(accessTokenDto);
  }

  private void setImmediateSchedulers(Schedulable schedulable) {
    schedulable.setScheduler(Schedulers.immediate());
    schedulable.setObserveOnScheduler(Schedulers.immediate());
  }
}
