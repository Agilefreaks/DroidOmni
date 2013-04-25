package com.omnipasteapp.omni.communication;

import com.omnipasteapp.omni.core.communication.Clipboard;
import com.omnipasteapp.omni.core.communication.DefaultClipboardMediator;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DefaultClipboardMediatorTest extends TestCase {

    private Clipboard _localClipboard;
    private Clipboard _remoteClipboard;
    private DefaultClipboardMediator _subject;

    @Before
    public void setUp(){
        _localClipboard = mock(Clipboard.class);
        _remoteClipboard = mock(Clipboard.class);

        _subject = new DefaultClipboardMediator();
        _subject.setLocalClipboard(_localClipboard);
        _subject.setRemoteClipboard(_remoteClipboard);
    }

    @Test
    public void testHandleWhenSenderIsLocalClipboardCallsRemoteClipboardPut(){
        _subject.handle(_localClipboard, "message");

        verify(_remoteClipboard).put("message");
    }

    @Test
    public void testHandleWhenSenderIsRemoteClipboardCallsLocalClipboardPut(){
        _subject.handle(_remoteClipboard, "message");

        verify(_localClipboard).put("message");
    }
}
