package com.omnipaste.droidomni.fragments.clippings;

import com.omnipaste.droidomni.R;
import com.omnipaste.omnicommon.dto.ClippingDto;

import org.androidannotations.annotations.EFragment;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

@EFragment
public class AllFragment extends ClippingsListFragment {
  public AllFragment() {
    super();
  }

  public int getTitle() {
    return R.string.clippings_tab_all;
  }

  public Subscription observe(Observable<ClippingDto> observable) {
    return observable
        .subscribe(new Action1<ClippingDto>() {
          @Override
          public void call(ClippingDto clippingDto) {
            add(clippingDto);
          }
        });
  }
}
