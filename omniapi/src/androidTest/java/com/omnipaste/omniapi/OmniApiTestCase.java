package com.omnipaste.omniapi;

import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public abstract class OmniApiTestCase extends TestCase {
  protected AccessTokenDto accessTokenDto = new AccessTokenDto("access");
  protected OmniApi omniApiV1;
  protected Configuration configuration;

  @Mock
  public ConfigurationService configurationService;

  public void setUp() throws Exception {
    super.setUp();

    MockitoAnnotations.initMocks(this);

    this.configuration = new Configuration();
    configuration.setApiClientId("client id");
    configuration.setApiUrl("http://test.omnipasteapp.com/api");
    configuration.setAccessToken(accessTokenDto);
    when(configurationService.getConfiguration()).thenReturn(configuration);

    this.omniApiV1 = new OmniApiV1(configurationService);
  }
}
