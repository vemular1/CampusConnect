package com.example.campusconnect;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.campusconnect.utility.SharedPrefUtility;
import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class NotificationEvent extends Activity {

	TextView tvToday, tvUpcoming;
	private Intent intent;
	private RequestParams params;
	private WebServices ws;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		tvToday = (TextView) findViewById(R.id.tvTodayEvents);
		tvUpcoming = (TextView) findViewById(R.id.tvUpcomingEvents);

		// load events
		getEvents("all");
		getEvents("today");
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
	private void getEvents(final String tag) {

		intent = new Intent(this, HomePage.class);

		params = new RequestParams();
		params.add("tag", tag);
		ws = new WebServices(getApplicationContext());

		ws.invokeWebService(params, "GetEvents", new WSResponnse() {
			@Override
			public void onResponse(boolean success, String response) {
				if (success) {

					try {
						if (response.length() > 0) {
							JSONArray jsonArray = new JSONArray(response);
							String eventValues = "";
							for (int i = 0; i < jsonArray.length(); i++) {
								eventValues = eventValues
										+ jsonArray.get(i).toString() + "\n";
							}

							if (tag.equals("all")) {
								tvUpcoming.setText(eventValues);
							} else {
								tvToday.setText(eventValues);
							}
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}, "");

	}
}
