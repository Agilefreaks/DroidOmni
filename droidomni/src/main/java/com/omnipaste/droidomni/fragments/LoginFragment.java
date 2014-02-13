package com.omnipaste.droidomni.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.AccountAdapter;
import com.omnipaste.droidomni.events.LoginEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {
  private EventBus eventBus = EventBus.getDefault();
  private AccountAdapter accountsAdapter;

  @ViewById
  public ListView accounts;

  @SystemService
  public AccountManager accountManager;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);

    accountsAdapter = new AccountAdapter(accountManager.getAccountsByType("com.google"));
  }

  @AfterViews
  public void afterView() {
    if (accounts.getAdapter() == null) {
      accounts.setAdapter(accountsAdapter);
    }
  }

  @ItemClick
  public void accountsItemClicked(Account account) {
    eventBus.post(new LoginEvent(account.name));
  }
}
