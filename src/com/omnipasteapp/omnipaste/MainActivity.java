package com.omnipasteapp.omnipaste;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.omnipasteapp.omnipaste.core.ClipboardService;

public class MainActivity extends Activity implements LogoutDialogFragment.LogoutDialogListener {

    private SharedPreferences _clipboardServicePreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _clipboardServicePreferences = getSharedPreferences(ClipboardService.TAG, MODE_PRIVATE);

        // get email from intent
        Intent intent = getIntent();
        String email = intent.getStringExtra(ClipboardService.CHANNEL_NAME);

        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.status_text_view);
        textView.setText(String.format("Welcome %s", email));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                DialogFragment dialog = new LogoutDialogFragment();
                dialog.show(getFragmentManager(), LogoutDialogFragment.TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void stopClipboardService(){
        Intent intent = new Intent(this, ClipboardServiceCommandReceiver.class);
        intent.setAction(ClipboardService.STOP);
        sendBroadcast(intent);
    }

    private void logOut(){
        SharedPreferences.Editor editor = _clipboardServicePreferences.edit();

        editor.remove(ClipboardService.CHANNEL_NAME);

        editor.commit();
    }

    @Override
    public void onOk(boolean logout) {
        stopClipboardService();
        if(logout){
            logOut();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onCancel() {
    }
}