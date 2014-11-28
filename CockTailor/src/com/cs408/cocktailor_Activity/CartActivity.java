package com.cs408.cocktailor_Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.cs408.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class CartActivity extends Activity {

	private SharedPreferences prefs;
	private ImageButton back_button;
	private ListView addedListView;
	private ImageButton order_button;
	ArrayList<Added_Menu> menus = new ArrayList<Added_Menu>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.added_menu);
		this.prefs = getSharedPreferences("cart", Activity.MODE_PRIVATE);

		addedListView = (ListView) findViewById(R.id.cart_added_menu);
		back_button = (ImageButton) findViewById(R.id.cart_back_button);
		order_button = (ImageButton) findViewById(R.id.cart_order_button);

		Set<String> added_menu = prefs.getStringSet("added_menu",
				new HashSet<String>());

		
		int cnt = prefs.getInt("count", 0);
		for (Object o : added_menu.toArray()) {
			Added_Menu tempMenu = new Added_Menu();
			tempMenu.menu = o.toString();
			tempMenu.amount = prefs.getInt(tempMenu.menu, 0);
			menus.add(tempMenu);
		}
		back_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		order_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				(new send_order()).execute("");
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), CartConfirmActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}

		});

		CartListAdapter adapter = new CartListAdapter(this, R.layout.cart_list,
				menus);
		addedListView.setAdapter(adapter);
		/*
		 * Set<String> added_menu = prefs.getStringSet("added_menu", null);
		 * 이런식으로 하게 되면 added_menu에 현재 추가된 메뉴들의 리스트를 받아요. int cnt =
		 * prefs.getInt("count", 0); 여기에는 제가 현재 추가된 메뉴 종류의 개수 넣어놨어요 그리고 해당 메뉴를 몇
		 * 개 주문했는지 알려면 int menu_cnt = prefs.getInt("pinacolada",0); 이런식으로 하면
		 * "pinacolada"가 몇 개 추가됐는지 값을 받아올 수 있어요.
		 * 
		 * SharedPreference에 값 저장하는거는 제가 BaseExpandableAdapter.java :136줄부터
		 * 구현해놨으니 참고하시면 될 것같아요.
		 */
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
		Editor edit = prefs.edit();
		int tempcnt;
		int cnt = prefs.getInt("count", 0);
		for(Added_Menu am:menus){
			tempcnt = prefs.getInt(am.menu, 0);
			if(tempcnt == 0){
				edit.remove(am.menu);
				edit.putInt("count", cnt--);
				edit.commit();
			}
		}
	}

	public class send_order extends AsyncTask<String, Void, ArrayList<String>> {

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
				String postURL = "http://cs408.kaist.ac.kr:4418/home/getorder";
				HttpPost post = new HttpPost(postURL);

				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				SharedPreferences prefs = getSharedPreferences("cart",
						Activity.MODE_PRIVATE);
				Set<String> added_menu = prefs.getStringSet("added_menu",
						new HashSet<String>());
				String post_string = "";

				for (Object o : added_menu.toArray()) {
					Added_Menu tempMenu = new Added_Menu();
					tempMenu.menu = o.toString();
					tempMenu.amount = prefs.getInt(tempMenu.menu, 0);
					post_string += o.toString() + " X "
							+ Integer.toString(tempMenu.amount) + " / ";
				}

				parameters.add(new BasicNameValuePair("order_content",
						post_string.substring(0, post_string.length() - 3)));
				Log.e("my", post_string.substring(0, post_string.length() - 3));
				Editor edit = prefs.edit();
				edit.clear();
				edit.commit();
				
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
				
				parameters.add(new BasicNameValuePair("table", "3"));
				parameters.add(new BasicNameValuePair("price", "19000"));
				parameters.add(new BasicNameValuePair("time", sdf.format(d)));

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(parameters,
						HTTP.UTF_8);
				post.setEntity(ent);
				HttpResponse responsePOST = client.execute(post);

				HttpEntity resEntity = responsePOST.getEntity();

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
