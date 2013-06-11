package com.omnipasteapp.omnipaste.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import roboguice.RoboGuice;

public class RoboDialogFragment extends DialogFragment {

  @Override
  public void onAttach(Activity activity){
    super.onAttach(activity);

    RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
  }
}
