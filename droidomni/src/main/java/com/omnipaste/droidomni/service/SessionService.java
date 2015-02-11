package com.omnipaste.droidomni.service;

import com.omnipaste.droidomni.prefs.DeviceId;
import com.omnipaste.omniapi.prefs.ApiAccessToken;
import com.omnipaste.omniapi.prefs.ApiClientToken;
import com.omnipaste.omniapi.resource.v1.AuthorizationCodes;
import com.omnipaste.omniapi.resource.v1.Token;
import com.omnipaste.omniapi.resource.v1.user.Devices;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.AuthorizationCodeDto;
import com.omnipaste.omnicommon.dto.DeviceDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;
import com.omnipaste.omnicommon.prefs.StringPreference;
import com.omnipaste.omnicommon.rx.Schedulable;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.Unit;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

@Singleton
public class SessionService extends Schedulable {
  private final Token token;
  private final AuthorizationCodes authorizationCodes;
  private final Devices devices;
  private final StringPreference deviceId;
  private final AccessTokenPreference apiAccessToken;
  private final StringPreference apiClientToken;
  private DeviceDto deviceDto;

  @Inject
  public SessionService(Token token,
                        AuthorizationCodes authorizationCodes,
                        Devices devices,
                        @DeviceId StringPreference deviceId,
                        @ApiAccessToken AccessTokenPreference apiAccessToken,
                        @ApiClientToken StringPreference apiClientToken) {
    this.token = token;
    this.authorizationCodes = authorizationCodes;
    this.devices = devices;
    this.deviceId = deviceId;
    this.apiAccessToken = apiAccessToken;
    this.apiClientToken = apiClientToken;
  }

  public Boolean isLogged() {
    return apiAccessToken.get() != null;
  }

  public Boolean isConnected() {
    return isLogged() && getDeviceDto() != null;
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

  public Observable<Unit> logout() {
    final PublishSubject<Unit> publishSubject = PublishSubject.create();

    devices.delete(deviceId.get())
      .subscribe(new Action1<Boolean>() {
        @Override
        public void call(Boolean deleted) {
          deviceId.delete();
          apiAccessToken.delete();
          publishSubject.onNext(null);
        }
    });

    return publishSubject;
  }

  public DeviceDto getDeviceDto() {
    return deviceDto;
  }

  public void setDeviceDto(DeviceDto deviceDto) {
    this.deviceDto = deviceDto;
  }
}
