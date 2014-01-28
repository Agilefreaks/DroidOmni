package com.omnipaste.phoneprovider;

import android.content.Context;

import rx.Observable;

public interface IPhoneProvider {
  Observable getObservable(Context context);
}
