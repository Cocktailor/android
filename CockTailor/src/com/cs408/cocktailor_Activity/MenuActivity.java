package com.cs408.cocktailor_Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuActivity extends Activity {

	private ArrayList<String> mGroupList = null;
	private ArrayList<String> list = new ArrayList<String>();
	private ArrayList<ArrayList<String>> mChildList = null;
	private ArrayList<String> mChildListContent1 = null;
	private BaseExpandableAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cocktail_menu);

		setLayout();
		// NfcRead.NFCRead_activity.finish();

		mGroupList = new ArrayList<String>();
		mChildList = new ArrayList<ArrayList<String>>();
		mChildListContent1 = new ArrayList<String>();

		mChildListContent1.add("Midori Sour");
		mChildListContent1.add("Sex on the beach");
		mChildListContent1.add("Gin Tonic");

		mChildList.add(mChildListContent1);
		mChildList.add(mChildListContent1);
		mChildList.add(mChildListContent1);

		adapter = new BaseExpandableAdapter(this, mGroupList, mChildList);
		mListView.setAdapter(adapter);

		// 그룹 클릭 했을 경우 이벤트
		mListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});

		// 차일드 클릭 했을 경우 이벤트
		mListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				/*
				 * Toast.makeText(getApplicationContext(), "c click = " +
				 * childPosition, Toast.LENGTH_SHORT) .show();
				 */
				return false;
			}
		});

		// 그룹이 닫힐 경우 이벤트
		mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				/*
				 * Toast.makeText(getApplicationContext(), "g Collapse = " +
				 * groupPosition, Toast.LENGTH_SHORT) .show();
				 */
			}
		});

		// 그룹이 열릴 경우 이벤트
		mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				/*
				 * Toast.makeText(getApplicationContext(), "g Expand = " +
				 * groupPosition, Toast.LENGTH_SHORT) .show();
				 */
			}
		});

		cart_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "confirm cart",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), CartActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});

		(new Menu_receive())
				.execute("http://cs408.kaist.ac.kr:4418/menu_receive");
	}

	/*
	 * Layout
	 */
	private ExpandableListView mListView;
	private ImageButton cart_button;

	private void setLayout() {
		mListView = (ExpandableListView) findViewById(R.id.menu_list);
		cart_button = (ImageButton) findViewById(R.id.order_button1);
	}

	public class Menu_receive extends
			AsyncTask<String, Void, ArrayList<String>> {
		private final ProgressDialog dialog = new ProgressDialog(
				MenuActivity.this);

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			adapter.setGroup(result);

			adapter.notifyDataSetChanged();
			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Downloading contacts...");
			dialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			ArrayList<String> result = new ArrayList<String>();
			ArrayList<ArrayList<String>> detail_menu = new ArrayList<ArrayList<String>>();
			ArrayList<String> menu1 = new ArrayList<String>();
			ArrayList<String> menu2 = new ArrayList<String>();
			menu1.add("Midori Sour");
			menu1.add("Sex on the beach");
			menu1.add("Gin Tonic");

			menu2.add("Pinacolada");
			menu2.add("Black Russian");
			menu2.add("Pink Lady");

			detail_menu.add(menu1);
			detail_menu.add(menu2);
			adapter.setChild(detail_menu);
			result.add("Category 1");
			result.add("Category 2");
			try{
				// (1)
				HttpGet method = new HttpGet(params[0]);
				// (2)
				DefaultHttpClient client = new DefaultHttpClient();
				// 헤더를 설정
				method.setHeader("Connection", "Keep-Alive");
				// (3)
				HttpResponse response = client.execute(method);
				// (4) response status 가 400 이 아니라면 ( 오류나면 )
				int status = response.getStatusLine().getStatusCode();
				if (status != HttpStatus.SC_OK)
					throw new Exception(""); // 실패
				// (5) response 받기 JSONArray 로 파싱
				String str = EntityUtils
						.toString(response.getEntity(), "UTF-8");
				JSONArray test_result = new JSONArray(str);

			}
			catch (Exception e)
			{
			}
			return result;
		}
	}
}
