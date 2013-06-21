package com.omnipasteapp.omnipaste.test;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.BackgroundService;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roboguice.RoboGuice;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class BackgroundServiceTest {
  private BackgroundService subject;

  @Mock
  private IOmniService omniService;

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IOmniService.class).toInstance(omniService);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice
        .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
            .with(new TestModule()));

    subject = new BackgroundService();
    RoboGuice.getInjector(Robolectric.application).injectMembers(subject);
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void onDestroyCallsOmniServiceStop() {
    subject.onDestroy();

    verify(omniService).stop();
  }
}
