package com.omnipaste.droidomni.interaction;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.omniapi.resource.v1.user.Devices;
import com.omnipaste.omnicommon.dto.DeviceDto;
import com.omnipaste.omnicommon.prefs.StringPreference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivateDeviceTest {
  private ActivateDevice activateDevice;

  @Mock
  public Devices devices;

  @Mock
  public StringPreference deviceId;

  @Mock
  public StringPreference gcmSenderId;

  @Mock
  public GoogleCloudMessaging googleCloudMessaging;

  @Before
  public void context() {
    activateDevice = new ActivateDevice(devices, deviceId, gcmSenderId, googleCloudMessaging);
  }

  @Test
  public void runWillRegisterToGcmAndCallActivateOnDevice() throws IOException {
    when(deviceId.get()).thenReturn("42");
    when(gcmSenderId.get()).thenReturn("43");
    when(googleCloudMessaging.register("43")).thenReturn("token");
    when(devices.activate("42", "token")).thenReturn(Observable.<DeviceDto>empty());

    TestScheduler scheduler = new TestScheduler();
    activateDevice.run().subscribeOn(scheduler).subscribe();
    scheduler.triggerActions();

    verify(devices, times(1)).activate("42", "token");
  }
}