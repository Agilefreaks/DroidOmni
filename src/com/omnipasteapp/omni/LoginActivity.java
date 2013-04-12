package com.omnipasteapp.omni;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.omnipasteapp.omni.core.ClipboardService;

public class LoginActivity extends Activity implements GoogleLoginDialogFragment.GoogleLoginDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @SuppressWarnings("UnusedParameters")
    public void loginClicked(View view) {
        GoogleLoginDialogFragment dialog = new GoogleLoginDialogFragment();
        dialog.setListener(this);
        dialog.show(getFragmentManager(), GoogleLoginDialogFragment.TAG);
    }

    @Override
    public void onAccountSelected(Account account) {
        onSuccess(account.name);
    }

    private void onSuccess(String channel){
        // start the clipboard service
        Intent intent = new Intent(this, ClipboardServiceCommandReceiver.class);
        intent.putExtra(ClipboardService.CHANNEL_NAME, channel);
        intent.setAction(ClipboardService.START);
        sendBroadcast(intent);

        // navigate to main activity
        Intent activity_intent = new Intent(this, MainActivity.class);
        activity_intent.putExtra(ClipboardService.CHANNEL_NAME, channel);
        startActivity(activity_intent);
    }
}
