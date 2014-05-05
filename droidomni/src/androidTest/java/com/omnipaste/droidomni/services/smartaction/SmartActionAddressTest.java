package com.omnipaste.droidomni.services.smartaction;

import android.content.Intent;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SmartActionAddressTest extends TestCase {
  public SmartActionAddress smartActionAddress;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    smartActionAddress = new SmartActionAddress();
  }

  public void testBuildIntentWillReturnTheCorrectIntent() throws Exception {
    ClippingDto clippingDto = new ClippingDto().setContent("str Avram Iancu, nr. 1 - 3, ap. 5");

    Intent intent = smartActionAddress.buildIntent(clippingDto);

    assertThat(intent.getAction(), is(Intent.ACTION_VIEW));
    assertThat(intent.getDataString(), is("google.navigation:q=str Avram Iancu, nr. 1 - 3, ap. 5"));
    assertThat(intent.getFlags(), is(Intent.FLAG_ACTIVITY_NEW_TASK));
  }
}
