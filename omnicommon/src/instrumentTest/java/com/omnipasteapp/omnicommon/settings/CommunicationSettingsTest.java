package com.omnipasteapp.omnicommon.settings;

import junit.framework.TestCase;

public class CommunicationSettingsTest extends TestCase {
  private CommunicationSettings subject;

  protected void setUp() {
    subject = new CommunicationSettings("42");
  }

  public void testGetChannelReturnsTheChannelName() {
    assertEquals("42", subject.getChannel());
  }

  public void testHasChannelReturnsFalseWhenChannelIsNull() {
    subject.setChannel(null);

    assertFalse(subject.hasChannel());
  }

  public void testHasChannelReturnsFalseWhenChannelIsEmpty() {
    subject.setChannel("");

    assertFalse(subject.hasChannel());
  }

  public void testHasChannelReturnsTrueWhenChannelIsNotEmpty() {
    subject.setChannel("42");

    assertTrue(subject.hasChannel());
  }
}
