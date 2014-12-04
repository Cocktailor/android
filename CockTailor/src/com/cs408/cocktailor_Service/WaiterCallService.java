package com.cs408.cocktailor_Service;

import com.cs408.cocktailor_Activity.CallAlertActivity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.app.PendingIntent.CanceledException;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.cs408.cocktailor_Activity.NFCWaiterPopupActivity;

/**
 * 서비스 순서 onCreate() → onStartCommand() → Service Running → onDestroy()
 */
public class WaiterCallService extends Service {

	@Override
	public void onCreate() {
//		Toast.makeText(this, "Waiter Service is created", 1).show();
		BroadcastReceiver mReceiver = new CustomerFindReceiver();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		super.onCreate();

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		Toast.makeText(this, "서비스 onStartCommand", 1).show();

		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		adapter.enable();
	    
		Intent waiterPopupIntent = new Intent(getApplicationContext(), NFCWaiterPopupActivity.class);

		Bundle bun = new Bundle();
		waiterPopupIntent.putExtras(bun);
		PendingIntent pie = PendingIntent.getActivity(getApplicationContext(), 0, waiterPopupIntent, PendingIntent.FLAG_ONE_SHOT);
		try {
			pie.send();
		} catch (CanceledException e) {
			
		}

		return START_STICKY;
		// return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		adapter.disable();
		

		
		BroadcastReceiver mReceiver = new CustomerFindReceiver();
		unregisterReceiver(mReceiver);
		Toast.makeText(this, "서비스 onDestro", 1).show();
	}
	
	
}
