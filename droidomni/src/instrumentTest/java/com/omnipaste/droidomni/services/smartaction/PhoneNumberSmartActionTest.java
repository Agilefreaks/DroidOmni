package com.omnipaste.droidomni.services.smartaction;

import android.content.Intent;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PhoneNumberSmartActionTest extends TestCase {
  private PhoneNumberSmartAction phoneNumberSmartAction;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    phoneNumberSmartAction = new PhoneNumberSmartAction();
  }

  public void testBuildIntentWillReturnACorrectIntent() throws Exception {
    ClippingDto clippingDto = new ClippingDto().setContent("42");

    Intent intent = phoneNumberSmartAction.buildIntent(clippingDto);

    assertThat(intent.getAction(), is(Intent.ACTION_CALL));
    assertThat(intent.getDataString(), is("tel:42"));
    assertThat(intent.getFlags(), is(Intent.FLAG_ACTIVITY_NEW_TASK));
  }
}
