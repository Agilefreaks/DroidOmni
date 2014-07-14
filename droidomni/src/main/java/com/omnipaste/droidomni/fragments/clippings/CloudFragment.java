package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.EFragment;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

@EFragment
public class CloudFragment extends ClippingsListFragment {
  public CloudFragment() {
    super();
  }

  public int getTitle() {
    return R.string.clippings_tab_cloud;
  }

  public Subscription observe(Observable<ClippingDto> observable) {
    return observable.filter(new Func1<ClippingDto, Boolean>() {
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
