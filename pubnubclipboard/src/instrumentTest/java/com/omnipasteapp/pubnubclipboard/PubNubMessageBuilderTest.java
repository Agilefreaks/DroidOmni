package com.omnipasteapp.pubnubclipboard;

import junit.framework.TestCase;

import java.util.Hashtable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PubNubMessageBuilderTest extends TestCase {

  private PubNubMessageBuilder subject;

  public void setUp() {
    subject = new PubNubMessageBuilder();
  }

  public void testSetChannelAlwaysAddItemWithKeyChannel() {
    Hashtable<String, String> result = subject.setChannel("channel-value").build();

    assertTrue(result.containsKey("channel"));
    assertThat(result.get("channel"), is("channel-value"));
  }

  public void testAddValueAlwaysSetsItemWithMessageToValue() {
    Hashtable<String, String> result = subject.addValue("value").build();

    assertThat(result.get("message"), is("value"));
  }
}
