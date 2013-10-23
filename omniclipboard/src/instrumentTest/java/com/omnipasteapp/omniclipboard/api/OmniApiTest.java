package com.omnipasteapp.omniclipboard.api;

import android.test.AndroidTestCase;

public class OmniApiTest extends AndroidTestCase {
  private IOmniApi subject;

  public void setUp() {
    subject = new OmniApi();
  }

  public void testClippingsReturnsAInstanceOfIClippings() {
    assertTrue(subject.clippings() != null);
  }
}
