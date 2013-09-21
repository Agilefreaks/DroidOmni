package com.omnipasteapp.omnicommon;

import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IClipping;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClipboardDataTest extends TestCase {
  private IClipboardData subject;

  protected void setUp() {
    IClipping clipping = mock(IClipping.class);
    when(clipping.getContent()).thenReturn("42");

    subject = new ClipboardData(new Object(), clipping);
  }

  public void testGetData() {
    assertEquals(subject.getData(), "42");
  }

  public void testGetSender() {
    assertNotNull(subject.getSender());
  }
}
