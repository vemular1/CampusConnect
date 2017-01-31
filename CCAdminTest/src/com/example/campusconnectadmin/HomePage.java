package com.example.campusconnectadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
	}

	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub

			getMenuInflater().inflate(R.menu.main, menu);

			return super.onCreateOptionsMenu(menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub

			int id = item.getItemId();
			if (id == R.id.home) {
				Intent home = new Intent(this, HomePage.class);
				startActivity(home);
				return true;
			}else if (id == R.id.logout) {
				
				SharedPrefUtility pref=new SharedPrefUtility(getApplicationContext());
				pref.setLoginId("");
				
				Intent logout = new Intent(this, Login.class);
				startActivity(logout);
				return true;
			}
			return super.onOptionsItemSelected(item);

		}
	
	public void onClick(View v){		
		Intent intent=null;		
		if (v.getId()==R.id.btnEvents){
			intent=new Intent(this,Events.class);
		}else if (v.getId()==R.id.btnSuggession){
			intent=new Intent(this,Suggessions.class);
		}else if (v.getId()==R.id.btnFeedback){
			intent=new Intent(this,Feedback.class);
		}else if (v.getId()==R.id.btnEmergencyReports){
			intent=new Intent(this,EmergencyReports.class);
		}else if (v.getId()==R.id.btnStudentInfo){
			intent=new Intent(this,StudentInfo.class);
		}		
		startActivity(intent);
	}	
}
