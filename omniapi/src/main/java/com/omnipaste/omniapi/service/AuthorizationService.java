package com.omnipaste.omniapi.service;

import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import org.apache.http.HttpStatus;

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
  private final AccessTokenDto accessTokenDto;

  @Inject
  public AuthorizationService(Token token, AccessTokenDto accessTokenDto) {
    this.token = token;
    this.accessTokenDto = accessTokenDto;
  }

  public AccessTokenDto getAccessTokenDto() {
    return accessTokenDto;
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
              Observable prefixObservable = Observable.concat(token.refresh(accessTokenDto.getRefreshToken()), observable)
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
                ((RetrofitError) e).getResponse().getStatus() == HttpStatus.SC_UNAUTHORIZED;
          }
        });
      }
    });
  }

  private boolean hasRefreshToken() {
    String refreshToken = accessTokenDto.getRefreshToken();
    return refreshToken != null && !refreshToken.isEmpty();
  }
}