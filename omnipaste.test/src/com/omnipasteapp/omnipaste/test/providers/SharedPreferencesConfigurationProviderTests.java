package com.omnipasteapp.omnipaste.test.providers;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnipaste.providers.SharedPreferencesConfigurationProvider;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roboguice.RoboGuice;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class SharedPreferencesConfigurationProviderTests {
  @Mock
  private Context mockContext;
  @Mock
  private SharedPreferences mockSharedPreferences;

  private SharedPreferencesConfigurationProvider subject;

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(Context.class).toInstance(mockContext);
    }
  }

  @Before
  public void Setup() {
    MockitoAnnotations.initMocks(this);
    when(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);

    RoboGuice
        .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
            .with(new TestModule()));

    subject = new SharedPreferencesConfigurationProvider();
    RoboGuice.getInjector(Robolectric.application).injectMembers(subject);
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void getValueGetsSharedPreferencesInPrivateMode() {
    subject.getValue("key");

    verify(mockContext).getSharedPreferences(anyString(), eq(Context.MODE_PRIVATE));
  }

  @Test
  public void getValueCallsSharedPreferencesGetStringValue() {
    subject.getValue("key");

    verify(mockSharedPreferences).getString(eq("key"), anyString());
  }

  @Test
  public void setValueCallsSharedPreferencesEdit() {
    SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
    when(mockSharedPreferences.edit()).thenReturn(mockEditor);

    subject.setValue("key", "value");

    verify(mockSharedPreferences).edit();
  }

  @Test
  public void setValueCallsEditorPutString() {
    SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
    when(mockSharedPreferences.edit()).thenReturn(mockEditor);

    subject.setValue("key", "value");

    verify(mockEditor).putString(eq("key"), eq("value"));
  }

  @Test
  public void setValueCallsCommit() {
    SharedPreferences.Editor mockEditor = mock(SharedPreferences.Editor.class);
    when(mockSharedPreferences.edit()).thenReturn(mockEditor);

    subject.setValue("key", "value");

    verify(mockEditor).commit();
  }
}
