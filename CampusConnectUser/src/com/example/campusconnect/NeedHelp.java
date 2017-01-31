package com.example.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NeedHelp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_need_help);
	}

	public void onClick(View v) {

		Intent intent = null;
		intent = new Intent(this, WelcomePage.class);
		startActivity(intent);
	}
}
