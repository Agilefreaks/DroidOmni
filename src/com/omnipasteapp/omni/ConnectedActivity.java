package com.omnipasteapp.omni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.omnipasteapp.omni.core.ClipboardService;

public class ConnectedActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            stopClipboardService();
        }

        return super.onKeyDown(keyCode, event);
    }


    public void stopClipboardService(){
        Intent intent = new Intent(this, ClipboardService.class);
        stopService(intent);
    }
}