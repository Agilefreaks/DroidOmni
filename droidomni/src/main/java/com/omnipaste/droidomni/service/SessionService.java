package com.omnipaste.droidomni.service;

import com.omnipaste.omniapi.prefs.ApiAccessToken;
import com.omnipaste.omniapi.prefs.ApiClientToken;
import com.omnipaste.omniapi.resource.v1.AuthorizationCodes;
import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.AuthorizationCodeDto;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;
import com.omnipaste.omnicommon.prefs.StringPreference;
import com.omnipaste.omnicommon.rx.Schedulable;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

@Singleton
public class SessionService extends Schedulable {
  private Token token;
  private AuthorizationCodes authorizationCodes;
  private AccessTokenPreference apiAccessToken;
  private StringPreference apiClientToken;
  private RegisteredDeviceDto registeredDeviceDto;

  @Inject
  public SessionService(Token token,
                        AuthorizationCodes authorizationCodes,
                        @ApiAccessToken AccessTokenPreference apiAccessToken,
                        @ApiClientToken StringPreference apiClientToken) {
    this.token = token;
    this.authorizationCodes = authorizationCodes;
    this.apiAccessToken = apiAccessToken;
    this.apiClientToken = apiClientToken;
  }

  public Boolean isLogged() {
    return apiAccessToken.get() != null;
  }

  public Observable<AccessTokenDto> login(String code) {
    return token.create(code).doOnNext(new Action1<AccessTokenDto>() {
      @Override public void call(AccessTokenDto accessTokenDto) {
        apiAccessToken.set(accessTokenDto);
      }
    });
  }

  public Observable<AccessTokenDto> login(String[] emails) {
    return authorizationCodes.get(apiClientToken.get(), emails)
        .flatMap(new Func1<AuthorizationCodeDto, Observable<AccessTokenDto>>() {
          @Override
          public Observable<AccessTokenDto> call(AuthorizationCodeDto authorizationCodeDto) {
            return login(authorizationCodeDto.getCode());
          }
        });
  }

  public void logout() {
    apiAccessToken.delete();
  }

  public RegisteredDeviceDto getRegisteredDeviceDto() {
    return registeredDeviceDto;
  }

  public void setRegisteredDeviceDto(RegisteredDeviceDto registeredDeviceDto) {
    this.registeredDeviceDto = registeredDeviceDto;
  }
}
