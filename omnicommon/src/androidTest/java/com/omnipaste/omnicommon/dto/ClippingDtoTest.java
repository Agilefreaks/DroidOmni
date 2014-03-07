package com.omnipaste.omnicommon.dto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ClippingDtoTest extends TestCase {
  public void testConstructorWithClippingDtoWillCloneCreateAnObjectWithSameProperties() throws Exception {
    ClippingDto origin = new ClippingDto();
    origin.setIdentifier("42");

    assertThat(new ClippingDto(origin).getIdentifier(), is("42"));
  }
}
