package com.omnipasteapp.omnicommon.models;

import junit.framework.TestCase;

public class DeviceTest extends TestCase {
  Device subject;

  protected void setUp() {
    subject = new Device("132");
  }

  public void testGetRegistrationId() {
    assertEquals(subject.getRegistrationId(), "132");
  }
}
