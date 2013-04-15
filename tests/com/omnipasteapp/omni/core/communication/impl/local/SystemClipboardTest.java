package com.omnipasteapp.omni.core.communication.impl.local;

import android.content.ClipData;
import android.content.ClipboardManager;
import com.omnipasteapp.omni.core.communication.Clipboard;
import com.omnipasteapp.omni.core.communication.ClipboardListener;
import com.omnipasteapp.omni.core.communication.impl.local.services.ClipDataService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SystemClipboardTest extends TestCase {

    private ClipboardManager _clipboardManager;
    private ClipboardListener _clipboardListener;
    private ClipDataService _clipDataService;
    private SystemClipboard _subject;

    @Before
    public void setUp(){
        _clipboardManager = mock(ClipboardManager.class);
        _clipboardListener = mock(ClipboardListener.class);

        _subject = new SystemClipboard(_clipboardManager);
        _subject.setClipboardListener(_clipboardListener);
        _clipDataService = mock(ClipDataService.class);
        _subject.setClipDataService(_clipDataService);
    }

    @Test
    public void testOnPrimaryClipChangedCallsListenerHandle() {
        ClipData.Item item = mock(ClipData.Item.class);
        stub(item.getText()).toReturn("message");
        ClipData result = mock(ClipData.class);
        stub(result.getItemAt(0)).toReturn(item);
        stub(_clipboardManager.getPrimaryClip()).toReturn(result);

        _subject.onPrimaryClipChanged();

        verify(_clipboardListener).handle(_subject, "message");
    }

    @Test
    public void testPutCallsClipboardManagerSetPrimaryClip(){
        stub(_clipDataService.create("message")).toReturn(mock(ClipData.class));
        doNothing().when(_clipboardManager).setPrimaryClip(any(ClipData.class));

        _subject.put("message");

        verify(_clipboardManager).setPrimaryClip(any(ClipData.class));
    }

    @Test
    public void testOnPrimaryClipChangedWhenTheMessageIsSameAsTheOneInPutDoesNotCallListenerHandle(){
        ClipData.Item item = mock(ClipData.Item.class);
        stub(item.getText()).toReturn("message");
        ClipData result = mock(ClipData.class);
        stub(result.getItemAt(0)).toReturn(item);
        stub(_clipboardManager.getPrimaryClip()).toReturn(result);
        stub(_clipDataService.create("message")).toReturn(result);
        _subject.put("message");

        _subject.onPrimaryClipChanged();

        verify(_clipboardListener, never()).handle(any(Clipboard.class), anyString());
    }
}
