package com.omnipaste.droidomni.controllers;


import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.Collection;

import rx.Observable;

public interface ClippingsFragmentController {
  void stop();

  void afterView();

  Observable<ClippingDto> getObservable();

  Collection<ClippingDto> getClippings();
}
