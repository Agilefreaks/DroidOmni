package com.omnipaste.droidomni.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.adapters.AccountAdapter;
import com.omnipaste.droidomni.events.UpdateUI;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment implements AdapterView.OnItemClickListener {
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
    accounts.setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
    Account account = (Account) adapterView.getItemAtPosition(position);

    if (account != null) {
      Configuration configuration = configurationService.getConfiguration();
      configuration.channel = account.name;
      configurationService.setConfiguration(configuration);

      eventBus.post(new UpdateUI());
    }
  }
}
