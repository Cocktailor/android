package com.cs408.cocktailor_Activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cs408.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class DetailViewActivity extends Activity {

	private SharedPreferences prefs;
	ImageView thumbnail;
	Bitmap bmimg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_menu);
		Intent intent = getIntent();
		String item = intent.getStringExtra("item");
		String pic_link = intent.getStringExtra("pic_link");
		String description = intent.getStringExtra("description");
		TextView item_name = (TextView) findViewById(R.id.detail_view_name);
		item_name.setText(item);
		
		TextView des_view_title = (TextView)findViewById(R.id.description_title);
		des_view_title.setBackgroundResource(R.layout.linesample);
		TextView des_view  = (TextView) findViewById(R.id.description);
		des_view.setText(description);
		
		
		ImageButton close_button = (ImageButton)findViewById(R.id.detail_view_close);
		close_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		
		thumbnail = (ImageView) findViewById(R.id.menu_thumbnail);
		Log.e("my","pic_link = " + pic_link);
		(new image_receive()).execute("http://cs408.kaist.ac.kr:4418/api/picture/"+pic_link);

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

	private class image_receive extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
			// TODO Auto-generated method stub
			try {
				URL myFileUrl = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) myFileUrl
						.openConnection();
				conn.setDoInput(true);
				conn.connect();

				InputStream is = conn.getInputStream();

				bmimg = BitmapFactory.decodeStream(is);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return bmimg;
		}

		protected void onPostExecute(Bitmap img) {
			thumbnail.setImageBitmap(img);
		}

	}

}
