package com.omnipaste.droidomni.domain;

import android.os.Bundle;

import com.omnipaste.omnicommon.dto.NotificationDto;

import junit.framework.TestCase;

import org.json.JSONObject;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GcmNotificationTest extends TestCase {
  public void testCorrectValuesGetSet() throws Exception {
    Bundle bundle = new Bundle();
    bundle.putString(GcmNotification.TYPE_KEY, "clipping_created");
    bundle.putString(GcmNotification.PAYLOAD_KEY, new JSONObject(new HashMap<String, String>() {{
      put(GcmNotification.ID_KEY, "42");
    }}).toString());
    GcmNotification gcmNotification = new GcmNotification(bundle);

    assertThat(gcmNotification.getType(), is(NotificationDto.Type.CLIPPING_CREATED));
    assertThat(gcmNotification.getId(), is("42"));
  }

  public void testUnknownType() throws Exception {
    Bundle bundle = new Bundle();
    bundle.putString(GcmNotification.TYPE_KEY, "some other type");

    GcmNotification gcmNotification = new GcmNotification(bundle);

    assertThat(gcmNotification.getType(), is(NotificationDto.Type.UNKNOWN));
  }
}
