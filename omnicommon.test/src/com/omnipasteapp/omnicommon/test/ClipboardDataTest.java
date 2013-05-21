package com.omnipasteapp.omnicommon.test;

import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ClipboardDataTest {
  private IClipboardData subject;

  @Before
  public void setUp() {
    subject = new ClipboardData(new Object(), "42");
  }

  @Test
  public void testGetData() {
    assertThat(subject.getData(), is("42"));
  }

  @Test
  public void testGetSender() {
    assertThat(subject.getSender(), is(Object.class));
  }
}
