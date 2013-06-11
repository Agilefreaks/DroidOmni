package com.omnipasteapp.omnipaste.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import com.omnipasteapp.omnipaste.R;

public class LogoutDialog extends RoboDialogFragment implements DialogInterface.OnClickListener {

  public static final String TAG = "LogoutDialogFragment";

  public static LogoutDialog create(){
    return new LogoutDialog();
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstance){
    Dialog dialog = new AlertDialog.Builder(getActivity())
            .setTitle(R.string.logout)
            .setView(View.inflate(getActivity(), R.layout.dialog_logout, null))
            .setPositiveButton(R.string.ok, this)
            .setNegativeButton(R.string.cancel, null)
            .create();

    return dialog;
  }

  @Override
  public void onClick(DialogInterface dialog, int i) {
    logout();
  }

  public void logout(){

  }
}
