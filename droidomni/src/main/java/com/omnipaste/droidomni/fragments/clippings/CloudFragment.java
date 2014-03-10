package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.EFragment;

import rx.Observable;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

@EFragment
public class CloudFragment extends ClippingsListFragment {
  @Override
  public void observer(Observable<ClippingDto> observable) {
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
