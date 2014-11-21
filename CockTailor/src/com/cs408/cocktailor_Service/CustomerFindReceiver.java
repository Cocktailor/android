package com.cs408.cocktailor_Service;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CustomerFindReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		String action = intent.getAction();
		if (BluetoothDevice.ACTION_FOUND.equals(action)) 
	    {
	        // Get the BluetoothDevice object from the Intent
	        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	        short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,  Short.MIN_VALUE);
	        // Add the name and address to an array adapter to show in a ListView
	       //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	       Log.d("customer",device.getName() + "\n" + device.getAddress());
	       Log.d("customer","Signal = " + Short.toString(rssi));
	       if(device.getAddress().equals("8C:3A:E3:EE:BA:FE")){
	    	   Log.i("my","customer find!!  Signal = " + Short.toString(rssi));
	       }
	       
	    }
		
	}

}
