package com.omnipaste.omniapi.dto.deserializer;

import com.google.gson.JsonPrimitive;
import com.omnipaste.omniapi.deserializer.ClippingTypeDeserializer;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ClippingTypeDeserializerTest {
  private ClippingTypeDeserializer clippingTypeDeserializer;

  @Before
  public void context() {
    clippingTypeDeserializer = new ClippingTypeDeserializer();
  }

  @Test
  public void deserializeReturnPhoneNumberType() {
    assertThat(ClippingDto.ClippingType.PHONE_NUMBER, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("phone_number"), null, null)));
  }

  @Test
  public void deserializeReturnWebSiteType() {
    assertThat(ClippingDto.ClippingType.URL, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("url"), null, null)));
  }

  @Test
  public void deserializeReturnAddressType() {
    assertThat(ClippingDto.ClippingType.ADDRESS, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("address"), null, null)));
  }

  @Test
  public void deserializeReturnUUnknownType() {
    assertThat(ClippingDto.ClippingType.UNKNOWN, is(clippingTypeDeserializer.deserialize(new JsonPrimitive("something else"), null, null)));
  }
}
