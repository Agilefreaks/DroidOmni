package com.omnipaste.droidomni.integration;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import com.omnipaste.droidomni.R;
import com.robotium.solo.Solo;

public abstract class TestIntegration<TActivity extends Activity> extends ActivityInstrumentationTestCase2<TActivity> {
  protected Solo solo;
  protected Activity activity;
  protected Instrumentation instrumentation;

  protected TestIntegration(Class<TActivity> activityClass) {
    super(activityClass);
  }

  public void setUp() throws Exception {
    instrumentation = getInstrumentation();
    activity = getActivity();
    solo = new Solo(instrumentation, activity);
    localSetUp();
  }

  public void tearDown() throws Exception {
    solo.finishOpenedActivities();
  }

  protected void localSetUp() {
    ensureLoggedOut();
  }

  protected void ensureLoggedOut() {
    if (solo.waitForView(R.id.clippingsPager, 1, 5000)) {
      // we need to sign out
      TestHelper.signOut(solo);
    }
  }
}
