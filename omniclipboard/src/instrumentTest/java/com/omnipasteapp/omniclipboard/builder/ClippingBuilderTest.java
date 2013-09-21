package com.omnipasteapp.omniclipboard.builder;

import com.omnipasteapp.omniclipboard.api.builders.ClippingBuilder;
import com.omnipasteapp.omniclipboard.api.models.Clipping;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class ClippingBuilderTest extends TestCase {
  private ClippingBuilder subject;

  protected void setUp() {
    subject = new ClippingBuilder();
  }

  public void testBuildForClipping() {
    String json = "{\"_id\":{\"$oid\":\"523dd59e9d79b0bce9000003\"},\"content\":\"0745857479\",\"created_at\":\"2013-09-21T17:21:34.095Z\",\"token\":\"calinuswork@gmail.com\",\"type\":\"phone_number\",\"updated_at\":\"2013-09-21T17:21:34.095Z\"}\n";
    Clipping clipping = subject.build(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(json.getBytes()))));

    assertEquals("0745857479", clipping.getContent());
    assertEquals("calinuswork@gmail.com", clipping.getToken());
    assertEquals("phone_number", clipping.getType());
  }
}
