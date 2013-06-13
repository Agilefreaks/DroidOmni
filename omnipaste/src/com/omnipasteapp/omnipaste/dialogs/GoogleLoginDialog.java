package com.omnipasteapp.omnipaste.dialogs;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.BackgroundService;
import com.omnipasteapp.omnipaste.MainActivity;
import com.omnipasteapp.omnipaste.R;

import java.util.ArrayList;

public class GoogleLoginDialog extends RoboDialogFragment implements DialogInterface.OnClickListener {

  public static final String TAG = "GoogleLoginDialogFragment";

  private Account[] _accounts;

  @Inject
  private AccountManager accountManager;

  @Inject
  private IConfigurationService configurationService;

  public static GoogleLoginDialog create() {
    return new GoogleLoginDialog();
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstance) {
    Dialog dialog = new AlertDialog.Builder(getActivity())
        .setTitle(R.string.google_login_title)
        .setSingleChoiceItems(createAccountAdapter(accounts()), 1, this)
        .setNegativeButton(R.string.cancel, this)
        .create();

    return dialog;
  }

  @Override
  public void onClick(DialogInterface dialogInterface, int i) {
    if (i > -1 && i < accounts().length) {
      login(accounts()[i]);
    }
  }

  public ArrayAdapter<String> createAccountAdapter(Account[] accounts) {
    ArrayList<String> names = new ArrayList<String>();
    for(Account account: accounts) {
      names.add(account.name);
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_list_item_1,
        names);

    return adapter;
  }

  public void login(Account account) {
    saveConfiguration(account);

    intentService.startService(BackgroundService.class);
    intentService.startActivity(MainActivity.class);
  }

  public Account[] accounts() {
    if (_accounts == null) {
      _accounts = accountManager.getAccountsByType("com.google");
    }

    return _accounts;
  }

  private void saveConfiguration(Account account) {
    CommunicationSettings settings = configurationService.getCommunicationSettings();
    settings.setChannel(account.name);
    configurationService.updateCommunicationSettings();
  }
}
