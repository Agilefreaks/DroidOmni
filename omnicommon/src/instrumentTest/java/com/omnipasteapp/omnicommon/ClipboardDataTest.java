package com.omnipasteapp.omnicommon;

import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.models.Clipping;

import junit.framework.TestCase;

public class ClipboardDataTest extends TestCase {
  private IClipboardData subject;

  protected void setUp() {
    subject = new ClipboardData(new Object(), new Clipping("", "42"));
  }

  public void testGetData() {
    assertEquals(subject.getData(), "42");
  }

  public void testGetSender() {
    assertNotNull(subject.getSender());
  }
}
