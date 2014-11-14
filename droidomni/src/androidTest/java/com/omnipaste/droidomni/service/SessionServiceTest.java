package com.omnipaste.droidomni.service;

import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.service.SessionService;
import com.omnipaste.omniapi.resource.v1.AuthorizationCodes;
import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.AuthorizationCodeDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;
import com.omnipaste.omnicommon.prefs.StringPreference;
import com.omnipaste.omnicommon.rx.Schedulable;

import rx.Observable;
import rx.Observer;
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
  private AuthorizationCodes mockAuthorizationCodes;
  private AccessTokenPreference mockApiAccessToken;
  private StringPreference mockApiClientToken;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockToken = mock(Token.class);
    mockAuthorizationCodes = mock(AuthorizationCodes.class);
    mockApiAccessToken = mock(AccessTokenPreference.class);
    mockApiClientToken = mock(StringPreference.class);

    subject = new SessionService(mockToken, mockAuthorizationCodes, mockApiAccessToken, mockApiClientToken);
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
    Observer mockObserver = mock(Observer.class);
    Throwable error = mock(Throwable.class);

    PublishSubject<AccessTokenDto> tokenCreate = PublishSubject.create();
    when(mockToken.create("wrong code")).thenReturn(tokenCreate);

    setImmediateSchedulers(subject);
    subject.login("wrong code").subscribe(mockObserver);
    tokenCreate.onError(error);

    verify(mockObserver).onError(error);
  }

  @SuppressWarnings("unchecked")
  public void testLoginWillSetAccessTokenAndRefreshTokenOnSuccess() throws Exception {
    Observer mockObserver = mock(Observer.class);
    AccessTokenDto accessTokenDto = new AccessTokenDto("access", "refresh");

    PublishSubject<AccessTokenDto> tokenCreate = PublishSubject.create();
    when(mockToken.create("helpless")).thenReturn(tokenCreate);

    setImmediateSchedulers(subject);
    subject.login("helpless").subscribe(mockObserver);
    tokenCreate.onNext(accessTokenDto);

    verify(mockObserver).onNext(accessTokenDto);
    verify(mockApiAccessToken).set(accessTokenDto);
  }

  public void testLoginWithEmailsOnFailWillCallTokenCreate() {
    PublishSubject<AuthorizationCodeDto> authorizationCodes = PublishSubject.create();
    String[] emails = {"city@is.deing"};
    when(mockApiClientToken.get()).thenReturn("client token");
    when(mockAuthorizationCodes.get("client token", emails)).thenReturn(authorizationCodes);
    when(mockToken.create("code")).thenReturn(Observable.<AccessTokenDto>empty());

    setImmediateSchedulers(subject);
    subject.login(emails).subscribe();
    authorizationCodes.onNext(new AuthorizationCodeDto("code"));

    verify(mockToken).create("code");
  }

  private void setImmediateSchedulers(Schedulable schedulable) {
    schedulable.setScheduler(Schedulers.immediate());
    schedulable.setObserveOnScheduler(Schedulers.immediate());
  }
}
