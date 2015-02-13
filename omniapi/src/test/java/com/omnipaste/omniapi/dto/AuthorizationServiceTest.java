package com.omnipaste.omniapi.dto;

import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;

import org.apache.http.HttpStatus;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import rx.Observer;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorizationServiceTest {
  private AuthorizationService subject;
  private Observer<String> mockObserver;
  private AccessTokenPreference mockAccessToken;

  @SuppressWarnings("unchecked")
  @Before
  public void context() {
    mockObserver = (Observer<String>) mock(Observer.class);
    Token mockToken = mock(Token.class);
    mockAccessToken = mock(AccessTokenPreference.class);
    subject = new AuthorizationService(mockToken, mockAccessToken);
  }

  @Test
  public void authorizeOnCompleteWillCallOnCompleteOnSubscriber() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    subject.authorize(observable).subscribe(mockObserver);

    observable.onCompleted();

    verify(mockObserver, times(1)).onCompleted();
  }

  @Test
  public void onNextWillCallOnNextOnSubscriber() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    subject.authorize(observable).subscribe(mockObserver);

    observable.onNext("Alanis Morissette");

    verify(mockObserver, times(1)).onNext("Alanis Morissette");
  }

  @Test
  public void onErrorWhenErrorIsNotRetrofitErrorItWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    subject.authorize(observable).subscribe(mockObserver);
    Exception e = new Exception();

    observable.onError(e);

    verify(mockObserver, times(1)).onError(e);
  }

  @Test
  public void onErrorWhenErrorIs401AndRefreshTokenIsNotNullWillNotCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    when(mockAccessToken.get()).thenReturn(new AccessTokenDto("token", "refresh"));
    subject.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.httpError("", new Response("", HttpStatus.SC_UNAUTHORIZED, "", new ArrayList<Header>(), null), null, null);

    observable.onError(retrofitError);

    verify(mockObserver, never()).onError(retrofitError);
  }

  @Test
  public void onErrorWhenErrorIs401AndRefreshTokenIsNullWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    when(mockAccessToken.get()).thenReturn(new AccessTokenDto("token", null));
    subject.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.httpError("", new Response("", HttpStatus.SC_UNAUTHORIZED, "", new ArrayList<Header>(), null), null, null);

    observable.onError(retrofitError);

    verify(mockObserver).onError(retrofitError);
  }

  @Test
  public void onErrorWhenErrorIs401AndRefreshTokenIsEmptyWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    when(mockAccessToken.get()).thenReturn(new AccessTokenDto("token", ""));
    subject.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.httpError("", new Response("", HttpStatus.SC_UNAUTHORIZED, "", new ArrayList<Header>(), null), null, null);

    observable.onError(retrofitError);

    verify(mockObserver).onError(retrofitError);
  }

  @Test
  public void onErrorWhenErrorRetrofitErrorWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    subject.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.unexpectedError("", new HttpHostConnectException(null, null));

    observable.onError(retrofitError);

    verify(mockObserver, times(1)).onError(retrofitError);
  }
}