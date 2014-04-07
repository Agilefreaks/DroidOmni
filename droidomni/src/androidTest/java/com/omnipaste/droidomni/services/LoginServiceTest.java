package com.omnipaste.droidomni.services;

import android.test.InstrumentationTestCase;

import com.omnipaste.omniapi.IOmniApi;
import com.omnipaste.omniapi.resources.v1.Token;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServiceTest extends InstrumentationTestCase {
  private LoginService loginService;

  @Mock
  IOmniApi mockOmniApi;

  @Mock
  ConfigurationService configurationService;

  @Override
  @SuppressWarnings("ConstantConditions")
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);

    loginService = new LoginService();
    loginService.omniApi = mockOmniApi;
    loginService.configurationService = configurationService;
  }

  public void testLoginWillCallTokenCreate() throws Exception {
    Token token = mock(Token.class);
    Configuration configuration = mock(Configuration.class);
    when(mockOmniApi.token()).thenReturn(token);
    when(configurationService.getConfiguration()).thenReturn(configuration);
    when(configuration.getApiClientId()).thenReturn("42");

    loginService.login("43");

    verify(token).create("42", "43");
  }
}
