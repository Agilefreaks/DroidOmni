package com.omnipaste.droidomni.domain;

import android.os.Bundle;

import com.omnipaste.omnicommon.dto.NotificationDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GcmNotificationTest extends TestCase {
  public void testCorrectValuesGetSet() throws Exception {
    Bundle bundle = new Bundle();
    bundle.putString(GcmNotification.PROVIDER_KEY, "CLIPBOARD");
    bundle.putString(GcmNotification.REGISTRATION_ID_KEY, "42");
    GcmNotification gcmNotification = new GcmNotification(bundle);

    assertThat(gcmNotification.getProvider(), is(NotificationDto.Target.CLIPBOARD));
    assertThat(gcmNotification.getRegistrationId(), is("42"));
  }

  public void testUnknownProvider() throws Exception {
    Bundle bundle = new Bundle();
    bundle.putString(GcmNotification.PROVIDER_KEY, "notification");

    GcmNotification gcmNotification = new GcmNotification(bundle);

    assertThat(gcmNotification.getProvider(), is(NotificationDto.Target.UNKNOWN));
  }
}
