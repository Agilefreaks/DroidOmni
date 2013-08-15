package com.omnipasteapp.omniclipboard.messaging;

import java.util.Hashtable;

public interface IPubNubMessageBuilder {
    IPubNubMessageBuilder setChannel(String channel);

    IPubNubMessageBuilder addValue(String value);

    Hashtable<String, String> build();
}
