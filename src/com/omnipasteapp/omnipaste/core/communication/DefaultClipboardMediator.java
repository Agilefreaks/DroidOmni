package com.omnipasteapp.omnipaste.core.communication;

public class DefaultClipboardMediator implements ClipboardMediator, ClipboardListener {

    private Clipboard _remoteClipboard;
    private Clipboard _localClipboard;

    @Override
    public void setRemoteClipboard(Clipboard remoteClipboard) {
        if(_remoteClipboard != null){
            _remoteClipboard.setClipboardListener(null);
        }
        _remoteClipboard = remoteClipboard;

        if(_remoteClipboard != null){
            _remoteClipboard.setClipboardListener(this);
        }
    }

    @Override
    public Clipboard getRemoteClipboard() {
        return _remoteClipboard;
    }

    @Override
    public void setLocalClipboard(Clipboard localClipboard) {
        if(_localClipboard != null){
            _localClipboard.setClipboardListener(null);
        }

        _localClipboard = localClipboard;

        if(_localClipboard != null){
            _localClipboard.setClipboardListener(this);
        }
    }

    @Override
    public Clipboard getLocalClipboard() {
        return _localClipboard;
    }

    @Override
    public void handle(Clipboard sender, String message) {
        if(sender == _localClipboard && _remoteClipboard != null){
            _remoteClipboard.put(message);
        }
        if(sender == _remoteClipboard && _localClipboard != null){
            _localClipboard.put(message);
        }
    }
}
