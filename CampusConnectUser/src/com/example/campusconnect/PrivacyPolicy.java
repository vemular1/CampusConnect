package com.example.campusconnect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class PrivacyPolicy extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_privacy_policy);
	}

	public void onClick(View v){
		finish();
	}
}
