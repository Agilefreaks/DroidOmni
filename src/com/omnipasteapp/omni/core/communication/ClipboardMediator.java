package com.omnipasteapp.omni.core.communication;

public interface ClipboardMediator {

    void setRemoteClipboard(Clipboard remoteClipboard);

    Clipboard getRemoteClipboard();

    void setLocalClipboard(Clipboard localClipboard);

    Clipboard getLocalClipboard();

}
