package com.omnipasteapp.omnipaste.core.communication;

public interface ClipboardListener {

    void handle(Clipboard sender, String message);

}
