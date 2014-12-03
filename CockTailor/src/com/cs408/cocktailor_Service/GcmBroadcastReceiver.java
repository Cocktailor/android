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

import com.cs408.cocktailor_Activity.CallAlertActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.TelephonyManager;
import android.util.Log;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

	private String regID;
	private SharedPreferences prefs;
	private String device_id;
	Context Gcontext;

	@Override
	public void onReceive(Context context, Intent intent) {
		Gcontext = context;
		Log.i("GcmBroadcastReceiver.java | onReceive", "|"
				+ "=================" + "|");
		Bundle bundle = intent.getExtras();
		for (String key : bundle.keySet()) {
			Object value = bundle.get(key);
			Log.i("GcmBroadcastReceiver.java | onReceive",
					"|"
							+ String.format("%s : %s (%s)", key, value
									.toString(), value.getClass().getName())
							+ "|");

			if (key.equals("registration_id")) {
				regID = value.toString();
				TelephonyManager telephony = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				device_id = adapter.getAddress();
				(new gcm_register()).execute("");
			} else if (key.equals("ble_id")) {
				prefs = context.getSharedPreferences("customer",
						Activity.MODE_PRIVATE);
				Editor edit = prefs.edit();
				edit.putString("customer_device", value.toString());
				edit.putBoolean("checked", false);
				edit.commit();
				Log.d("my", "gcm is arrived!!");
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				adapter.startDiscovery();

			} else if (key.equals("table")) {
				Bundle bun = new Bundle();
				bun.putString("notiMessage", "test");
				 

				Intent popupIntent = new Intent(context, CallAlertActivity.class);

				popupIntent.putExtras(bun);
				PendingIntent pie= PendingIntent.getActivity(context, 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);
				try {
				 pie.send();
				} catch (CanceledException e) {
				}

			}
		}
		Log.i("GcmBroadcastReceiver.java | onReceive", "|"
				+ "=================" + "|");

		// Explicitly specify that GcmIntentService will handle the intent.
		ComponentName comp = new ComponentName(context.getPackageName(),
				GCMIntentService.class.getName());
		// Start the service, keeping the device awake while it is launching.
		// startWakefulService(context, intent.setComponent(comp));
		setResultCode(Activity.RESULT_OK);
	}

	public class gcm_register extends
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
				String postURL = "http://cs408.kaist.ac.kr:4418/api/register_user";
				HttpPost post = new HttpPost(postURL);

				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("reg_id", regID));
				parameters.add(new BasicNameValuePair("device_id", device_id));
				parameters.add(new BasicNameValuePair("iswaiter", "Y"));
				
				prefs = Gcontext.getSharedPreferences("waiter", Activity.MODE_PRIVATE);
				String waiter_name = prefs.getString("waiter_name", "default");
				parameters.add(new BasicNameValuePair("waiter_name", waiter_name));

				String restaurant_id = prefs.getString("restaurant_id", "error");
				parameters.add(new BasicNameValuePair("restaurant_id", restaurant_id));

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(parameters,
						HTTP.UTF_8);
				post.setEntity(ent);
				HttpResponse responsePOST = client.execute(post);

				HttpEntity resEntity = responsePOST.getEntity();

				Log.e("my", "redID = " + regID);
				if (resEntity != null) {
					Log.i("RESPONSE", EntityUtils.toString(resEntity));
				}
			} catch (Exception e) {

				Log.e("my", "POST ERROR ");
				e.printStackTrace();
			}
			return null;
		}
	}
}