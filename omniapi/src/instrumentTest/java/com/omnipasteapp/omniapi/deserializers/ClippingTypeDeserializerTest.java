package com.omnipasteapp.omniapi.deserializers;

import com.google.gson.JsonPrimitive;
import com.omnipasteapp.omnicommon.models.ClippingType;

import junit.framework.TestCase;

public class ClippingTypeDeserializerTest extends TestCase {
  private ClippingTypeDeserializer _subject;

  protected void setUp() {
    _subject = new ClippingTypeDeserializer();
  }

  public void testDeserializeReturnPhoneNumberType() {
    assertEquals(ClippingType.PhoneNumber, _subject.deserialize(new JsonPrimitive("phone_number"), null, null));
  }

  public void testDeserializeReturnUriType() {
    assertEquals(ClippingType.URI, _subject.deserialize(new JsonPrimitive("uri"), null, null));
  }

  public void testDeserializeReturnUUnknownType() {
    assertEquals(ClippingType.Unknown, _subject.deserialize(new JsonPrimitive("something else"), null, null));
  }
}
