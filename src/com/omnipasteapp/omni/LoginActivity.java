package com.omnipasteapp.omni;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.omnipasteapp.omni.core.ClipboardService;

public class LoginActivity extends Activity implements GoogleLoginDialogFragment.GoogleLoginDialogListener {

    private SharedPreferences _clipboardServicePreferences;

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
        saveChannel(account.name);
        startService(account.name);
    }

    private void saveChannel(String channel){
        SharedPreferences.Editor editor = _clipboardServicePreferences.edit();

        editor.putString(ClipboardService.CHANNEL_NAME, channel);

        editor.commit();
    }

    private void startService(String channel){
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
