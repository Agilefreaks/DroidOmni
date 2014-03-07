package com.omnipaste.droidomni.integration;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;

public class TestSessions extends TestIntegration<MainActivity_> {
  public TestSessions() {
    super(MainActivity_.class);
  }

  @SuppressWarnings("ConstantConditions")
  public void testLoginLogout() throws Exception {
    TestHelper.signIn(solo);
    assertTrue("Should show a list view with clippings", solo.waitForView(R.id.clippings));

    TestHelper.signOut(solo);
    assertTrue("Should be on main activity", solo.waitForActivity(MainActivity_.class));
  }
}
