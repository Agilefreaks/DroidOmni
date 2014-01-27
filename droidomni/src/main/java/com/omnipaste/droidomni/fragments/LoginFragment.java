package com.omnipaste.droidomni.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.AccountAdapter;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {
  private EventBus eventBus = EventBus.getDefault();

  @ViewById
  public ListView accounts;

  @SystemService
  public AccountManager accountManager;

  @Inject
  public ConfigurationService configurationService;

  @AfterViews
  public void afterView() {
    DroidOmniApplication.inject(this);

    accounts.setAdapter(new AccountAdapter(accountManager.getAccountsByType("com.google")));
  }

  @ItemClick
  public void accountsItemClicked(Account account) {
    Configuration configuration = configurationService.getConfiguration();
    configuration.setChannel(account.name);
    configurationService.setConfiguration(configuration);

    eventBus.post(MainFragment_.builder().build());
  }
}
