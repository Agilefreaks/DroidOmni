package com.omnipasteapp.omni;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class GoogleLoginDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public interface GoogleLoginDialogListener {
        public void onAccountSelected(Account account);
    }

    public static final String TAG = "GoogleLoginDialogFragment";

    private GoogleLoginDialogListener _listener;
    private Account[] _accounts;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        _accounts = getAccounts();

        if(activity instanceof GoogleLoginDialogListener){
            _listener = (GoogleLoginDialogListener) activity;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        final int size = _accounts.length;
        String[] names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = _accounts[i].name;
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle("Select a Google account")
                .setItems(names, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(_listener != null){
            _listener.onAccountSelected(_accounts[i]);
        }
    }

    private Account[] getAccounts(){
        AccountManager accountManager = AccountManager.get(getActivity().getApplicationContext());

        final Account[] accounts = accountManager.getAccountsByType("com.google");

        return accounts;
    }
}
