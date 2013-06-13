package com.omnipasteapp.omnipaste.test;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
            .with(new TestModule()));
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }
}
