package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public class Token extends Resource {
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

  private interface TokenApi {
    @POST("/v1/oauth2/token.json")
    Observable<AccessTokenDto> create(@Body AuthorizationRequest authorizationRequest);
  }

  private static final String AUTHORIZATION_CODE = "authorization_code";
  private static final String REFRESH_TOKEN = "refresh_token";

  private final TokenApi tokenApi;

  public Token(String clientId, String baseUrl) {
    super(baseUrl);

    this.clientId = clientId;
    tokenApi = restAdapter.create(TokenApi.class);
  }

  public Observable<AccessTokenDto> create(String code) {
    return tokenApi.create(new AuthorizationRequest(clientId, AUTHORIZATION_CODE).setCode(code));
  }

  public Observable<AccessTokenDto> refresh(String refreshToken) {
    return tokenApi.create(new AuthorizationRequest(clientId, REFRESH_TOKEN).setRefreshToken(refreshToken));
  }
}
