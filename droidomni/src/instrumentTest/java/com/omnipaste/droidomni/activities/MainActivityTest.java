package com.omnipaste.droidomni.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.fragments.LoginFragment_;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity_> {
  public MainActivityTest() {
    super(MainActivity_.class);
  }

  public void testAfterViewWhenNoChannelSavedAddsLoginFragment() throws Exception {
    ActionBarActivity activity = getActivity();

    Fragment containerFragment = activity.getSupportFragmentManager().findFragmentById(R.id.container);

    assertThat(containerFragment, instanceOf(LoginFragment_.class));
  }
}
