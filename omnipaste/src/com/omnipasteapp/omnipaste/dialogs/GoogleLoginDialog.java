package com.omnipasteapp.omnipaste.dialogs;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.google.inject.Inject;
import com.omnipasteapp.omnipaste.R;

public class GoogleLoginDialog extends RoboDialogFragment implements DialogInterface.OnClickListener  {

  public interface Listener {
    public void onAccountSelected(Account account);
  }

  public static final String TAG = "GoogleLoginDialogFragment";

  private Account[] _accounts;
  private Listener _listener;

  @Inject
  private AccountManager accountManager;

  public static GoogleLoginDialog create() {
    return new GoogleLoginDialog();
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstance){
    initAccounts();

    Dialog dialog = new AlertDialog.Builder(getActivity())
            .setTitle(R.string.google_login_title)
            .setSingleChoiceItems(createAccountAdapter(_accounts), 1, this)
            .setNegativeButton(R.string.cancel, this)
            .create();

    return dialog;
  }

  @Override
  public void onClick(DialogInterface dialogInterface, int i) {
    if(_listener != null && i > -1 && i < _accounts.length){
      _listener.onAccountSelected(_accounts[i]);
    }
  }

  public GoogleLoginDialog setListener(Listener listener){
    _listener = listener;

    return this;
  }

  public void initAccounts(){
    _accounts = accountManager.getAccountsByType("com.google");
  }

  public ArrayAdapter<String> createAccountAdapter(Account[] accounts){
    final int size = accounts.length;
    String[] names = new String[size];
    for (int i = 0; i < size; i++) {
      names[i] = accounts[i].name;
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
            android.R.layout.simple_list_item_1,
            names);

    return adapter;
  }
}
