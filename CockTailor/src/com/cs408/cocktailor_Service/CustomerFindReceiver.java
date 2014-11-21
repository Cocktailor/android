package com.cs408.cocktailor_Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

public class CustomerFindReceiver extends BroadcastReceiver {
	private String device_id;
	private SharedPreferences prefs;
	private String response_ble_id;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		String action = intent.getAction();
		if (BluetoothDevice.ACTION_FOUND.equals(action)) {
			// Get the BluetoothDevice object from the Intent
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,
					Short.MIN_VALUE);
			// Add the name and address to an array adapter to show in a
			// ListView
			// mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			Log.d("customer", device.getName() + "\n" + device.getAddress());
			Log.d("customer", "Signal = " + Short.toString(rssi));
			prefs = context.getSharedPreferences("customer",
					Activity.MODE_PRIVATE);
			response_ble_id = prefs.getString("customer_device", "");
			Log.e("my",
					"customer_device = "
							+ response_ble_id);
			Log.e("my", "check = " + prefs.getBoolean("checked", true));
			if (prefs.getString("customer_device", "").equals(device.getAddress())
					&& !prefs.getBoolean("checked", true)) {
				Log.i("my", "customer find!!  Signal = " + Short.toString(rssi));
				Editor edit = prefs.edit();
				edit.putBoolean("checked", true);
				edit.commit();

				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				device_id = adapter.getAddress();
				(new send_signal_strength()).execute(Short.toString(rssi));
			}

		}

	}

	public class send_signal_strength extends
			AsyncTask<String, Void, ArrayList<String>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);

		}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://cs408.kaist.ac.kr:4418/ble_signal";
				HttpPost post = new HttpPost(postURL);

				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("strength", params[0]));
				parameters.add(new BasicNameValuePair("device_id", device_id));
				parameters.add(new BasicNameValuePair("response_ble_id", response_ble_id));

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(parameters,
						HTTP.UTF_8);
				post.setEntity(ent);
				HttpResponse responsePOST = client.execute(post);
				HttpEntity resEntity = responsePOST.getEntity();

				if (resEntity != null) {
					Log.i("RESPONSE", EntityUtils.toString(resEntity));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
