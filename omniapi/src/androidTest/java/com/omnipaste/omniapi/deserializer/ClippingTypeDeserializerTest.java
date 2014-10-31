package com.omnipaste.omniapi.deserializer;

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
    assertThat(ClippingDto.ClippingType.PHONE_NUMBER, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("phone_number"), null, null)));
  }

  public void testDeserializeReturnWebSiteType() {
    assertThat(ClippingDto.ClippingType.URL, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("url"), null, null)));
  }

  public void testDeserializeReturnAddressType() {
    assertThat(ClippingDto.ClippingType.ADDRESS, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("address"), null, null)));
  }

  public void testDeserializeReturnUUnknownType() {
    assertThat(ClippingDto.ClippingType.UNKNOWN, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("something else"), null, null)));
  }
}
