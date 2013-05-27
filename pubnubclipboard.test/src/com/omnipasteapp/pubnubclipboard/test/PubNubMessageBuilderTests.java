package com.omnipasteapp.pubnubclipboard.test;

import com.omnipasteapp.pubnubclipboard.PubNubMessageBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class PubNubMessageBuilderTests {

  private PubNubMessageBuilder subject;

  @Before
  public void setUp(){
    subject = new PubNubMessageBuilder();
  }

  @Test
  public void setChannelAlwaysAddItemWithKeyChannel(){
    Hashtable<String, String> result = subject.setChannel("channel-value").build();

    assertTrue(result.containsKey("channel"));
    assertEquals("channel-value", result.get("channel"));
  }

  @Test
  public void addValueAlwaysSetsItemWithMessageToValue(){
    Hashtable<String, String> result = subject.addValue("value").build();

    assertEquals("value", result.get("message"));
  }
}
