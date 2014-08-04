package com.omnipaste.omniapi.serializers;

import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TelephonyNotificationTypeSerializerTest extends TestCase {
  private TelephonyNotificationTypeSerializer serializer;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    serializer = new TelephonyNotificationTypeSerializer();
  }

  public void testSerializeWillReturnTheRightPrimitive() throws Exception {
    assertThat(
        serializer.serialize(
            TelephonyEventDto.TelephonyNotificationType.incomingCall,
            TelephonyEventDto.TelephonyNotificationType.class, null)
            .getAsString(),
        is("incoming_call"));
  }
}