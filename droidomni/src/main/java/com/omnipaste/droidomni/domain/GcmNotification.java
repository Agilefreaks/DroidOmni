package com.omnipaste.droidomni.domain;

import android.os.Bundle;

import com.omnipaste.omnicommon.dto.NotificationDto;

import org.json.JSONException;
import org.json.JSONObject;

public class GcmNotification {
  public static String PAYLOAD_KEY = "payload";
  public static String ID_KEY = "id";
  public static String TYPE_KEY = "type";

  private NotificationDto.Type type;
  private String id;

  public GcmNotification(Bundle extras) {
    try {
      this.type = NotificationDto.Type.parse(extras.getString(TYPE_KEY, ""));
      this.id = new JSONObject(extras.getString(PAYLOAD_KEY, "")).getString(ID_KEY);
    } catch (JSONException ignore) {
    }
  }

  public NotificationDto.Type getType() {
    return type;
  }

  public String getId() {
    return id;
  }
}
