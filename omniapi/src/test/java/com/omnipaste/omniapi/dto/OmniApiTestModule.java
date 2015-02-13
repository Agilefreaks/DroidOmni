package com.omnipaste.omniapi.dto;

import android.content.Context;

import com.omnipaste.omniapi.OmniApiModule;
import com.omnipaste.omniapi.dto.resource.v1.AuthorizationCodesTest;
import com.omnipaste.omniapi.dto.resource.v1.ClippingsTest;
import com.omnipaste.omniapi.dto.resource.v1.user.DevicesTest;
import com.omnipaste.omniapi.prefs.ApiAccessToken;
import com.omnipaste.omniapi.prefs.ApiClientId;
import com.omnipaste.omniapi.prefs.ApiUrl;
import com.omnipaste.omniapi.prefs.PrefsModule;
import com.omnipaste.omnicommon.OmniCommonModule;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;
import com.omnipaste.omnicommon.prefs.StringPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Module(
    includes = {
        OmniCommonModule.class,
        OmniApiModule.class,
        PrefsModule.class
    },
    injects = {
        ClippingsTest.class,
        DevicesTest.class,
        AuthorizationServiceTest.class,
        AuthorizationCodesTest.class
    },
    library = true,
    overrides = true,
    complete = false
)
public class OmniApiTestModule {
  private final Context context;

  public OmniApiTestModule(Context context) {
    this.context = context;
  }

  @Provides
  public Context provideContext() {
    return context;
  }

  @Provides @Singleton
  public Endpoint provideEndpoint(@ApiUrl StringPreference apiUrl) {
    return Endpoints.newFixedEndpoint("https://testapi.omnipasteapp.com");
  }

  @Provides @ApiAccessToken
  public AccessTokenPreference provideApiAccessToken() {
    AccessTokenPreference mockAccessTokenPreference = mock(AccessTokenPreference.class);
    when(mockAccessTokenPreference.get()).thenReturn(new AccessTokenDto("access", "refresh"));

    return mockAccessTokenPreference;
  }

  @Provides @ApiClientId
  public StringPreference provideApiClientId() {
    StringPreference mockApiClientId = mock(StringPreference.class);
    when(mockApiClientId.get()).thenReturn("api client id");

    return mockApiClientId;
  }
}
