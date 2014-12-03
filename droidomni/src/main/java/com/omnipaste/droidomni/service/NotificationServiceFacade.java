package com.omnipaste.droidomni.service;

import com.omnipaste.droidomni.service.notification.NotificationServiceClippings;
import com.omnipaste.droidomni.service.notification.NotificationServiceEvents;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationServiceFacade extends ServiceBase {
  private NotificationServiceClippings notificationServiceClippings;
  private NotificationServiceEvents notificationServiceEvents;

  @Inject
  public NotificationServiceFacade(NotificationServiceClippings notificationServiceClippings, NotificationServiceEvents notificationServiceEvents) {
    this.notificationServiceClippings = notificationServiceClippings;
    this.notificationServiceEvents = notificationServiceEvents;
  }

  public void start() {
    notificationServiceClippings.start();
    notificationServiceEvents.start();
  }

  public void stop() {
    notificationServiceClippings.stop();
    notificationServiceEvents.stop();
  }
}
