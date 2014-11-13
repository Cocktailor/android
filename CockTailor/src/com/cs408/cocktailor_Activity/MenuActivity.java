package com.cs408.cocktailor_Activity;

import com.appmaker.nfcread.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.ArrayList;

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
	private ArrayList<ArrayList<String>> mChildList = null;
	private ArrayList<String> mChildListContent1 = null;
	private BaseExpandableAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cocktail_menu);

		setLayout();
		NfcRead.NFCRead_activity.finish();
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
				Toast.makeText(getApplicationContext(),
						"g click = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		});

		// 차일드 클릭 했을 경우 이벤트
		mListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Toast.makeText(getApplicationContext(),
						"c click = " + childPosition, Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		});

		// 그룹이 닫힐 경우 이벤트
		mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						"g Collapse = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
			}
		});

		// 그룹이 열릴 경우 이벤트
		mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						"g Expand = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
			}
		});

		order_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "confirm cart",
						Toast.LENGTH_SHORT).show();
			}
		});

		(new Menu_receive()).execute("Receiving menu list test");
	}

	/*
	 * Layout
	 */
	private ExpandableListView mListView;
	private ImageButton order_button;

	private void setLayout() {
		mListView = (ExpandableListView) findViewById(R.id.menu_list);
		order_button = (ImageButton) findViewById(R.id.order_button1);
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

			try {
				/*
				 * URL u = new URL(params[0]);
				 * 
				 * HttpURLConnection conn = (HttpURLConnection)
				 * u.openConnection(); conn.setRequestMethod("GET");
				 * 
				 * conn.connect(); InputStream is = conn.getInputStream();
				 * 
				 * // Read the stream byte[] b = new byte[1024];
				 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 * 
				 * while (is.read(b) != -1) baos.write(b);
				 * 
				 * String JSONResp = new String(baos.toByteArray());
				 * 
				 * JSONArray arr = new JSONArray(JSONResp); for (int i = 0; i <
				 * arr.length(); i++) {
				 * result.add(convertContact(arr.getJSONObject(i))); }
				 */
				result.add("Category 1");
				result.add("Category 2");

				return result;
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return null;
		}

	}
}
