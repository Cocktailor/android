package com.cs408.cocktailor_Activity;


import com.cs408.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class CallAlertActivity extends Activity{

private SharedPreferences prefs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		long[] pattern = {100, 200};
		final Vibrator m_vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		m_vibrator.vibrate(pattern, 1);
		new AlertDialog.Builder(CallAlertActivity.this)
			.setTitle("Waigent - Customer Call")
			.setMessage("\ntable : 3\n\ntonic water\n")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                	  finish();
                  }
              }).show();
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