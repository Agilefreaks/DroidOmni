package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.prefs.ApiClientId;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.prefs.StringPreference;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

@Singleton
public class Token extends Resource<Token.TokenApi> {
  private final String clientId;

  @SuppressWarnings("UnusedDeclaration")
  private class AuthorizationRequest {
    private String clientId;
    private String grantType;
    private String code;
    private String refreshToken;

    private AuthorizationRequest(String clientId, String grantType) {
      this.clientId = clientId;
      this.grantType = grantType;
    }

    public String getClientId() {
      return clientId;
    }

    public String getGrantType() {
      return grantType;
    }

    public String getCode() {
      return code;
    }

    public AuthorizationRequest setCode(String code) {
      this.code = code;
      return this;
    }

    public String getRefreshToken() {
      return refreshToken;
    }

    public AuthorizationRequest setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
      return this;
    }
  }

  public interface TokenApi {
    @POST("/v1/oauth2/token.json")
    Observable<AccessTokenDto> create(@Body AuthorizationRequest authorizationRequest);
  }

  private static final String AUTHORIZATION_CODE = "authorization_code";
  private static final String REFRESH_TOKEN = "refresh_token";

  @Inject
  public Token(@ApiClientId StringPreference clientId, RestAdapter restAdapter) {
    super(restAdapter, TokenApi.class);
    this.clientId = clientId.get();
  }

  public Observable<AccessTokenDto> create(String code) {
    return api.create(new AuthorizationRequest(clientId, AUTHORIZATION_CODE).setCode(code)).delay(5, TimeUnit.SECONDS);
  }

  public Observable<AccessTokenDto> refresh(String refreshToken) {
    return api.create(new AuthorizationRequest(clientId, REFRESH_TOKEN).setRefreshToken(refreshToken));
  }
}
