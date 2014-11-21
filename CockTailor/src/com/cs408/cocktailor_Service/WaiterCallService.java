package com.cs408.cocktailor_Service;

import com.google.android.gcm.GCMRegistrar;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 서비스 순서 onCreate() → onStartCommand() → Service Running → onDestroy()
 */
public class WaiterCallService extends Service {

	
	
	public void registerGcm() {

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		final String regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {
			GCMRegistrar.register(this, "1026088236239");
			// Log.e("my","redID = " + GCMRegistrar.getRegistrationId(this));
		} else {
			Log.e("my", "regId = " + regId);
		}
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "Waiter Service is created", 1).show();
		registerGcm();
		super.onCreate();

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "서비스 onStartCommand", 1).show();

		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		adapter.enable();
		
		adapter.startDiscovery();
		
		BroadcastReceiver mReceiver = new CustomerFindReceiver();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND); 
		registerReceiver(mReceiver, filter);
		
		return START_STICKY;
		// return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "서비스 onDestro", 1).show();
	}
	
	
}
