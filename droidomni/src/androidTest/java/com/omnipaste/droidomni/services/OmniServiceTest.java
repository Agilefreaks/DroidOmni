package com.omnipaste.droidomni.services;

import android.test.InstrumentationTestCase;

import com.omnipaste.droidomni.fragments.DeviceInitFragment;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OmniServiceTest extends InstrumentationTestCase {

  @Override
  @SuppressWarnings("ConstantConditions")
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
  }

  public void testAddsSubscriberIfEnabled() throws Exception {
    OmniService_ omniService = new OmniService_();

    ConfigurationService mockConfigurationService = mock(ConfigurationService.class);
    when(mockConfigurationService.isClipboardNotificationEnabled()).thenReturn(true);
    when(mockConfigurationService.isTelephonyServiceEnabled()).thenReturn(false);
    when(mockConfigurationService.isTelephonyNotificationEnabled()).thenReturn(true);
    when(mockConfigurationService.isGcmWorkAroundEnabled()).thenReturn(false);
    omniService.configurationService = mockConfigurationService;

    assertThat(omniService.getSubscribers(),
        contains(
            omniService.clipboardSubscriber.get(),
            omniService.telephonyNotificationsSubscriber.get())
    );
  }
}