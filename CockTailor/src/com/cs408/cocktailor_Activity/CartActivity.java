package com.cs408.cocktailor_Activity;

import java.util.Set;

import com.cs408.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class CartActivity extends Activity {
	
	private SharedPreferences prefs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.added_menu);
		this.prefs = getSharedPreferences( "cart" , Activity.MODE_PRIVATE);
		/*
		 * Set<String> added_menu = prefs.getStringSet("added_menu", null);
		 * 이런식으로 하게 되면 added_menu에 현재 추가된 메뉴들의 리스트를 받아요.
		 * int cnt = prefs.getInt("count", 0);
		 * 여기에는 제가 현재 추가된 메뉴 종류의 개수 넣어놨어요
		 * 그리고 해당 메뉴를 몇 개 주문했는지 알려면 
		 * int menu_cnt = prefs.getInt("pinacolada",0);
		 * 이런식으로 하면 "pinacolada"가 몇 개 추가됐는지 값을 받아올 수 있어요.
		 * 
		 * SharedPreference에 값 저장하는거는 제가 BaseExpandableAdapter.java :136줄부터 구현해놨으니 참고하시면 될 것같아요.
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
	}

}
