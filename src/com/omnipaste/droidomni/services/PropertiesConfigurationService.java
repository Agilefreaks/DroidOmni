package com.omnipaste.droidomni.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfigurationService implements ConfigurationService {

	private Properties _properties;

	public PropertiesConfigurationService() {
		_properties = new Properties();

		LoadProperties();
	}

	public void LoadProperties() {
		try {
			_properties.load(new FileReader("configuration.properties"));
		} catch (FileNotFoundException e) {
			// TODO Log
		} catch (IOException e) {
			// TODO Log
		}
	}

	@Override
	public String read(String key) {
		return (String) _properties.get(key);
	}

	@Override
	public void write(String key, String value) {
		_properties.put(key, value);
	}

}
