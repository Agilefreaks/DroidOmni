package com.omnipaste.omnicommon.domain;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

public class Configuration {
  private String gcmSenderId, apiUrl, apiClientId;
  private AccessTokenDto accessToken;
  private Boolean notificationsClipboard, notificationsTelephony, notificationsPhone, notificationsGcmWorkaround;

  public Configuration() {
  }

  public boolean hasAccessToken() {
    return getAccessToken() != null;
  }

  public String getGcmSenderId() {
    return gcmSenderId;
  }

  public void setGcmSenderId(String value) {
    gcmSenderId = value;
  }

  public String getApiUrl() {
    return apiUrl;
  }

  public void setApiUrl(String value) {
    apiUrl = value;
  }

  public void setApiClientId(String apiClientId) {
    this.apiClientId = apiClientId;
  }

  public String getApiClientId() {
    return apiClientId;
  }

  public void setAccessToken(AccessTokenDto accessToken) {
    this.accessToken = accessToken;
  }

  public AccessTokenDto getAccessToken() {
    return accessToken;
  }

  public Boolean getNotificationsClipboard() {
    return notificationsClipboard;
  }

  public void setNotificationsClipboard(Boolean notificationsClipboard) {
    this.notificationsClipboard = notificationsClipboard;
  }

  public Boolean getNotificationsTelephony() {
    return notificationsTelephony;
  }

  public void setNotificationsTelephony(Boolean notificationsTelephony) {
    this.notificationsTelephony = notificationsTelephony;
  }

  public Boolean getNotificationsPhone() {
    return notificationsPhone;
  }

  public void setNotificationsPhone(Boolean notificationsPhone) {
    this.notificationsPhone = notificationsPhone;
  }

  public Boolean getNotificationsGcmWorkaround() {
    return notificationsGcmWorkaround;
  }

  public void setNotificationsGcmWorkaround(Boolean notificationsGcmWorkaround) {
    this.notificationsGcmWorkaround = notificationsGcmWorkaround;
  }
}
