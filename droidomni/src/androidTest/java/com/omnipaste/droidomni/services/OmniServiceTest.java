package com.omnipaste.droidomni.services;

import android.test.InstrumentationTestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class OmniServiceTest extends InstrumentationTestCase {

  @Override
  @SuppressWarnings("ConstantConditions")
  public void setUp() throws Exception {
    super.setUp();

    System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
  }

  public void testConstructorAddsAllSubscribers() throws Exception {
    OmniService_ omniService = new OmniService_();

    assertThat(omniService.getSubscribers(),
        contains(
            omniService.clipboardSubscriber,
            omniService.phoneSubscribe,
            omniService.telephonyNotificationsSubscriber,
            omniService.gcmWorkaroundSubscriber));
  }
}