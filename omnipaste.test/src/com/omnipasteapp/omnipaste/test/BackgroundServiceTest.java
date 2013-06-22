package com.omnipasteapp.omnipaste.test;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.framework.IOmniServiceFactory;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.BackgroundService;
import com.omnipasteapp.omnipaste.enums.BackgroundServiceStates;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roboguice.RoboGuice;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BackgroundServiceTest {
  private BackgroundService _subject;

  @Mock
  private IOmniServiceFactory _omniServiceFactory;

  @Mock
  private IOmniService _omniService;

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IOmniServiceFactory.class).toInstance(_omniServiceFactory);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice
            .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
                    .with(new TestModule()));

    when(_omniServiceFactory.create()).thenReturn(_omniService);

    _subject = new BackgroundService();
    RoboGuice.getInjector(Robolectric.application).injectMembers(_subject);
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void onDestroyCallsOmniServiceStop() {
    _subject.start();

    _subject.onDestroy();

    verify(_omniService).stop();
  }

  @Test
  public void initAlwaysCallsOmniServiceFactoryCreate() {
    _subject.start();

    verify(_omniServiceFactory).create();
  }

  @Test
  public void initAlwaysSetsBackgroundServiceStateToActive() {
    _subject.start();

    assertEquals(BackgroundServiceStates.Active, BackgroundService.serviceState);
  }

  @Test
  public void onDestroyAlwaysSetsBackgroundServiceStateToInactive() {
    _subject.start();

    _subject.onDestroy();

    assertEquals(BackgroundServiceStates.Inactive, BackgroundService.serviceState);
  }
}
