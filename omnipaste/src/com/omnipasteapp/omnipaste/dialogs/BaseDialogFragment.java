package com.omnipasteapp.omnipaste.dialogs;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockDialogFragment;
import com.google.inject.Inject;
import com.omnipasteapp.omnipaste.services.IIntentService;

public class BaseDialogFragment extends RoboSherlockDialogFragment {

  @Inject
  protected IIntentService intentService;

}
