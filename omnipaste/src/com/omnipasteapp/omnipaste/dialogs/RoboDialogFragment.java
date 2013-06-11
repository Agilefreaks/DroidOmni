package com.omnipasteapp.omnipaste.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import com.google.inject.Inject;
import com.omnipasteapp.omnipaste.services.IIntentService;
import roboguice.RoboGuice;

public class RoboDialogFragment extends DialogFragment {

  @Inject
  protected IIntentService intentService;

  @Override
  public void onAttach(Activity activity){
    super.onAttach(activity);

    RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
  }
}
