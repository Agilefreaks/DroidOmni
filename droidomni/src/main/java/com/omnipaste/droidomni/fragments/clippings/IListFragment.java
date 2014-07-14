package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.omnicommon.dto.ClippingDto;

import rx.Observable;
import rx.Subscription;

public interface IListFragment {
  public int getTitle();

  public Subscription observe(Observable<ClippingDto> observable);
}
