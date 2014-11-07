package com.omnipaste.omniapi;

import android.content.Context;

import com.omnipaste.omniapi.prefs.ApiAccessToken;
import com.omnipaste.omniapi.prefs.PrefsModule;
import com.omnipaste.omniapi.resource.v1.AuthorizationCodesTest;
import com.omnipaste.omniapi.resource.v1.ClippingsTest;
import com.omnipaste.omniapi.resource.v1.DevicesTest;
import com.omnipaste.omniapi.resource.v1.EventsApiTest;
import com.omnipaste.omnicommon.OmniCommonModule;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.prefs.AccessTokenPreference;

import dagger.Module;
import dagger.Provides;

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
        EventsApiTest.class,
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

  @Provides @ApiAccessToken
  public AccessTokenPreference provideApiAccessToken() {
    AccessTokenPreference mockAccessTokenPreference = mock(AccessTokenPreference.class);
    when(mockAccessTokenPreference.get()).thenReturn(new AccessTokenDto("access", "refresh"));

    return mockAccessTokenPreference;
  }
}
