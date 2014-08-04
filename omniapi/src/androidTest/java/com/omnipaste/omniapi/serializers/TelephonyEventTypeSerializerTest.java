package com.omnipaste.omniapi.serializers;

import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TelephonyEventTypeSerializerTest extends TestCase {
  private TelephonyEventTypeSerializer serializer;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    serializer = new TelephonyEventTypeSerializer();
  }

  public void testSerializeWillReturnTheRightPrimitive() throws Exception {
    assertThat(
        serializer.serialize(
            TelephonyEventDto.TelephonyEventType.incomingCall,
            TelephonyEventDto.TelephonyEventType.class, null)
            .getAsString(),
        is("incoming_call"));
  }
}