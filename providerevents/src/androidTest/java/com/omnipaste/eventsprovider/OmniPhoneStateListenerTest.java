package com.omnipaste.eventsprovider;

import com.omnipaste.eventsprovider.listeners.OmniPhoneStateListener;

import junit.framework.TestCase;

public class OmniPhoneStateListenerTest extends TestCase {
  OmniPhoneStateListener omniPhoneStateListener;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    omniPhoneStateListener = new OmniPhoneStateListener();
  }
}