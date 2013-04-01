package com.omnipasteapp.omni.core.communication.impl.pubnub;

import java.util.Hashtable;

import com.omnipasteapp.omni.core.communication.CloudClipboard;
import com.omnipasteapp.omni.core.communication.CloudMessageListener;
import com.omnipasteapp.omni.services.ConfigurationService;
import com.omnipasteapp.omni.services.PropertiesConfigurationService;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

public class PubNubService extends Callback implements CloudClipboard {

	private String _channel;
	private CloudMessageListener _cloudMessageListener;
	private PubNubMessageBuilder _pubNubMessageBuilder;
	private Pubnub _pubNub;

	public PubNubService(String channel) {
		_channel = channel;
		_pubNubMessageBuilder = new PubNubMessageBuilder();

		InitConnection();
		Subscribe();
	}

	public void InitConnection() {
		ConfigurationService config = new PropertiesConfigurationService();
		_pubNub = new Pubnub(config.read("publishkey"),
				config.read("subscribekey"), config.read("securitykey"));
	}
	
	public void Subscribe(){
		Hashtable<String, String> table = new Hashtable<String,String>();
		table.put("channel", _channel);
		
		try {
			_pubNub.subscribe(table, this);
		} catch (PubnubException e) {
			// TODO Log exception
		}
	}

	@Override
	public void broadcast(String str) {
		_pubNub.publish(_pubNubMessageBuilder
				.setChannel(_channel)
				.addValue(str)
				.build());
	}

	@Override
	public void setMessageListener(CloudMessageListener listener) {
		_cloudMessageListener = listener;
	}

	@Override
	public CloudMessageListener getMessageListener() {
		return _cloudMessageListener;
	}
	
	@Override
	public void successCallback(String channel, Object message) {
		onReceived(message.toString());
	}

	public void onReceived(String message) {
		if (_cloudMessageListener != null) {
			_cloudMessageListener.handle(message);
		}
	}

}
