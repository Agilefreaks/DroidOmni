package com.omnipaste.omnicommon;

import rx.Observable;

public interface Provider<T> {
  public Observable<T> init(final String identifier);

  public void destroy();
}
