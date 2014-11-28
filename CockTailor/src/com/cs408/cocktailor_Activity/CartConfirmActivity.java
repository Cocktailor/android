package com.cs408.cocktailor_Activity;


import com.cs408.R;

import android.app.Activity;
import android.content.Context;
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

public class CartConfirmActivity extends Activity{

private SharedPreferences prefs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.confirm_order);	
		Button bt = (Button)findViewById(R.id.order_confirm);
		long[] pattern = {100, 200};
		final Vibrator m_vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		m_vibrator.vibrate(pattern, 1);
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				m_vibrator.cancel();
				// TODO Auto-generated method stub
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