package com.omnipasteapp.omnipaste.test;

import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.MainActivity;
import com.omnipasteapp.omnipaste.services.IIntentService;
import com.omnipasteapp.omnipaste.util.ActivityTestModule;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

  public class TestModule extends ActivityTestModule {
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

    subject = new MainActivity();
    RoboGuice.getInjector(Robolectric.application).injectMembersWithoutViews(subject);
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