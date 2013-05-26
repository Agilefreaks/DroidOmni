package com.omnipasteapp.androidclipboard.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class AndroidClipboardTest {
  @Before
  public void setUp() {
  }

  @Test
  public void someTest() {
    assertThat(true, is(false));
  }
}
