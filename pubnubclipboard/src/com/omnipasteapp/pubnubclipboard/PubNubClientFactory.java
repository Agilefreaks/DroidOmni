package com.omnipasteapp.pubnubclipboard;

import com.pubnub.api.Pubnub;

public class PubNubClientFactory implements IPubNubClientFactory {
    public static final String PUBLISH_KEY = "pub-c-f6c56076-b928-407d-8e27-462dbf25e722";
    public static final String SUBSCRIBE_KEY = "sub-c-9f339926-9855-11e2-ac20-12313f022c90";
    public static final String SECRET_KEY = "sec-c-Y2FiOTQzYjEtOTE5NC00YTQ0LWI4YzQtYjYzNjhhNTE1ZTYw";

    @Override
    public IPubnub create() {
        return new PubnubWrapper(new Pubnub(PUBLISH_KEY, SUBSCRIBE_KEY, SECRET_KEY));
    }
}
