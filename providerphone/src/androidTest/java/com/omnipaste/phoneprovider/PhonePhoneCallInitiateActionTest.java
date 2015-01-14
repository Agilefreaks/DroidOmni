package com.omnipaste.phoneprovider;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhonePhoneCallInitiateActionTest extends TestCase {
  public void testParseWithLoweCaseWillReturnCorrectValue() throws Exception {
    assertThat(PhoneCallState.parse("call"), is(PhoneCallState.INITIATE));
  }

  public void testParseWithUpperCaseWillReturnCorrectValue() throws Exception {
    assertThat(PhoneCallState.parse("CALL"), is(PhoneCallState.INITIATE));
  }

  public void testParseWithEmptyStringWillReturnUnknown() throws Exception {
    assertThat(PhoneCallState.parse(""), is(PhoneCallState.UNKNOWN));
  }
}