package com.omnipasteapp.pubnubclipboard;

import java.util.Hashtable;

public class PubNubMessageBuilder implements IPubNubMessageBuilder {

    private Hashtable<String, String> _message;

    public PubNubMessageBuilder() {
        initializeNewMessage();
    }

    public PubNubMessageBuilder setChannel(String channel){
        _message.put("channel", channel);

        return this;
    }

    public PubNubMessageBuilder addValue(String value){
        _message.put("message", value);

        return this;
    }

    public Hashtable<String, String> build(){
        Hashtable<String, String> result = _message;

        initializeNewMessage();

        return result;
    }

    public void initializeNewMessage(){
        _message = new Hashtable<String, String>();
    }
}