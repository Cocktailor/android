package com.cs408.cocktailor_Activity;

import com.cs408.R;
import com.cs408.cocktailor_Service.CustomerFindReceiver;
import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class NFCWaiterPopupActivity extends Activity{

private SharedPreferences prefs;
	
	public void registerGcm() {
	
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
	
		final String regId = GCMRegistrar.getRegistrationId(this);
	
		if (regId.equals("")) {
			GCMRegistrar.register(this, "1026088236239");
		} else {
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("my", "NFC Waiter Popup On Create");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.nfcwaiter_popup);
		Button cancel_button = (Button)findViewById(R.id.CancelButton);
		Button okay_button = (Button)findViewById(R.id.OkayButton);
		final EditText editt = (EditText)findViewById(R.id.NFCWaiterEditText);
		
		okay_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				String waiter_name = editt.getText().toString();
				prefs = getSharedPreferences("waiter", Activity.MODE_PRIVATE);
				Editor edit = prefs.edit();
				edit.putString("waiter_name", waiter_name);
				edit.commit();

				registerGcm();
				BroadcastReceiver mReceiver = new CustomerFindReceiver();
				IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
				registerReceiver(mReceiver, filter);

				finish();
			}
		});

		cancel_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}