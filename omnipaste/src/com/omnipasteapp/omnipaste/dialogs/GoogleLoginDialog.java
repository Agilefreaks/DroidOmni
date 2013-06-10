package com.omnipasteapp.omnipaste.dialogs;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.omnipasteapp.omnipaste.R;

public class GoogleLoginDialog extends DialogFragment implements DialogInterface.OnClickListener {

  public static final String TAG = "GoogleLoginDialogFragment";

  public interface Listener {
    public void onAccountSelected(Account account);
  }

  private Listener _listener;
  private Account[] _accounts;

  @Override
  public void onAttach(Activity activity){
    super.onAttach(activity);

    _accounts = getAccounts();

    if(activity instanceof Listener){
      _listener = (Listener) activity;
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
            .setTitle(getText(R.string.google_login_title))
            .setItems(names, this)
            .setCancelable(true)
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
