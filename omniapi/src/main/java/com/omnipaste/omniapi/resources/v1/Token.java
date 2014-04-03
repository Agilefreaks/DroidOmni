package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public class Token extends Resource {
  private class AuthorizationRequest {
    private String clientId;
    private String grantType;
    private String code;

    private AuthorizationRequest(String clientId, String grantType, String code) {
      this.clientId = clientId;
      this.grantType = grantType;
      this.code = code;
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
  }

  private interface TokenApi {
    @POST("/v1/oauth2/token.json")
    Observable<AccessTokenDto> create(@Body AuthorizationRequest authorizationRequest);
  }

  private static final String AUTHORIZATION_CODE = "authorization_code";

  private final TokenApi tokenApi;

  public Token(String baseUrl) {
    super(baseUrl);

    tokenApi = restAdapter.create(TokenApi.class);
  }

  public Observable<AccessTokenDto> create(String clientId, String code) {
    return tokenApi.create(new AuthorizationRequest(clientId, AUTHORIZATION_CODE, code));
  }
}
