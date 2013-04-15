package com.omnipasteapp.omni.core.communication.impl.local;

import android.content.ClipData;
import android.content.ClipboardManager;
import com.omnipasteapp.omni.core.communication.Clipboard;
import com.omnipasteapp.omni.core.communication.ClipboardListener;
import com.omnipasteapp.omni.core.communication.impl.local.services.ClipDataService;

public class SystemClipboard implements Clipboard, ClipboardManager.OnPrimaryClipChangedListener {

    private ClipDataService _clipDataService;
    private ClipboardManager _clipboardManager;
    private ClipboardListener _clipboardListener;
    private String _previouslySentMessage;

    public SystemClipboard(ClipboardManager clipboardManager){
        _clipboardManager = clipboardManager;
        _clipDataService = new ClipDataService();
        clipboardManager.addPrimaryClipChangedListener(this);
    }

    @Override
    public void put(String str) {
        _previouslySentMessage = str;
        ClipData clipData = _clipDataService.create(str);
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

    public void setClipDataService(ClipDataService clipDataService){
        _clipDataService = clipDataService;
    }

    @Override
    public void onPrimaryClipChanged() {
        onReceived();
    }

    private void onReceived(){
        String message = _clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
        if(_clipboardListener != null && !message.equals(_previouslySentMessage)){
            _clipboardListener.handle(this, message);
            _previouslySentMessage = message;
        }
    }
}
