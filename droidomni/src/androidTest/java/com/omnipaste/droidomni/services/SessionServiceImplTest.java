package com.omnipaste.droidomni.services;

import android.test.InstrumentationTestCase;

import com.google.android.gms.internal.is;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.functions.Action1;
import rx.schedulers.TestScheduler;
import rx.subjects.TestSubject;

import static org.hamcrest.core.IsInstanceOf.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionServiceImplTest extends InstrumentationTestCase {
  private SessionServiceImpl subject;

  @Mock
  ConfigurationService configurationService;

  @Mock
  Configuration configuration;

  @Mock
  OmniApi omniApi;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);

    subject = new SessionServiceImpl(configurationService);
    subject.omniApi = omniApi;
    when(configurationService.getConfiguration()).thenReturn(configuration);
  }

  public void testLoginWillSetAccessTokenInConfigurationAndApi() throws Exception {
    AccessTokenDto accessTokenDto = new AccessTokenDto();

    subject.login(accessTokenDto);

    verify(configuration).setAccessToken(accessTokenDto);
    verify(omniApi).setAccessToken(accessTokenDto);
  }

  public void testLoginWhenNoAccessTokenWillReturnFalse() throws Exception {
    when(configuration.getAccessToken()).thenReturn(null);

    assertFalse(subject.login());
  }

  public void testLoginWhenAccessTokenReturnsTrue() throws Exception {
    when(configuration.getAccessToken()).thenReturn(new AccessTokenDto());

    assertTrue(subject.login());
  }
}
