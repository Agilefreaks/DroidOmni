package com.omnipasteapp.pubnubclipboard;

import com.pubnub.api.Pubnub;

public interface IPubNubClientFactory {
    Pubnub create();
}
