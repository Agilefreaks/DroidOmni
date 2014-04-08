package com.omnipaste.droidomni.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.InstrumentationTestCase;

import com.google.gson.Gson;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocalConfigurationServiceTest extends InstrumentationTestCase {
  private LocalConfigurationService subject;

  @Mock
  Context context;

  @Mock
  SharedPreferences sharedPreferences;

  @SuppressWarnings("ConstantConditions")
  @Override
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());

    MockitoAnnotations.initMocks(this);

    when(context.getSharedPreferences("com.omnipaste.droidomni", Context.MODE_PRIVATE)).thenReturn(sharedPreferences);
    Mockito.doNothing().when(sharedPreferences).registerOnSharedPreferenceChangeListener(subject);
    subject = new LocalConfigurationService(context);
  }

  public void testPopulateConfigurationWhenAccessTokenIsEmpty() throws Exception {
    when(sharedPreferences.getString(LocalConfigurationService.ACCESS_TOKEN, "")).thenReturn("");

    assertThat(subject.getConfiguration().getAccessToken(), is(nullValue()));
  }

  public void testPopulateConfigurationWhenAccessTokenWhenAccessTokenIsNotEmpty() throws Exception {
    when(sharedPreferences.getString(LocalConfigurationService.ACCESS_TOKEN, ""))
        .thenReturn(new Gson().toJson(new AccessTokenDto("access token", "refresh token")));

    AccessTokenDto accessTokenDto = subject.getConfiguration().getAccessToken();

    assertThat(accessTokenDto.getAccessToken(), is("access token"));
    assertThat(accessTokenDto.getRefreshToken(), is("refresh token"));
  }

  public void testSetConfigurationWhenAccessTokenIsNull() throws Exception {
    SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
    when(sharedPreferences.edit()).thenReturn(editor);

    subject.setConfiguration(new Configuration());

    verify(editor).commit();
  }
}
