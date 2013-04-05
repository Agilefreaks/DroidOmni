package com.omnipasteapp.omni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
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

    public void startService(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        startClipboardService(message);
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
