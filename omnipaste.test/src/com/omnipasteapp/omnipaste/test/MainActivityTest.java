package com.omnipasteapp.omnipaste.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import com.omnipasteapp.omnipaste.MainActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

  private MainActivity subject;

  @Before
  public void setUp(){
    subject = new MainActivity();
  }

  @Test
  public void shouldPass() {
    assertThat(true, is(true));
  }
}