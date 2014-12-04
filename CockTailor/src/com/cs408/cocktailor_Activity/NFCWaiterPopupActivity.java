package com.cs408.cocktailor_Activity;

import com.cs408.R;
import com.cs408.cocktailor_Service.CustomerFindReceiver;
import com.google.android.gcm.GCMRegistrar;
import android.view.LayoutInflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(NFCWaiterPopupActivity.this);
	    LayoutInflater inflater = NFCWaiterPopupActivity.this.getLayoutInflater();
	    final View view = inflater.inflate(R.layout.nfcwaiter_popup, null);
	    builder.setView(view)
	    // Add action buttons
	           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        	   @Override
	        	   public void onClick(DialogInterface dialog, int id) {
	        			EditText editt = (EditText)view.findViewById(R.id.nfc_waiter_name);
	        		   	String waiter_name = editt.getText().toString();
						Log.d("hun", editt.getText().toString());
			   			prefs = getSharedPreferences("waiter", Activity.MODE_PRIVATE);
						Editor edit = prefs.edit();
						edit.putString("waiter_name", waiter_name);
						edit.putString("restaurant_id", "1");
						edit.commit();

						registerGcm();
						AlertDialog.Builder buildera = new AlertDialog.Builder(NFCWaiterPopupActivity.this);
						buildera.setMessage("Registered to WAIGENT!")
	                          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                              public void onClick(DialogInterface dialog, int id) {
	                            	  finish();
	                              }
	                          });
		           	   	AlertDialog dialoga = buildera.create();
		           	   	dialoga.show();
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   finish();
	               }
	           });      

	    AlertDialog dialog = builder.create();
	    dialog.show();
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