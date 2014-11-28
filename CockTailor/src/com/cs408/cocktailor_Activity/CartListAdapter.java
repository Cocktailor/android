package com.cs408.cocktailor_Activity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.cs408.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CartListAdapter extends ArrayAdapter<Added_Menu>{



	private SharedPreferences prefs;
	private LayoutInflater inflater = null;
	private ArrayList<Added_Menu> menus = null;

	public CartListAdapter(Context context, int resource, ArrayList<Added_Menu> menus) {
		super(context, resource, menus);

		this.inflater = LayoutInflater.from(context);

		this.prefs = context.getSharedPreferences( "cart" , Activity.MODE_PRIVATE);
		this.menus = menus;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		final ArrayAdapter<Added_Menu> adapter=this;
		if(view == null){
			view = inflater.inflate(R.layout.cart_list, parent, false);
			
		}
		
		Added_Menu ob = getItem(position);
		final String mName = ob.menu;
		final int mAmount = ob.amount;
		TextView menu_name = (TextView)view.findViewById(R.id.cart_list_menu);
		TextView menu_amount = (TextView)view.findViewById(R.id.cart_list_amount);
		/*ImageButton plus = (ImageButton)view.findViewById(R.id.cart_plus_button);
		ImageButton minus = (ImageButton)view.findViewById(R.id.cart_minus_button);
		
		plus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Editor edit = prefs.edit();
				int ex_am = prefs.getInt(mName, 0);
				edit.putInt(mName, ex_am+1);
				edit.commit();
				adapter.notifyDataSetChanged();
			}
		});
		minus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Editor edit = prefs.edit();
				int ex_am = prefs.getInt(mName, 0);
				if(ex_am > 0){
					edit.putInt(mName, ex_am-1);
				}
				edit.commit();
				adapter.notifyDataSetChanged();
			}
		});*/
		
		
		menu_name.setText(ob.menu);
		menu_amount.setText(Integer.toString(prefs.getInt(mName, 0)));
		
		return view;
		
	}
	
}
