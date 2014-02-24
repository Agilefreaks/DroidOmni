package com.omnipaste.droidomni.integration;

import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.activities.OmniActivity_;
import com.robotium.solo.Solo;

import static junit.framework.Assert.assertTrue;

public class TestHelper {
  public static void signOut(Solo solo) {
    solo.clickOnView(solo.getView(android.R.id.home));
    solo.clickOnText("Sign out");
  }

  public static void signIn(Solo solo) {
    solo.assertCurrentActivity("Should be in the MainActivity", MainActivity_.class);
    solo.clickInList(1);
    assertTrue("Should be in the OmniActivity", solo.waitForActivity(OmniActivity_.class));
  }
}
