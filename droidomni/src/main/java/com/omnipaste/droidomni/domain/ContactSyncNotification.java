package com.omnipaste.droidomni.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactSyncNotification implements Parcelable {
  private Status status;
  private Throwable reason;

  public enum Status {
    Started,
    Completed,
    Failed
  }

  public static final Parcelable.Creator<ContactSyncNotification> CREATOR = new Parcelable.Creator<ContactSyncNotification>() {
    public ContactSyncNotification createFromParcel(Parcel pc) {
      return new ContactSyncNotification(pc);
    }

    public ContactSyncNotification[] newArray(int size) {
      return new ContactSyncNotification[size];
    }
  };

  public ContactSyncNotification(Status status) {
    this.status = status;
  }

  public ContactSyncNotification(Status status, Throwable reason) {
    this.status = status;
    this.reason = reason;
  }

  public ContactSyncNotification(Parcel in) {
    this.status = (Status) in.readSerializable();
    this.reason = (Throwable) in.readSerializable();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeSerializable(getStatus());
    parcel.writeSerializable(reason);
  }

  public Status getStatus() {
    return status;
  }

  public Throwable getReason() {
    return reason;
  }
}
