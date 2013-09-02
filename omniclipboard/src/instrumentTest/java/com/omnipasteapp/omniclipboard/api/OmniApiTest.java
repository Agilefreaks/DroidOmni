package com.omnipasteapp.omniclipboard.api;

import android.content.Context;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;

public class OmniApiTest extends TestCase {
  private IOmniApi subject;

  public void setUp() {
    subject = new OmniApi(mock(Context.class));
  }

  public void testClippingsReturnsAInstanceOfIClippings() {
    assertTrue(subject.clippings() != null);
  }
}
