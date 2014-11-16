package com.cs408.cocktailor_Activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> groupList = null;
	private ArrayList<ArrayList<String>> childList = null;
	private LayoutInflater inflater = null;
	private ViewHolder viewHolder = null;
	private SharedPreferences prefs;

	public BaseExpandableAdapter(Context c, ArrayList<String> groupList,
			ArrayList<ArrayList<String>> childList) {
		super();
		this.inflater = LayoutInflater.from(c);

		this.prefs = c.getSharedPreferences( "cart" , Activity.MODE_PRIVATE);
		this.groupList = groupList;
		this.childList = childList;
	}

	public void setGroup(ArrayList<String> groupList) {
		this.groupList = groupList;
	}

	public void setChild(ArrayList<ArrayList<String>> childList) {
		this.childList = childList;
	}

	@Override
	public String getGroup(int groupPosition) {
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
			viewHolder.iv_image.setBackgroundColor(Color.GREEN);
		} else {
			viewHolder.iv_image.setBackgroundColor(Color.WHITE);
		}

		viewHolder.tv_groupName.setText(getGroup(groupPosition));

		return v;
	}

	@Override
	public String getChild(int groupPosition, int childPosition) {
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

			viewHolder.rating.setStepSize((float) 0.5); // 별 색깔이 1칸씩줄어들고 늘어남
														// 0.5로하면 반칸씩 들어감
			viewHolder.rating.setRating((float) 4.5); // 처음보여줄때(색깔이 한개도없음)
														// default 값이 0 이다
			viewHolder.rating.setIsIndicator(true);

			viewHolder.menu_plus_button
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Editor edit = prefs.edit();
							int ex_cart = prefs.getInt(getChild(groupP, childP), 0);//카트에 추가돼있는 메뉴들
							Set<String> added_menu = prefs.getStringSet("added_menu", new HashSet<String>());
							int cnt = prefs.getInt("count", 0);
							if(ex_cart==0){
								edit.putInt(getChild(groupP, childP), 1);
								added_menu.add(getChild(groupP, childP));
								cnt+=1;
							}
							else{
								edit.putInt(getChild(groupP, childP), ex_cart+1);
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

			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));

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
		public ImageView iv_image;
		public TextView tv_groupName;
		public TextView tv_childName;
		public RatingBar rating;
		public ImageButton menu_plus_button;
	}

}
