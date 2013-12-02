package com.omnipasteapp.omniapi.resources;

import junit.framework.TestCase;

public class ClippingsTest extends TestCase {
  private Clippings _subject;

  protected void setUp() {
    _subject = Resource.build(Clippings.class, "http://api.omnipasteapp.com", "v1", "test@omnipasteapp.com");
  }

  public void testGetUri() {
    assertEquals(_subject.getUri(), "http://api.omnipasteapp.com/v1/clippings");
  }
}
