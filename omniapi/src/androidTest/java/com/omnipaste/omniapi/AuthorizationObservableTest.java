package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import junit.framework.TestCase;

import org.apache.http.HttpStatus;
import org.apache.http.conn.HttpHostConnectException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import rx.Observer;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuthorizationObservableTest extends TestCase {
  private AuthorizationObservable authorizationObservable;

  @Mock
  private Observer<String> mockObserver;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    MockitoAnnotations.initMocks(this);

    authorizationObservable = new AuthorizationObservable(new Token("", "http://some.end.point"), new AccessTokenDto());
  }

  public void testAuthorizeOnCompleteWillCallOnCompleteOnSubscriber() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    authorizationObservable.authorize(observable).subscribe(mockObserver);

    observable.onCompleted();

    verify(mockObserver, times(1)).onCompleted();
  }

  public void testOnNextWillCallOnNextOnSubscriber() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    authorizationObservable.authorize(observable).subscribe(mockObserver);

    observable.onNext("Alanis Morissette");

    verify(mockObserver, times(1)).onNext("Alanis Morissette");
  }

  public void testOnErrorWhenErrorIsNotRetrofitErrorItWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    authorizationObservable.authorize(observable).subscribe(mockObserver);
    Exception e = new Exception();

    observable.onError(e);

    verify(mockObserver, times(1)).onError(e);
  }

  public void testOnErrorWhenErrorIs401WillNotCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    authorizationObservable.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.httpError("", new Response("", HttpStatus.SC_UNAUTHORIZED, "", new ArrayList<Header>(), null), null, null);

    observable.onError(retrofitError);

    verify(mockObserver, never()).onError(retrofitError);
  }

  public void testOnErrorWhenErrorRetrofitErrorWillCallOnError() throws Exception {
    PublishSubject<String> observable = PublishSubject.create();
    authorizationObservable.authorize(observable).subscribe(mockObserver);
    RetrofitError retrofitError = RetrofitError.unexpectedError("", new HttpHostConnectException(null, null));

    observable.onError(retrofitError);

    verify(mockObserver, times(1)).onError(retrofitError);
  }
}