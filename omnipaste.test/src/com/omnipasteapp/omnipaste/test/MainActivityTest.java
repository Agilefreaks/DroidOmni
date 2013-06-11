package com.omnipasteapp.omnipaste.test;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.MainActivity;
import com.omnipasteapp.omnipaste.services.IIntentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
  private MainActivity subject;
  @Mock
  private IOmniService omniService;

  @Mock
  private IConfigurationService configurationService;

  @Mock
  private IIntentService intentService;

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IConfigurationService.class).toInstance(configurationService);
      bind(IOmniService.class).toInstance(omniService);
      bind(IIntentService.class).toInstance(intentService);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice
        .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
            .with(new TestModule()));

    subject = Robolectric.buildActivity(MainActivity.class).get();
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void onCreateWhenIsConfiguredReturnsFalseDoesNotCallStart() throws InterruptedException {
    when(omniService.isConfigured()).thenReturn(false);

    subject.onCreate(null);

    verify(omniService, never()).start();
  }

  @Test
  public void onCreateAlwaysCallsLoadCommunicationSettings(){
    subject.onCreate(null);

    verify(configurationService).loadCommunicationSettings();
  }
}