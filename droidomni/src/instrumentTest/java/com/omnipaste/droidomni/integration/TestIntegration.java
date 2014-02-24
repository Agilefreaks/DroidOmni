package com.omnipaste.droidomni.integration;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public abstract class TestIntegration<TActivity extends Activity> extends ActivityInstrumentationTestCase2<TActivity> {
  protected Solo solo;

  protected TestIntegration(Class<TActivity> activityClass) {
    super(activityClass);
  }

  public void setUp() throws Exception {
    solo = new Solo(getInstrumentation(), getActivity());
    localSetUp();
  }

  public void tearDown() throws Exception {
    solo.finishOpenedActivities();
  }

  protected void localSetUp() {
  }
}
