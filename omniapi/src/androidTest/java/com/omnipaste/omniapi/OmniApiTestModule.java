package com.omnipaste.omniapi;

import android.content.Context;

import com.omnipaste.omniapi.prefs.PrefsModule;
import com.omnipaste.omniapi.resource.v1.ClippingsTest;
import com.omnipaste.omniapi.resource.v1.DevicesTest;
import com.omnipaste.omniapi.resource.v1.EventsApiTest;
import com.omnipaste.omnicommon.OmniCommonModule;

import dagger.Module;
import dagger.Provides;

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
        AuthorizationServiceTest.class
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
}
