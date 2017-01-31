package com.example.campusconnect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Terms extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
	}
	
	public void onClick(View v){
		finish();
	}
}
