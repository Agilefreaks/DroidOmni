package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Devices;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;

public class OmniApiTest extends TestCase {
  private OmniApi subject;

  public void setUp() throws Exception {
    super.setUp();

    subject = new OmniApi("http://test.omnipasteapp.com/api");
  }

  public void testDevices() throws Exception {
    assertThat(subject.devices(), instanceOf(Devices.class));
  }

  public void testDevicesWillReturnTheSameInstance() throws Exception {
    assertThat(subject.devices(), sameInstance(subject.devices()));
  }
}
