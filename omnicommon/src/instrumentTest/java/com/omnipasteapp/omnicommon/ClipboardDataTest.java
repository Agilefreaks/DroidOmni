package com.omnipasteapp.omnicommon;

import com.omnipasteapp.omnicommon.interfaces.IClipboardData;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ClipboardDataTest extends TestCase {
    private IClipboardData subject;

    protected void setUp() {
        subject = new ClipboardData(new Object(), "42");
    }

    public void testGetData() {
        assertThat(subject.getData(), is("42"));
    }

    public void testGetSender() {
        assertNotNull(subject.getSender());
    }
}
