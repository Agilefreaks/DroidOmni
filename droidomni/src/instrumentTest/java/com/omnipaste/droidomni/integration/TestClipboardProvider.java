package com.omnipaste.droidomni.integration;

import com.omnipaste.droidomni.activities.MainActivity_;

public class TestClipboardProvider extends TestIntegration<MainActivity_> {
  public TestClipboardProvider() {
    super(MainActivity_.class);
  }

  public void testClipboardProviderShouldNotDuplicatedItems() {
    solo.waitForActivity(MainActivity_.class, 2000);
  }
}
