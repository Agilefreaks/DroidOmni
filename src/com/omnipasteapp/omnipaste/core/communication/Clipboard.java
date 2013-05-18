package com.omnipasteapp.omnipaste.core.communication;

public interface Clipboard {
	
	public void put(String str);

	public void setClipboardListener(ClipboardListener listener);
		
	public ClipboardListener getClipboardListener();

}
