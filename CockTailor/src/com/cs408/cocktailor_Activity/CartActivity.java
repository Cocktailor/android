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
		 * �̷������� �ϰ� �Ǹ� added_menu�� ���� �߰��� �޴����� ����Ʈ�� �޾ƿ�.
		 * int cnt = prefs.getInt("count", 0);
		 * ���⿡�� ���� ���� �߰��� �޴� ������ ���� �־�����
		 * �׸��� �ش� �޴��� �� �� �ֹ��ߴ��� �˷��� 
		 * int menu_cnt = prefs.getInt("pinacolada",0);
		 * �̷������� �ϸ� "pinacolada"�� �� �� �߰��ƴ��� ���� �޾ƿ� �� �־��.
		 * 
		 * SharedPreference�� �� �����ϴ°Ŵ� ���� BaseExpandableAdapter.java :136�ٺ��� �����س����� �����Ͻø� �� �Ͱ��ƿ�.
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
