package com.omnipaste.omniapi.service;

import com.omnipaste.omniapi.prefs.ApiAccessToken;
import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;

import java.net.HttpURLConnection;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

@Singleton
public class AuthorizationService {
  private final Token token;
  private final AccessTokenPreference accessToken;

  @Inject
  public AuthorizationService(Token token, @ApiAccessToken AccessTokenPreference accessToken) {
    this.token = token;
    this.accessToken = accessToken;
  }

  public AccessTokenDto getAccessTokenDto() {
    return accessToken.get();
  }

  public <T> Observable<T> authorize(final Observable<T> observable) {
    return Observable.create(new Observable.OnSubscribe<T>() {
      @Override
      public void call(final Subscriber<? super T> subscriber) {
        observable.subscribe(new Observer<T>() {
          @Override
          public void onCompleted() {
            subscriber.onCompleted();
          }

          @SuppressWarnings("unchecked")
          @Override
          public void onError(Throwable e) {
            if (isUnauthorized(e) && hasRefreshToken()) {
              Observable prefixObservable = Observable.concat(token.refresh(getAccessTokenDto().getRefreshToken()), observable)
                  .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                      subscriber.onError(throwable);
                    }
                  })
                  .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                      return !(o instanceof AccessTokenDto);
                    }
                  });
              prefixObservable.subscribe(subscriber);
            } else {
              subscriber.onError(e);
            }
          }

          @Override
          public void onNext(T args) {
            subscriber.onNext(args);
          }

          private boolean isUnauthorized(Throwable e) {
            return e instanceof RetrofitError &&
                ((RetrofitError) e).getResponse() != null &&
                ((RetrofitError) e).getResponse().getStatus() == HttpURLConnection.HTTP_UNAUTHORIZED;
          }
        });
      }
    });
  }

  private boolean hasRefreshToken() {
    String refreshToken = getAccessTokenDto().getRefreshToken();
    return refreshToken != null && !refreshToken.isEmpty();
  }
}
