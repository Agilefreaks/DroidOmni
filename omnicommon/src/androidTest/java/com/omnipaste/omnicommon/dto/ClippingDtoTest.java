package com.omnipaste.omnicommon.dto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ClippingDtoTest extends TestCase {
  public void testConstructorWithClippingDtoWillCloneCreateAnObjectWithSameProperties() throws Exception {
    ClippingDto clippingDto = new ClippingDto();
    clippingDto.setDeviceId("42");

    assertThat(new ClippingDto(clippingDto).getDeviceId(), is("42"));
  }

  public void testGetContentLengthWhenContentIsNotNullReturnsTheLength() throws Exception {
    ClippingDto origin = new ClippingDto().setContent("some text");

    assertThat(origin.getContentLength(), is(9));
  }

  public void testGetContentLengthWhenContentIsNullReturnsZero() throws Exception {
    ClippingDto origin = new ClippingDto();

    assertThat(origin.getContentLength(), is(0));
  }
}
