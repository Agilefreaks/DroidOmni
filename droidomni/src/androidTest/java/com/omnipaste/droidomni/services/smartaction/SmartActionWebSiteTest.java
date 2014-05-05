package com.omnipaste.droidomni.services.smartaction;

import android.content.Intent;

import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SmartActionWebSiteTest extends TestCase {
  public SmartActionWebSite smartActionWebSite;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    smartActionWebSite = new SmartActionWebSite();
  }

  public void testBuildIntentWillReturnTheCorrectIntent() throws Exception {
    ClippingDto clippingDto = new ClippingDto().setContent("http://www.omnipasteapp.com");

    Intent intent = smartActionWebSite.buildIntent(clippingDto);

    assertThat(intent.getAction(), is(Intent.ACTION_VIEW));
    assertThat(intent.getDataString(), is("http://www.omnipasteapp.com"));
    assertThat(intent.getFlags(), is(Intent.FLAG_ACTIVITY_NEW_TASK));
  }
}
