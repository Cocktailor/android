package com.cs408.cocktailor_Activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.cs408.R;
import com.cs408.cocktailor_Service.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {

	private ArrayList<Detail_Information> groupList = null;
	private ArrayList<ArrayList<Detail_Information>> childList = null;
	private LayoutInflater inflater = null;
	private ViewHolder viewHolder = null;
	private SharedPreferences prefs;
	private ImageView thumbnail;
	private Context mContext;
	Bitmap bmimg;

	public BaseExpandableAdapter(Context c, ArrayList<Detail_Information> groupList,
			ArrayList<ArrayList<Detail_Information>> childList) {
		super();
		this.inflater = LayoutInflater.from(c);
		
		this.mContext = c;

		this.prefs = c.getSharedPreferences( "cart" , Activity.MODE_PRIVATE);
		this.groupList = groupList;
		this.childList = childList;
	}

	public void setGroup(ArrayList<Detail_Information> groupList) {
		this.groupList = groupList;
	}

	public void setChild(ArrayList<ArrayList<Detail_Information>> childList) {
		this.childList = childList;
	}

	@Override
	public Detail_Information getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.category_list_row, parent, false);
			viewHolder.tv_groupName = (TextView) v.findViewById(R.id.tv_group);
			viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		if (isExpanded) {
			viewHolder.iv_image.setImageResource(R.drawable.temp_arrow2);
		} else {
			viewHolder.iv_image.setImageResource(R.drawable.temp_arrow1);
		}

		viewHolder.tv_groupName.setText(getGroup(groupPosition).menu_name);

		return v;
	}

	@Override
	public Detail_Information getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@SuppressLint("InflateParams") @Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		View v = convertView;
		final int groupP = groupPosition;
		final int childP = childPosition;
		

		if (v == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.cocktail_list_row, null);
			viewHolder.tv_childName = (TextView) v.findViewById(R.id.tv_child);
			viewHolder.rating = (RatingBar) v.findViewById(R.id.ratingBar1);
			viewHolder.menu_plus_button = (ImageButton) v
					.findViewById(R.id.menu_add_button1);
			viewHolder.menu_minus_button = (ImageButton) v
					.findViewById(R.id.menu_minus_button1);
			viewHolder.thumbnail = (ImageView) v.findViewById(R.id.child_thumbnail);
			viewHolder.count = (TextView)v.findViewById(R.id.counting);
			
			
			//viewHolder.np = (NumberPicker)v.findViewById(R.id.number_picker1);
			//viewHolder.np.setMaxValue(10);
	        //viewHolder.np.setMinValue(0);
	        //viewHolder.np.setWrapSelectorWheel(false);

			viewHolder.rating.setStepSize((float) 0.5); // �� ������ 1ĭ���پ��� �þ
														// 0.5���ϸ� ��ĭ�� ��
			viewHolder.rating.setRating((float) 4.5); // ó�������ٶ�(������ �Ѱ�������)
														// default ���� 0 �̴�
			viewHolder.rating.setIsIndicator(true);
			

			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		thumbnail = viewHolder.thumbnail;

		ImageLoader imageloader = new ImageLoader(this.mContext);
		
		imageloader.DisplayImage("http://cs408.kaist.ac.kr:4418/api/picture/"+getChild(groupPosition, childPosition).pic_link, thumbnail);
		//(new image_receive()).execute("http://cs408.kaist.ac.kr:4418/api/picture/"+getChild(groupPosition, childPosition).pic_link);
		viewHolder.tv_childName.setText(getChild(groupPosition, childPosition).menu_name);
		viewHolder.tv_childName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("my","selected Item = " + getChild(groupP, childP));
				Intent intent = new Intent(v.getContext(),DetailViewActivity.class);
				//Intent intent = new Intent(v.getContext(),CallAlertActivity.class);
				intent.putExtra("item", getChild(groupP, childP).menu_name);
				intent.putExtra("pic_link", getChild(groupP, childP).pic_link);
				intent.putExtra("price", getChild(groupP, childP).price);
				intent.putExtra("description", getChild(groupP, childP).description);
				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(intent);
				
			}
		});
		viewHolder.menu_plus_button
		.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor edit = prefs.edit();
				int ex_cart = prefs.getInt(getChild(groupP, childP).menu_name, 0);//īƮ�� �߰����ִ� �޴���
				Set<String> added_menu = prefs.getStringSet("added_menu", new HashSet<String>());
				int cnt = prefs.getInt("count", 0);
				if(ex_cart==0){
					edit.putInt(getChild(groupP, childP).menu_name, 1);
					added_menu.add(getChild(groupP, childP).menu_name);
					cnt+=1;
				}
				else{
					edit.putInt(getChild(groupP, childP).menu_name, ex_cart+1);
				}
				edit.putInt("count", cnt);
				edit.putStringSet("added_menu", added_menu);
				edit.commit();
				Toast.makeText(
						v.getContext(),
						getChild(groupP, childP)
								+ "is added in cart\n" + "cnt = " + Integer.toString(cnt),
						Toast.LENGTH_SHORT).show();
			}
		});

		return v;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	class ViewHolder {
		public ImageView iv_image, thumbnail;
		public TextView tv_groupName;
		public TextView tv_childName,count;
		public NumberPicker np;
		public RatingBar rating;
		public ImageButton menu_plus_button,menu_minus_button;
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
