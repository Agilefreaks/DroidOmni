package com.omnipaste.omniapi.deserializers;

import com.google.gson.JsonPrimitive;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TelephonyEventTypeDeserializerTest extends TestCase {
  private TelephonyEventTypeDeserializer telephonyEventTypeDeserializer;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    telephonyEventTypeDeserializer = new TelephonyEventTypeDeserializer();
  }

  public void testDeserializeReturnsIncomingCallType() throws Exception {
    assertThat(TelephonyEventDto.TelephonyEventType.incomingCall, is(telephonyEventTypeDeserializer.deserialize(new JsonPrimitive("incoming_call"), null, null)));
  }

  public void testDeserializeReturnsIncomingSmsType() throws Exception {
    assertThat(TelephonyEventDto.TelephonyEventType.incomingSms, is(telephonyEventTypeDeserializer.deserialize(new JsonPrimitive("incoming_sms"), null, null)));
  }
}