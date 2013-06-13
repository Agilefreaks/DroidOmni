package com.omnipasteapp.omnipaste.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.BackgroundService;
import com.omnipasteapp.omnipaste.LoginActivity;
import com.omnipasteapp.omnipaste.R;

public class LogoutDialog extends RoboDialogFragment implements DialogInterface.OnClickListener {

  public static final String TAG = "LogoutDialogFragment";

  public static LogoutDialog create() {
    return new LogoutDialog();
  }

  @Inject
  private IConfigurationService configurationService;

  @Inject
  private Context context;

  @Override
  public Dialog onCreateDialog(Bundle savedInstance) {
    return new AlertDialog.Builder(getActivity())
        .setTitle(R.string.logout)
        .setView(View.inflate(getActivity(), R.layout.dialog_logout, null))
        .setPositiveButton(R.string.ok, this)
        .setNegativeButton(R.string.cancel, null)
        .create();
  }

  @Override
  public void onClick(DialogInterface dialog, int i) {
    logout();

    intentService.stopService(BackgroundService.class);
    intentService.startClearActivity(LoginActivity.class);
  }

  public void logout() {
    // Reach inside the class change the it's state and then call methods on it, puppet master
    CommunicationSettings communicationSettings = configurationService.getCommunicationSettings();
    communicationSettings.setChannel(null);
    configurationService.updateCommunicationSettings();
  }
}
