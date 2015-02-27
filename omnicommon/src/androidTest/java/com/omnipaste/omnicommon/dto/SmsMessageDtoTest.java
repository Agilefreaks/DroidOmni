package com.omnipaste.omnicommon.dto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SmsMessageDtoTest extends TestCase {
  public void testConstructorWithNoParamsWillSetDefaultTypeAndState() {
    SmsMessageDto smsMessageDto = new SmsMessageDto();

    assertThat(smsMessageDto.getType(), equalTo(SmsMessageDto.Type.INCOMING));
    assertThat(smsMessageDto.getState(), equalTo(SmsMessageDto.State.RECEIVED));
  }
}