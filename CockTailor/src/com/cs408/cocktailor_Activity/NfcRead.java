package com.cs408.cocktailor_Activity;

import java.io.UnsupportedEncodingException;

import com.cs408.cocktailor_Service.WaiterCallService;
import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.SharedPreferences.Editor;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class NfcRead extends Activity {
	private NfcAdapter mNfcAdapter;
	public static Activity NFCRead_activity;
	private SharedPreferences prefs;
	public static final String MIME_TEXT_PLAIN = "application/com.cs408.cocktailor";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NFCRead_activity = this;

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		if (mNfcAdapter == null) {
			Toast.makeText(this, "NFC를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (!mNfcAdapter.isEnabled()) {
			Intent setnfc = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
			startActivity(setnfc);
		} else {
			handleIntent(getIntent());
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		setupForegroundDispatch(this, mNfcAdapter);
	}

	private void handleIntent(Intent intent) {
		String action = intent.getAction();

		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

			String type = intent.getType();
			if (type.contains(MIME_TEXT_PLAIN)) {
				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

				// Toast.makeText(NfcRead.this, "NFC is tagged!",
				// Toast.LENGTH_SHORT).show();
				new NdefReaderTask().execute(tag);

			} else {
			}
		} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String[] techList = tag.getTechList();
			String searchedTech = Ndef.class.getName();

			for (String tech : techList) {
				if (searchedTech.equals(tech)) {
					new NdefReaderTask().execute(tag);
					break;
				}
			}
		}
	}

	@Override
	protected void onPause() {
		stopForegroundDispatch(this, mNfcAdapter);

		super.onPause();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	public static void setupForegroundDispatch(final Activity activity,
			NfcAdapter adapter) {
		final Intent intent = new Intent(activity.getApplicationContext(),
				activity.getClass());
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		final PendingIntent pendingIntent = PendingIntent.getActivity(
				activity.getApplicationContext(), 0, intent, 0);

		IntentFilter[] filters = new IntentFilter[1];
		String[][] techList = new String[][] {};

		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
		try {
			filters[0].addDataType(MIME_TEXT_PLAIN);
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("Check your mime type.");
		}

		adapter.enableForegroundDispatch(activity, pendingIntent, filters,
				techList);
	}

	public static void stopForegroundDispatch(final Activity activity,
			NfcAdapter adapter) {
		adapter.disableForegroundDispatch(activity);
	}

	private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

		@Override
		protected String doInBackground(Tag... params) {
			Tag tag = params[0];
			Ndef ndef = Ndef.get(tag);
			if (ndef == null) {
				// NDEF is not supported by this Tag.
				return null;
			}

			NdefMessage ndefMessage = ndef.getCachedNdefMessage();

			NdefRecord[] records = ndefMessage.getRecords();

			for (NdefRecord ndefRecord : records) {
				try {
					return readText(ndefRecord);
				} catch (UnsupportedEncodingException e) {
					Log.e("myLog", "Unsupported Encoding", e);
				}

			}

			return null;
		}

		private String readText(NdefRecord record)
				throws UnsupportedEncodingException {

			byte[] payload = record.getPayload();

			String value = new String(payload, "UTF-8");

			return value;
		}

		@Override
		protected void onPostExecute(String result) {

			prefs = getSharedPreferences("from", Activity.MODE_PRIVATE);
			Editor edit = prefs.edit();
			if (result.equals("customer")) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), SplashScreen.class);

				edit.putBoolean("NFC", true);
				edit.putString("who", "customer");
				edit.commit();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				Log.e("my", result);

				//registerGcm();
				startActivity(intent);

				finish();
			} else if (result.equals("waiter")) {
				Log.e("my", "waiter mode");
				Intent service = new Intent(getApplicationContext(), WaiterCallService.class);
				startService(service);
				edit.putBoolean("NFC", true);
				edit.putString("who", "waiter");
				finish();
			}
		}

	}

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

}
