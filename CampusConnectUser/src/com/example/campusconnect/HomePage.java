package com.example.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.campusconnect.gcm.GetApiKey;
import com.campusconnect.utility.SharedPrefUtility;
import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class HomePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		GetApiKey apiKey = new GetApiKey(getApplicationContext());

		apiKey.getRegId(new WSResponnse() {

			@Override
			public void onResponse(boolean success, String response) {
				// //TODO Auto-generated method stub

				// insert api key in to DB as an admin key
				sendApiToServer(response);

			}
		});
	}

	protected void sendApiToServer(String response) {

		RequestParams params = new RequestParams();
		params.put("key", response);
		params.put("type", "user");

		WebServices ws = new WebServices(getApplicationContext());

		ws.invokeWebService(params, "SendApi", new WSResponnse() {

			@Override
			public void onResponse(boolean success, String response) {

			}
		}, "");

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
		} else if (id == R.id.logout) {

			SharedPrefUtility pref = new SharedPrefUtility(
					getApplicationContext());
			pref.setLoginId("");

			Intent logout = new Intent(this, WelcomePage.class);
			startActivity(logout);
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	public void onClick(View v) {

		Intent intent = null;

		if (v.getId() == R.id.btnCollaboration) {

			intent = new Intent(this, RecentContacts.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnReportEmergencies) {

			intent = new Intent(this, ReportEmergencies.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnFeedback) {

			intent = new Intent(this, Feedback.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnNotification) {

			intent = new Intent(this, NotificationEvent.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnHelp) {

			intent = new Intent(this, Help.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnProfile) {

			intent = new Intent(this, Profile.class);
			startActivity(intent);

		}

	}

}
