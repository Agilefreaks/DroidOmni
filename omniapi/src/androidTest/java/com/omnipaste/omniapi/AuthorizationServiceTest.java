package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;

import org.apache.http.HttpStatus;
import org.apache.http.conn.HttpHostConnectException;

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

public class AuthorizationServiceTest extends InstrumentationTestCaseBase {
  private AuthorizationService subject;
  private Observer<String> mockObserver;
  private AccessTokenPreference mockAccessToken;

  @SuppressWarnings("unchecked")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    mockObserver = (Observer<String>) mock(Observer.class);
    Token mockToken = mock(Token.class);
    mockAccessToken = mock(AccessTokenPreference.class);
    subject = new AuthorizationService(mockToken, mockAccessToken);
  }

  public void testAuthorizeOnCompleteWillCallOnCompleteOnSubscriber() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    subject.authorize(observable).subscribe(mockObserver);

    observable.onCompleted();

    verify(mockObserver, times(1)).onCompleted();
  }

  public void testOnNextWillCallOnNextOnSubscriber() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    subject.authorize(observable).subscribe(mockObserver);

    observable.onNext("Alanis Morissette");

    verify(mockObserver, times(1)).onNext("Alanis Morissette");
  }

  public void testOnErrorWhenErrorIsNotRetrofitErrorItWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    subject.authorize(observable).subscribe(mockObserver);
    Exception e = new Exception();

    observable.onError(e);

    verify(mockObserver, times(1)).onError(e);
  }

  public void testOnErrorWhenErrorIs401AndRefreshTokenIsNotNullWillNotCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    when(mockAccessToken.get()).thenReturn(new AccessTokenDto("token", "refresh"));
    subject.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.httpError("", new Response("", HttpStatus.SC_UNAUTHORIZED, "", new ArrayList<Header>(), null), null, null);

    observable.onError(retrofitError);

    verify(mockObserver, never()).onError(retrofitError);
  }

  public void testOnErrorWhenErrorIs401AndRefreshTokenIsNullWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    when(mockAccessToken.get()).thenReturn(new AccessTokenDto("token", null));
    subject.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.httpError("", new Response("", HttpStatus.SC_UNAUTHORIZED, "", new ArrayList<Header>(), null), null, null);

    observable.onError(retrofitError);

    verify(mockObserver).onError(retrofitError);
  }

  public void testOnErrorWhenErrorIs401AndRefreshTokenIsEmptyWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    when(mockAccessToken.get()).thenReturn(new AccessTokenDto("token", ""));
    subject.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.httpError("", new Response("", HttpStatus.SC_UNAUTHORIZED, "", new ArrayList<Header>(), null), null, null);

    observable.onError(retrofitError);

    verify(mockObserver).onError(retrofitError);
  }

  public void testOnErrorWhenErrorRetrofitErrorWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    subject.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.unexpectedError("", new HttpHostConnectException(null, null));

    observable.onError(retrofitError);

    verify(mockObserver, times(1)).onError(retrofitError);
  }
}