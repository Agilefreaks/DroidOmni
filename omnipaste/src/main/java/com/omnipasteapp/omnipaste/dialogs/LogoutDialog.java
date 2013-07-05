package com.omnipasteapp.omnipaste.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.omnipasteapp.omnipaste.R;

public class LogoutDialog extends DialogFragment {
  public static final String TAG = "LogoutDialogFragment";

  public static LogoutDialog create() {
    return new LogoutDialog();
  }

  public interface LogoutDialogListener {
    public void onDialogPositiveClick(DialogFragment dialog);

    public void onDialogNegativeClick(DialogFragment dialog);
  }

  private LogoutDialogListener listener;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    try {
      listener = (LogoutDialogListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
    }
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstance) {
    return new AlertDialog.Builder(getActivity())
        .setTitle(R.string.action_logout)
        .setView(View.inflate(getActivity(), R.layout.dialog_logout, null))
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            listener.onDialogPositiveClick(LogoutDialog.this);
          }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            listener.onDialogNegativeClick(LogoutDialog.this);
          }
        })
        .create();
  }
}
