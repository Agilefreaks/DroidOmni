package com.omnipasteapp.omniclipboard.api;

import junit.framework.TestCase;

public class OmniApiTest extends TestCase {
  private IOmniApi subject;

  public void setUp() {
    subject = new OmniApi();
  }

  public void testClippingsReturnsAInstanceOfIClippings() {
    assertTrue(subject.clippings() != null);
  }
}
