package com.omnipaste.droidomni.integration;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.activities.OmniActivity_;

public class TestSessions extends TestIntegration<MainActivity_> {
  public TestSessions() {
    super(MainActivity_.class);
  }

  @SuppressWarnings("ConstantConditions")
  public void testLoginLogout() throws Exception {
    // sign in
    solo.assertCurrentActivity("Should be in the MainActivity", MainActivity_.class);
    solo.clickInList(1);
    assertTrue("Should be in the OmniActivity", solo.waitForActivity(OmniActivity_.class));
    assertTrue("Should show a list view with clippings", solo.waitForView(R.id.clippings));

    signOut();
    assertTrue("Should be on main activity", solo.waitForActivity(MainActivity_.class));
  }

  @Override
  protected void localSetUp() {
    ensureLoggedOut();
  }

  private void ensureLoggedOut() {
    if (solo.waitForView(R.id.clippings, 1, 5000)) {
      // we need to sign out
      signOut();
    }
  }

  private void signOut() {
    solo.clickOnView(solo.getView(android.R.id.home));
    solo.clickOnText("Sign out");
  }
}
