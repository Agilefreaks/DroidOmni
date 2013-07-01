package com.omnipasteapp.omnicommon.settings;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommunicationSettingsTest extends TestCase {
  private CommunicationSettings subject;

  protected void setUp() {
    subject = new CommunicationSettings("42");
  }

  public void testGetChannelReturnsTheChannelName() {
    assertThat(subject.getChannel(), is("42"));
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
