package com.omnipaste.droidomni.services;

public interface ConfigurationService {

	String read(String key);
	
	void write(String key, String value);
	
}
