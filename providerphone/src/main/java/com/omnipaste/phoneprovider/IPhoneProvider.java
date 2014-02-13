package com.omnipaste.phoneprovider;

import android.content.Context;

import rx.Observable;

public interface IPhoneProvider {
  Observable subscribe(Context context);

  void unsubscribe();
}
