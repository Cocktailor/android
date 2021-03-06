package com.cs408.cocktailor_Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cs408.R;
import com.cs408.cocktailor_Service.BluetoothService;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;

public class MenuActivity extends Activity {

	private ArrayList<Detail_Information> mGroupList = null;
	private ArrayList<Detail_Information> list = new ArrayList<Detail_Information>();
	private ArrayList<ArrayList<Detail_Information>> mChildList = null;
	private ArrayList<Detail_Information> mChildListContent1 = null;
	public ArrayList<Functional_Call_Information> fclist = null;
	public BaseExpandableAdapter adapter;
	private BluetoothService btService = null;
	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}

	};
	
	public class ArrayAdapterWithIcon extends ArrayAdapter<String> {

		private List<Bitmap> images;

		public ArrayAdapterWithIcon(Context context, String[] items, Bitmap[] images) {
		    super(context, android.R.layout.select_dialog_item, items);
		    this.images = Arrays.asList(images);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View view = super.getView(position, convertView, parent);
		    TextView textView = (TextView) view.findViewById(android.R.id.text1);
		    Drawable d = new BitmapDrawable(getResources(),images.get(position));
		    textView.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
		    textView.setCompoundDrawablePadding(
		    		(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getContext().getResources().getDisplayMetrics()));
		    return view;
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cocktail_menu);

		setLayout();
		// NfcRead.NFCRead_activity.finish();

		mGroupList = new ArrayList<Detail_Information>();
		mChildList = new ArrayList<ArrayList<Detail_Information>>();
		mChildListContent1 = new ArrayList<Detail_Information>();
		
		Detail_Information example = new Detail_Information();
		example.menu_name = "Midori Sour";

		mChildListContent1.add(example);

		mChildList.add(mChildListContent1);
		mChildList.add(mChildListContent1);
		mChildList.add(mChildListContent1);

		SharedPreferences prefs =  getSharedPreferences("from", Activity.MODE_PRIVATE);
		final int rest_id = prefs.getInt("restaurant", 1);
		final String table_number = prefs.getString("table_number", "");

		adapter = new BaseExpandableAdapter(this, mGroupList, mChildList);
		mListView.setAdapter(adapter);

		if (btService == null) {
			btService = new BluetoothService(this, mHandler);
		}

		// 占쌓뤄옙 클占쏙옙 占쏙옙占쏙옙 占쏙옙占� 占싱븝옙트
		mListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});

		// 占쏙옙占싹듸옙 클占쏙옙 占쏙옙占쏙옙 占쏙옙占� 占싱븝옙트
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

		// 占쌓뤄옙占쏙옙 占쏙옙占쏙옙 占쏙옙占� 占싱븝옙트
		mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				/*
				 * Toast.makeText(getApplicationContext(), "g Collapse = " +
				 * groupPosition, Toast.LENGTH_SHORT) .show();
				 */
			}
		});

		// 占쌓뤄옙占쏙옙 占쏙옙占쏙옙 占쏙옙占� 占싱븝옙트
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
//				Toast.makeText(v.getContext(), "confirm cart",
//						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), CartActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		

		call_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(v.getContext(), "call waiter",
//						Toast.LENGTH_SHORT).show();
				
				String nameArray[] = new String[fclist.size()];  
				Bitmap iconArray[] = new Bitmap[fclist.size()]; 
				
				for(int i=0; i<fclist.size(); i++) {
					Functional_Call_Information fc = fclist.get(i);
					nameArray[i] = fc.name;
					iconArray[i] = fc.bmimg;
					Log.d("hun", fc.bmimg.toString());
				}
				
	            ListAdapter adapter = new ArrayAdapterWithIcon(MenuActivity.this, nameArray, iconArray);

	            new AlertDialog.Builder(MenuActivity.this)
	            	.setTitle("Waigent Call")
	                .setAdapter(adapter, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int item ) {
			            	   Functional_Call_Information fc = fclist.get(item);
			            	   Log.d("yo", fc.toString());
			            	   
			            	   BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			            	   adapter.enable();
	
			            	   Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			            	   discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
			            	   startActivity(discoverableIntent);
			            	   
			            	   Call_waiter c = new Call_waiter();
			            	   c.table = table_number;
			            	   c.functional_call_name = fc.name;
			            	   c.restaurant_id = Integer.toString(rest_id);
			            	   c.execute("");
	                    }
	            }).show();			    
			}
		});

		(new Menu_receive())
				.execute("http://cs408.kaist.ac.kr:4418/api/menu_receive/" + Integer.toString(rest_id));
	}
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences prefs = getSharedPreferences("cart", Activity.MODE_PRIVATE);
		int cnt = prefs.getInt("count", 0);
		if(cnt==0){
			adapter.clear_count_of_menu();
			Editor edit=prefs.edit();
			edit.putInt("price", 0);
			edit.commit();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = getSharedPreferences("cart", Activity.MODE_PRIVATE);
		int cnt = prefs.getInt("count", 0);
		if(cnt==0){
			adapter.clear_count_of_menu();
			Editor edit=prefs.edit();
			edit.putInt("price", 0);
			edit.commit();
		}
		
	}


	/*
	 * Layout
	 */
	private ExpandableListView mListView;
	private ImageButton cart_button, call_button;

	private void setLayout() {
		mListView = (ExpandableListView) findViewById(R.id.menu_list);
		cart_button = (ImageButton) findViewById(R.id.order_button1);
		call_button = (ImageButton) findViewById(R.id.call_button1);
	}

	public class Call_waiter extends AsyncTask<String, Void, ArrayList<String>> {
		private final ProgressDialog dialog = new ProgressDialog(MenuActivity.this);
		public String table;
		public String functional_call_name;
		public String restaurant_id;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Calling Waiter...");
			dialog.show();
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			new AlertDialog.Builder(MenuActivity.this)
				.setTitle("Call Completed!")
				.setItems(new String[]{"Ok"}, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
		}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				HttpClient client = new DefaultHttpClient();
				String postURL = "http://cs408.kaist.ac.kr:4418/api/call_waiter";
				HttpPost post = new HttpPost(postURL);

				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("ble_id", adapter.getAddress()));
				parameters.add(new BasicNameValuePair("table", table));
				parameters.add(new BasicNameValuePair("functional_call_name", functional_call_name));
				parameters.add(new BasicNameValuePair("restaurant_id", restaurant_id));

				UrlEncodedFormEntity ent = new UrlEncodedFormEntity(parameters,
						HTTP.UTF_8);
				post.setEntity(ent);
				HttpResponse responsePOST = client.execute(post);
				HttpEntity resEntity = responsePOST.getEntity();

				if (resEntity != null) {
					Log.i("RESPONSE", EntityUtils.toString(resEntity));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public class Menu_receive extends AsyncTask<String, Void, ArrayList<Detail_Information>> {

		private final ProgressDialog dialog = new ProgressDialog(MenuActivity.this);

		@Override
		protected void onPostExecute(ArrayList<Detail_Information> result) {
			super.onPostExecute(result);
			adapter.setGroup(result);

			adapter.notifyDataSetChanged();
			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Downloading Menu...");
			dialog.show();
		}

		
		@Override
		protected ArrayList<Detail_Information> doInBackground(String... params) {
			ArrayList<Detail_Information> result = new ArrayList<Detail_Information>();
			ArrayList<ArrayList<Detail_Information>> detail_menu = new ArrayList<ArrayList<Detail_Information>>();
			HashMap<Integer, ArrayList<Detail_Information>> menu_storage = new HashMap<Integer, ArrayList<Detail_Information>>();
			ArrayList<String> menu1 = new ArrayList<String>();
			ArrayList<String> menu2 = new ArrayList<String>();
			
			fclist = new ArrayList<Functional_Call_Information>();
			
			try {
				// (1)
				HttpGet method = new HttpGet(
						params[0]);
				// (2)
				DefaultHttpClient client = new DefaultHttpClient();
				// 占쏙옙占쏙옙占� 占쏙옙占쏙옙
				// method.setHeader("Connection", "Keep-Alive");
				// (3)
				Log.d("asdf", "JSON Receive");
				HttpResponse response = client.execute(method);
				// (4) response status 占쏙옙 400 占쏙옙 占싣니띰옙占� ( 占쏙옙占쏙옙占쏙옙占쏙옙 )
				int status = response.getStatusLine().getStatusCode();
				if (status != HttpStatus.SC_OK) {
					Log.e("asdf", "Connection is failed");
					throw new Exception(""); // 占쏙옙占쏙옙

				}
				// (5) response 占쌨깍옙 JSONArray 占쏙옙 占식쏙옙
				String str = EntityUtils
						.toString(response.getEntity(), "UTF-8");
				JSONObject jsonObject = new JSONObject(str);
				JSONArray category = jsonObject.getJSONArray("category");
				JSONArray cocktail_menu = jsonObject.getJSONArray("menu");
				JSONArray functional_call_names = jsonObject.getJSONArray("functional_call_name");
				JSONObject temporary;

				for (int i = 0; i < cocktail_menu.length(); i++) {
					temporary = cocktail_menu.getJSONObject(i);
					int menu_id = temporary.getInt("category_id");
					String menu_name = temporary.getString("name");
					int price = temporary.getInt("price");
					String pic_link = temporary.getString("picture");
					String description = temporary.getString("description");
					Detail_Information di = new Detail_Information();
					di.description = description;
					di.menu_name = menu_name;
					di.price=price;
					di.pic_link = pic_link;
					if (!menu_storage.containsKey(menu_id))
						menu_storage.put(menu_id, new ArrayList<Detail_Information>());
					menu_storage.get(menu_id).add(di);
				}

				Set<Entry<Integer, ArrayList<Detail_Information>>> set = menu_storage.entrySet();
				Iterator<Entry<Integer, ArrayList<Detail_Information>>> it = set.iterator();

				while (it.hasNext()) {
					Map.Entry<Integer, ArrayList<Detail_Information>> e = (Map.Entry<Integer, ArrayList<Detail_Information>>) it
							.next();
					detail_menu.add(e.getValue());

				}
				adapter.setChild(detail_menu);

				for (int i = 0; i < category.length(); i++) {
					Detail_Information temp = new Detail_Information();
					temp.menu_name = category.getJSONObject(i).getString("name");
					result.add(temp);
				}


				for (int i = 0; i < functional_call_names.length(); i++) {
					temporary = functional_call_names.getJSONObject(i);
					int restaurant_id = temporary.getInt("restaurant_id");
					String name = temporary.getString("name");
					String pic_link = temporary.getString("picture");
					
					Functional_Call_Information fc = new Functional_Call_Information();
					fc.restaurant_id = restaurant_id;
					fc.name = name;
					fc.pic_link = pic_link;
					String url = "http://cs408.kaist.ac.kr:4418/api/picture/"+fc.pic_link;
					URL myFileUrl;
					
					try {
						myFileUrl = new URL(url);
						HttpURLConnection conn;
						conn = (HttpURLConnection) myFileUrl.openConnection();
						conn.setDoInput(true);
						conn.connect();
	
						InputStream is = conn.getInputStream();
	
						fc.bmimg = BitmapFactory.decodeStream(is);
						fc.bmimg = ImageCutHelper.getclip(fc.bmimg, 200, 200);
					}  catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					fclist.add(fc);
				}

			} catch (Exception e) {
				Log.e("asdf", e.getMessage());
			}
			return result;
		}
	}
	
}
