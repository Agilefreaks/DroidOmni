package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.EFragment;

import rx.Observable;
import rx.functions.Action1;

@EFragment
public class AllFragment extends ClippingsListFragment {
  @Override
  public void observe(Observable<ClippingDto> observable) {
    observable
        .subscribe(new Action1<ClippingDto>() {
          @Override
          public void call(ClippingDto clippingDto) {
            add(clippingDto);
          }
        });
  }
}
