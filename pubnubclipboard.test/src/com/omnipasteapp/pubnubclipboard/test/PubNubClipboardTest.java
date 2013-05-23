package com.omnipasteapp.pubnubclipboard.test;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class PubNubClipboardTest {
  @Before
  public void setUp() {
  }

  @Test
  public void someTest() {
    assertThat(true, is(false));
  }
}
