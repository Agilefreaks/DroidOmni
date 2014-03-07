package com.omnipaste.droidomni.controllers;

import android.os.Bundle;

import com.omnipaste.droidomni.fragments.ClippingsFragment;

public interface ClippingsFragmentController {
  public void run(ClippingsFragment clippingsFragment, Bundle savedInstance);

  void stop();

  void afterView();
}
