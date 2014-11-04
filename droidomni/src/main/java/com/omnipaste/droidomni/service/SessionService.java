package com.omnipaste.droidomni.service;

import com.omnipaste.omniapi.prefs.ApiAccessToken;
import com.omnipaste.omniapi.prefs.ApiRefreshToken;
import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.prefs.StringPreference;
import com.omnipaste.omnicommon.rx.Schedulable;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

@Singleton
public class SessionService extends Schedulable {
  private Token token;
  private StringPreference apiAccessToken;
  private StringPreference apiRefreshToken;
  private RegisteredDeviceDto registeredDeviceDto;

  @Inject
  public SessionService(Token token, @ApiAccessToken StringPreference apiAccessToken, @ApiRefreshToken StringPreference apiRefreshToken) {
    this.token = token;
    this.apiAccessToken = apiAccessToken;
    this.apiRefreshToken = apiRefreshToken;
  }

  public Boolean isLogged() {
    return apiAccessToken.get() != null;
  }

  public void login(String code, final Action1<AccessTokenDto> onSuccess, final Action1<Throwable> onError) {
    token.create(code)
        .subscribeOn(scheduler)
        .observeOn(observeOnScheduler)
        .subscribe(
            // OnNext
            new Action1<AccessTokenDto>() {
              @Override
              public void call(AccessTokenDto accessTokenDto) {
                apiAccessToken.set(accessTokenDto.getAccessToken());
                apiRefreshToken.set(accessTokenDto.getRefreshToken());
                onSuccess.call(accessTokenDto);
              }
            },
            onError
        );
  }

  public void logout() {
    apiAccessToken.set(null);
    apiRefreshToken.set(null);
  }

  public RegisteredDeviceDto getRegisteredDeviceDto() {
    return registeredDeviceDto;
  }

  public void setRegisteredDeviceDto(RegisteredDeviceDto registeredDeviceDto) {
    this.registeredDeviceDto = registeredDeviceDto;
  }
}