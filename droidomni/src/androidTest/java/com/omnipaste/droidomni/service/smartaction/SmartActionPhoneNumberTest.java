package com.omnipaste.droidomni.service.smartaction;

import android.content.Intent;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SmartActionPhoneNumberTest extends TestCase {
  private SmartActionPhoneNumber smartActionPhoneNumber;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    smartActionPhoneNumber = new SmartActionPhoneNumber();
  }

  public void testBuildIntentWillReturnACorrectIntent() throws Exception {
    ClippingDto clippingDto = new ClippingDto().setContent("42");

    Intent intent = smartActionPhoneNumber.buildIntent(clippingDto);

    assertThat(intent.getAction(), is(Intent.ACTION_CALL));
    assertThat(intent.getDataString(), is("tel:42"));
    assertThat(intent.getFlags(), is(Intent.FLAG_ACTIVITY_NEW_TASK));
  }
}
