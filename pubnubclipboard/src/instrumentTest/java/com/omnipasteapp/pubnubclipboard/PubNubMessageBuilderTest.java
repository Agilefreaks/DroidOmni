package com.omnipasteapp.pubnubclipboard;

import junit.framework.TestCase;

import java.util.Hashtable;

public class PubNubMessageBuilderTest extends TestCase {

  private PubNubMessageBuilder subject;

  public void setUp() {
    subject = new PubNubMessageBuilder();
  }

  public void testSetChannelAlwaysAddItemWithKeyChannel() {
    Hashtable<String, String> result = subject.setChannel("channel-value").build();

    assertTrue(result.containsKey("channel"));
    assertEquals("channel-value", result.get("channel"));
  }

  public void testAddValueAlwaysSetsItemWithMessageToValue() {
    Hashtable<String, String> result = subject.addValue("value").build();

    assertEquals("value", result.get("message"));
  }
}
