package com.omnipasteapp.omni.core.communication;

public interface ClipboardMediator {

    void setCloudClipboard(Clipboard cloudClipboard);

    Clipboard getCloudClipboard();

    void setLocalClipboard(Clipboard localClipboard);

    Clipboard getLocalClipboard();

}
