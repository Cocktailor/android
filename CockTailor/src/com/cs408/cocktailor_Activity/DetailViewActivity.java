package com.cs408.cocktailor_Activity;

import com.cs408.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.NumberPicker;

public class DetailViewActivity extends Activity{

private SharedPreferences prefs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_menu);
		NumberPicker np = (NumberPicker)findViewById(R.id.number_picker1);
		np.setMaxValue(10);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
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
