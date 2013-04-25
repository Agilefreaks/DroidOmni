package com.omnipasteapp.omni;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class LogoutDialogFragment extends DialogFragment {

    private LogoutDialogListener _listener;

    public interface LogoutDialogListener{
        void onOk(boolean logout);
        void onCancel();
    }

    public static final String TAG = "Logout";

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        if(activity instanceof LogoutDialogListener){
            _listener = (LogoutDialogListener) activity;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){

        return new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setView(View.inflate(getActivity().getBaseContext(), R.layout.dialog_logout, null))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CheckBox checkBox = (CheckBox)((AlertDialog)dialog).findViewById(R.id.checkbox_logout);
                        _listener.onOk(checkBox.isChecked());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        _listener.onCancel();
                    }
                })
                .create();
    }
}
