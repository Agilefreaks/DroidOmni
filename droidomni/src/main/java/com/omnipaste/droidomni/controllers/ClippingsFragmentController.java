package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.fragments.clippings.ClippingsFragment;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.Collection;

import rx.Observable;

public interface ClippingsFragmentController {
  public void run(ClippingsFragment clippingsFragment, Bundle savedInstance);

  void stop();

  void afterView();

  Observable<ClippingDto> getObservable();

  Collection<ClippingDto> getClippings();
}
