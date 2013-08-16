package com.omnipasteapp.omniclipboard.api;

import com.omnipasteapp.omniclipboard.api.models.Clipping;

import junit.framework.TestCase;

public class ClippingTest extends TestCase {
  public void testClippingWillSetTokenAndContent() {
    Clipping clipping = new Clipping("token", "some content");

    assertEquals(clipping.getToken(), "token");
    assertEquals(clipping.getContent(), "some content");
  }
}
