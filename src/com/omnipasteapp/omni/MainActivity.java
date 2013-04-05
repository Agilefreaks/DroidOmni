package com.omnipasteapp.omni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import com.omnipasteapp.omni.core.ClipboardService;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void startClipboardService(String channelName){
        Intent intent = new Intent(this, ClipboardService.class);
        intent.putExtra(ClipboardService.CHANNEL_NAME, channelName);
        startService(intent);
    }

    public void stopClipboardService(){
        Intent intent = new Intent(this, ClipboardService.class);
        stopService(intent);
    }
}
