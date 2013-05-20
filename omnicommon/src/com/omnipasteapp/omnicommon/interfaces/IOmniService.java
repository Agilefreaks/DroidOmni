package com.omnipasteapp.omnicommon.interfaces;

public interface IOmniService {
    ILocalClipboard getLocalClipboard();

    IOmniClipboard getOmniClipboard();

    void start();

    void stop();
}
