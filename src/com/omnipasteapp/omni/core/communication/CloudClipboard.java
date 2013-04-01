package com.omnipasteapp.omni.core.communication;

public interface CloudClipboard {
	
	public void broadcast(String str);

	public void setMessageListener(CloudMessageListener listener);
		
	public CloudMessageListener getMessageListener();

}
