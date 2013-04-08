package com.omnipasteapp.omni.core.communication.impl.cloud.pubnub;

import com.omnipasteapp.omni.core.communication.ClipboardListener;
import com.omnipasteapp.omni.core.communication.RemoteClipboard;
import com.omnipasteapp.omni.services.ConfigurationService;
import com.omnipasteapp.omni.services.PropertiesConfigurationService;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

import java.util.Hashtable;

public class PubNubService extends Callback implements RemoteClipboard {

	private String _channel;
	private ClipboardListener _cloudMessageListener;
	private PubNubMessageBuilder _pubNubMessageBuilder;
	private Pubnub _pubNub;
    private String _previouslySentMessage;

	public PubNubService(String channel) {
		_channel = channel;
		_pubNubMessageBuilder = new PubNubMessageBuilder();

		InitConnection();
		Subscribe();
	}

	public void InitConnection() {
		ConfigurationService config = new  PropertiesConfigurationService();
		_pubNub = new Pubnub(config.read("publishkey"),
				config.read("subscribekey"), config.read("securitykey"));
	}
	
	public void Subscribe(){
		Hashtable<String, String> table = new Hashtable<String, String>();
		table.put("channel", _channel);
		
		try {
			_pubNub.subscribe(table, this);
		} catch (PubnubException e) {
			// TODO Log exception
		}
	}

	@Override
	public void put(String str) {
        _previouslySentMessage = str;
		_pubNub.publish(_pubNubMessageBuilder
				.setChannel(_channel)
				.addValue(str)
				.build(), new MessageSentCallback());
	}

	@Override
	public void setClipboardListener(ClipboardListener listener) {
		_cloudMessageListener = listener;
	}

	@Override
	public ClipboardListener getClipboardListener() {
		return _cloudMessageListener;
	}

    @Override
    public void disconnect() {
        _pubNub.unsubscribeAll();
    }
	
	@Override
	public void successCallback(String channel, Object message) {
		onReceived(message.toString());
	}

	public void onReceived(String message) {
        if (_cloudMessageListener != null && _previouslySentMessage != null && !_previouslySentMessage.equals(message)) {
			_cloudMessageListener.handle(this, message);
		}
	}
}
