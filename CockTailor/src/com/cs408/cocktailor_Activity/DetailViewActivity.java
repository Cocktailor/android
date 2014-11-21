package com.cs408.cocktailor_Activity;

import com.cs408.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

public class DetailViewActivity extends Activity{

private SharedPreferences prefs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_menu);
		Intent intent = getIntent();
		String item = intent.getStringExtra("item");
		TextView item_name = (TextView)findViewById(R.id.detail_view_name);
		item_name.setText(item);
		
		
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
