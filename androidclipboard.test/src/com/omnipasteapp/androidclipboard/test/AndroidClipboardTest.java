package com.omnipasteapp.androidclipboard.test;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import com.omnipasteapp.androidclipboard.AndroidClipboard;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class AndroidClipboardTest {
    private Context mockContext;
    private AndroidClipboard subject;

    @Before
    public void setUp() {
        mockContext = mock(Context.class);
        subject = new AndroidClipboard();
        subject.setContext(mockContext);
    }

    @Test
    public void initializeReturnsNewThread(){
        Thread result = subject.initialize();

        Assert.assertNotNull(result);
    }

    @Test
    public void runCallsContextGetClibpoardService(){
        ClipboardManager mockClipboardManager = mock(ClipboardManager.class);
        when(mockContext.getSystemService(eq(Context.CLIPBOARD_SERVICE)))
                .thenReturn(mockClipboardManager);

        subject.run();

        verify(mockContext).getSystemService(eq(Context.CLIPBOARD_SERVICE));
    }

    @Test
    public void runCallsAddPrimaryClipChangedListener(){
        ClipboardManager mockClipboardManager = mock(ClipboardManager.class);
        when(mockContext.getSystemService(eq(Context.CLIPBOARD_SERVICE)))
                .thenReturn(mockClipboardManager);

        subject.run();

        verify(mockClipboardManager).addPrimaryClipChangedListener(eq(subject));
    }

    @Test
    public void disposeCallsRemovePrimaryClipChangedListener(){
        ClipboardManager mockClipboardManager = mock(ClipboardManager.class);
        when(mockContext.getSystemService(eq(Context.CLIPBOARD_SERVICE)))
                .thenReturn(mockClipboardManager);

        subject.dispose();

        verify(mockClipboardManager).removePrimaryClipChangedListener(eq(subject));
    }

    @Test
    public void onPrimaryClipChangedCallsReceiverDataReceived(){
        ClipData mockClipData = mock(ClipData.class);
        when(mockClipData.getItemCount()).thenReturn(1);
        when(mockClipData.getItemAt(eq(0))).thenReturn(new ClipData.Item("test"));
        ClipboardManager mockClipboardManager = mock(ClipboardManager.class);
        when(mockClipboardManager.hasPrimaryClip()).thenReturn(true);
        when(mockClipboardManager.getPrimaryClip()).thenReturn(mockClipData);
        when(mockContext.getSystemService(eq(Context.CLIPBOARD_SERVICE)))
                .thenReturn(mockClipboardManager);
        ICanReceiveData mockDataReceiver = mock(ICanReceiveData.class);
        subject.addDataReceiver(mockDataReceiver);

        subject.onPrimaryClipChanged();

        verify(mockDataReceiver).dataReceived(any(IClipboardData.class));
    }

    @Test
    public void putDataAlwaysCallsClipboardManagerSetPrimaryClip(){
        ClipboardManager mockClipboardManager = mock(ClipboardManager.class);
        when(mockContext.getSystemService(eq(Context.CLIPBOARD_SERVICE)))
                .thenReturn(mockClipboardManager);

        subject.putData("test");

        verify(mockClipboardManager).setPrimaryClip(any(ClipData.class));
    }
}
