package com.example.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.campusconnect.utility.SharedPrefUtility;
import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class ReportEmergencies extends Activity {

	Spinner spCategory, spPriority;
	private Intent intent;
	private RequestParams params;
	private WebServices ws;
	private SharedPrefUtility sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_emergency);
		spCategory = (Spinner) findViewById(R.id.spCategory);
		spPriority = (Spinner) findViewById(R.id.spPriority);
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
				
				Intent logout = new Intent(this, WelcomePage.class);
				startActivity(logout);
				return true;
			}
			return super.onOptionsItemSelected(item);

		}
	public void onClick(View v) {
		
if(v.getId()==R.id.imgCall){
	
	Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
    phoneCallIntent.setData(Uri.parse("tel:+1"+"4437637258"));
    startActivity(phoneCallIntent);
}else
		
		sendEmergencyReport();
	}

	private void sendEmergencyReport() {

		intent = new Intent(this, HomePage.class);
		params = new RequestParams();
		ws = new WebServices(getApplicationContext());
		sharedPref = new SharedPrefUtility(getApplicationContext());

		params.add("category", spCategory.getSelectedItem().toString());
		params.add("priority", spPriority.getSelectedItem().toString());
		params.add("email_id", sharedPref.getLoginId());

		ws.invokeWebService(params, "ReportEmergency", new WSResponnse() {
			@Override
			public void onResponse(boolean success, String response) {
				Toast.makeText(getApplicationContext(), "EmergencyReport sent",
						Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		}, "");

	}

}
