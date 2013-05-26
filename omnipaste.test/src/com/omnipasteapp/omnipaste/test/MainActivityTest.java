package com.omnipasteapp.omnipaste.test;

import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.MainActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

  private MainActivity subject;

  @Before
  public void setUp() {
    subject = Robolectric.buildActivity(MainActivity.class).create().get();
  }

  @Test
  public void shouldAssignOmniService() {
    assertThat(subject.getOmniService(), is(IOmniService.class));
  }
}