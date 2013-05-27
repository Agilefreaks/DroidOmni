package com.omnipasteapp.omnipaste.test;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.MainActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

  private MainActivity subject;
  private IOmniService omniService = mock(IOmniService.class);

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IOmniService.class).toInstance(omniService);
    }
  }

  @Before
  public void setUp() {
    RoboGuice
        .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
            .with(new TestModule()));

    subject = Robolectric.buildActivity(MainActivity.class).create().get();
  }

  @Test
  public void shouldAssignOmniService() {
    assertThat(subject.getOmniService(), is(IOmniService.class));
 }

  @Test
  public void onCreateCallsStart() throws InterruptedException {
    verify(omniService).start();
  }
}