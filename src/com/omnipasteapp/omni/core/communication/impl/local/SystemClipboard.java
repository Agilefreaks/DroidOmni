package com.omnipasteapp.omni.core.communication.impl.local;

import android.content.ClipData;
import android.content.ClipboardManager;
import com.omnipasteapp.omni.core.communication.Clipboard;
import com.omnipasteapp.omni.core.communication.ClipboardListener;

public class SystemClipboard implements Clipboard, ClipboardManager.OnPrimaryClipChangedListener {

    private ClipboardManager _clipboardManager;
    private ClipboardListener _clipboardListener;

    public SystemClipboard(ClipboardManager clipboardManager){
        _clipboardManager = clipboardManager;
        clipboardManager.addPrimaryClipChangedListener(this);
    }

    @Override
    public void put(String str) {
        ClipData clipData = ClipData.newPlainText("", str);

        _clipboardManager.setPrimaryClip(clipData);
    }

    @Override
    public void setClipboardListener(ClipboardListener clipboardListener) {
        _clipboardListener = clipboardListener;
    }

    @Override
    public ClipboardListener getClipboardListener() {
        return _clipboardListener;
    }

    @Override
    public void onPrimaryClipChanged() {
        onReceived();
    }

    private void onReceived(){
        if(_clipboardListener != null){
            _clipboardListener.handle(this, _clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
        }
    }
}
