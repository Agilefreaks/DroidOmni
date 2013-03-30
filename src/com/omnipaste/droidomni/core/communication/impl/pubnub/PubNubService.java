package com.omnipaste.droidomni.core.communication.impl.pubnub;

import com.omnipaste.droidomni.core.communication.CloudClipboard;
import com.omnipaste.droidomni.core.communication.CloudMessageListener;
import com.omnipaste.droidomni.services.ConfigurationService;
import com.omnipaste.droidomni.services.PropertiesConfigurationService;
import com.pubnub.api.Pubnub;

public class PubNubService implements CloudClipboard {

	private String _channel;
	private CloudMessageListener _cloudMessageListener;
	private PubNubMessageBuilder _pubNubMessageBuilder;
	private Pubnub _pubNub;

	public PubNubService(String channel) {
		_channel = channel;
		_pubNubMessageBuilder = new PubNubMessageBuilder();

		InitConnection();
	}

	public void InitConnection() {
		ConfigurationService config = new PropertiesConfigurationService();
		_pubNub = new Pubnub(config.read("publishkey"),
				config.read("subscribekey"), config.read("securitykey"));
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

	public void onReceived(String message) {
		if (_cloudMessageListener != null) {
			_cloudMessageListener.handle(message);
		}
	}

}
