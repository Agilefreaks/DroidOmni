package com.omnipasteapp.omnipaste.test;

import android.content.Intent;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnipaste.BackgroundService;
import com.omnipasteapp.omnipaste.BackgroundServiceCommandReceiver;
import com.omnipasteapp.omnipaste.enums.BackgroundServiceStates;
import com.omnipasteapp.omnipaste.services.IIntentService;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roboguice.RoboGuice;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class BackgroundServiceCommandReceiverTests {

  private BackgroundServiceCommandReceiver _subject;

  @Mock
  private IIntentService _intentService;

  public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(IIntentService.class).toInstance(_intentService);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice
            .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
                    .with(new TestModule()));

    _subject = new BackgroundServiceCommandReceiver();
    RoboGuice.getInjector(Robolectric.application).injectMembers(_subject);
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void onHandleReceivedWhenActionIsStartServiceAndServiceIsInactiveCallsIntentServiceStartService() {
    BackgroundService.serviceState = BackgroundServiceStates.Inactive;
    Intent intent = new Intent();
    intent.setAction(BackgroundServiceCommandReceiver.START_SERVICE);

    _subject.onHandleReceive(null, intent);

    verify(_intentService).startService(eq(BackgroundService.class));
  }

  @Test
  public void onHandleReceivedWhenActionIsStopServiceCallsIntentSericeStopService() {
    BackgroundService.serviceState = BackgroundServiceStates.Active;
    Intent intent = new Intent();
    intent.setAction(BackgroundServiceCommandReceiver.STOP_SERVICE);

    _subject.onHandleReceive(null, intent);

    verify(_intentService).stopService(eq(BackgroundService.class));
  }
}
