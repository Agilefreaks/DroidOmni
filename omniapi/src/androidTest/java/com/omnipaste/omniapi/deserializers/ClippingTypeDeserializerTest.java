package com.omnipaste.omniapi.deserializers;

import com.google.gson.JsonPrimitive;
import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ClippingTypeDeserializerTest extends TestCase {
  private ClippingTypeDeserializer clippingTypeDeserializer;

  protected void setUp() {
    clippingTypeDeserializer = new ClippingTypeDeserializer();
  }

  public void testDeserializeReturnPhoneNumberType() {
    assertThat(ClippingDto.ClippingType.phoneNumber, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("phone_number"), null, null)));
  }

  public void testDeserializeReturnWebSiteType() {
    assertThat(ClippingDto.ClippingType.url, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("url"), null, null)));
  }

  public void testDeserializeReturnAddressType() {
    assertThat(ClippingDto.ClippingType.address, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("address"), null, null)));
  }

  public void testDeserializeReturnUUnknownType() {
    assertThat(ClippingDto.ClippingType.unknown, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("something else"), null, null)));
  }
}
