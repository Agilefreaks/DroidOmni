package com.omnipaste.droidomni.service;

import com.omnipaste.omniapi.prefs.ApiAccessToken;
import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;
import com.omnipaste.omnicommon.rx.Schedulable;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;

@Singleton
public class SessionService extends Schedulable {
  private Token token;
  private AccessTokenPreference apiAccessToken;
  private RegisteredDeviceDto registeredDeviceDto;

  @Inject
  public SessionService(Token token, @ApiAccessToken AccessTokenPreference apiAccessToken) {
    this.token = token;
    this.apiAccessToken = apiAccessToken;
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
