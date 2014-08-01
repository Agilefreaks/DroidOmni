package com.omnipaste.omnicommon.domain;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhoneActionTest extends TestCase {
  public void testParseWithLoweCaseWillReturnCorrectValue() throws Exception {
    assertThat(PhoneAction.parse("call"), is(PhoneAction.CALL));
  }

  public void testParseWithUpperCaseWillReturnCorrectValue() throws Exception {
    assertThat(PhoneAction.parse("CALL"), is(PhoneAction.CALL));
  }

  public void testParseWithEmptyStringWillReturnUnknown() throws Exception {
    assertThat(PhoneAction.parse(""), is(PhoneAction.UNKNOWN));
  }
}