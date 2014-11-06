package com.omnipaste.droidomni.fragments.clippings;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ClippingsListActionFactoryTest extends TestCase {
  public IClippingsListFactory subject;

  public void setUp() throws Exception {
    super.setUp();

    subject = new ClippingsListFactory();
  }

  public void testGetCount() throws Exception {
    assertThat(subject.getCount(), is(3));
  }
}