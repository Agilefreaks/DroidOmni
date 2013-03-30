package com.omnipaste.droidomni.core.communication.impl.pubnub;

import java.util.Hashtable;

public class PubNubMessageBuilder {

	private Hashtable<String, String> _message;
	
	public PubNubMessageBuilder() {
		InitializeMessage();
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
		
		InitializeMessage();
		
		return result; 
	}
	
	public void InitializeMessage(){
		_message = new Hashtable<String, String>();
	}
}
