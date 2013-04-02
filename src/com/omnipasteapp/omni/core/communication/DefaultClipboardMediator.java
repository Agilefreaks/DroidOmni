package com.omnipasteapp.omni.core.communication;

public class DefaultClipboardMediator implements ClipboardMediator, ClipboardListener {

    private Clipboard _cloudClipboard;
    private Clipboard _localClipboard;

    @Override
    public void setCloudClipboard(Clipboard cloudClipboard) {
        if(_cloudClipboard != null){
            _cloudClipboard.setClipboardListener(null);
        }
        _cloudClipboard = cloudClipboard;
        _cloudClipboard.setClipboardListener(this);
    }

    @Override
    public Clipboard getCloudClipboard() {
        return _cloudClipboard;
    }

    @Override
    public void setLocalClipboard(Clipboard localClipboard) {
        if(_localClipboard != null){
            _localClipboard.setClipboardListener(null);
        }

        _localClipboard = localClipboard;
        _localClipboard.setClipboardListener(this);
    }

    @Override
    public Clipboard getLocalClipboard() {
        return _localClipboard;
    }

    @Override
    public void handle(Clipboard sender, String message) {
        if(sender == _localClipboard && _cloudClipboard != null){
            _cloudClipboard.put(message);
        }
        if(sender == _cloudClipboard && _localClipboard != null){
            _localClipboard.put(message);
        }
    }
}
