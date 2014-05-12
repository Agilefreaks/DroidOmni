package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.EFragment;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

@EFragment
public class CloudFragment extends ClippingsListFragment {
  public CloudFragment() {
    super();
  }

  @Override
  public void observe(Observable<ClippingDto> observable) {
    observable.filter(new Func1<ClippingDto, Boolean>() {
      @Override
      public Boolean call(ClippingDto clippingDto) {
        return clippingDto.getClippingProvider() == ClippingDto.ClippingProvider.cloud;
      }
    }).subscribe(new Action1<ClippingDto>() {
      @Override
      public void call(ClippingDto clippingDto) {
        add(clippingDto);
      }
    });
  }
}
