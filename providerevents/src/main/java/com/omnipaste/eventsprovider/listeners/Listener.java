package com.omnipaste.eventsprovider.listeners;

public interface Listener {
  public void start(EventsReceiver receiver);

  public void stop();
}
