package com.omnipasteapp.omnipaste;

import android.accounts.Account;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.dialogs.GoogleLoginDialog;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActivity implements View.OnClickListener, GoogleLoginDialog.Listener {

  @InjectView(R.id.login_button)
  private Button loginButton;

  @Inject
  private IConfigurationService configurationService;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if(loginButton != null){
      loginButton.setOnClickListener(this);
    }
  }

  @Override
  public void onClick(View view) {
    DialogFragment dialog = new GoogleLoginDialog();
    dialog.show(getFragmentManager(), GoogleLoginDialog.TAG);
  }

  @Override
  public void onAccountSelected(Account account) {
    saveConfiguration(account);

    startActivity(new Intent(this, MainActivity.class));
  }

  private void saveConfiguration(Account account){
    CommunicationSettings settings = configurationService.getCommunicationSettings();
    settings.setChannel(account.name);
    configurationService.updateCommunicationSettings();
  }
}