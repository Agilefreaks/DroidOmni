package com.omnipaste.droidomni.events;

import android.os.Bundle;

import com.omnipaste.omnicommon.Provider;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GcmEventTest extends TestCase {
  public void testCorrectValuesGetSet() throws Exception {
    Bundle bundle = new Bundle();
    bundle.putString(GcmEvent.COLLAPSE_KEY, "clipboard");
    bundle.putString(GcmEvent.REGISTRATION_ID_KEY, "42");
    GcmEvent gcmEvent = new GcmEvent(bundle);

    assertThat(gcmEvent.getProvider(), is(Provider.clipboard));
    assertThat(gcmEvent.getRegistrationId(), is("42"));
  }
}
