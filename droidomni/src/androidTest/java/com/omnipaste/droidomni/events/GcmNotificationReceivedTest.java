package com.omnipaste.droidomni.events;

import android.os.Bundle;

import com.omnipaste.omnicommon.dto.NotificationDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GcmNotificationReceivedTest extends TestCase {
  public void testCorrectValuesGetSet() throws Exception {
    Bundle bundle = new Bundle();
    bundle.putString(GcmNotificationReceived.PROVIDER_KEY, "CLIPBOARD");
    bundle.putString(GcmNotificationReceived.REGISTRATION_ID_KEY, "42");
    GcmNotificationReceived gcmNotificationReceived = new GcmNotificationReceived(bundle);

    assertThat(gcmNotificationReceived.getProvider(), is(NotificationDto.Target.CLIPBOARD));
    assertThat(gcmNotificationReceived.getRegistrationId(), is("42"));
  }

  public void testUnknownProvider() throws Exception {
    Bundle bundle = new Bundle();
    bundle.putString(GcmNotificationReceived.PROVIDER_KEY, "notification");

    GcmNotificationReceived gcmNotificationReceived = new GcmNotificationReceived(bundle);

    assertThat(gcmNotificationReceived.getProvider(), is(NotificationDto.Target.UNKNOWN));
  }
}
