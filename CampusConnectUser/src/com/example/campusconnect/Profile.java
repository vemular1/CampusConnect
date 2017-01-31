package com.example.campusconnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.campusconnect.utility.SharedPrefUtility;
import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class Profile extends Activity {

	TextView etFirstName, etLastName, etMobile, etEmail, etTerms, etPrivacy;
	private WebServices ws;
	private RequestParams params;
	private SharedPrefUtility sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		etFirstName = (TextView) findViewById(R.id.etFirstName);
		etLastName = (TextView) findViewById(R.id.etLastName);
		etMobile = (TextView) findViewById(R.id.etMobile);
		etEmail = (TextView) findViewById(R.id.etEmail);
		etTerms = (TextView) findViewById(R.id.etTermOfUse);
		etPrivacy = (TextView) findViewById(R.id.etPrivacyPolicy);
		getProfile();
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
	private void getProfile() {
		params = new RequestParams();
		ws = new WebServices(getApplicationContext());

		sharedPref = new SharedPrefUtility(getApplicationContext());
		params.put("email_id", sharedPref.getLoginId());

		ws.invokeWebService(params, "GetProfile", new WSResponnse() {

			@Override
			public void onResponse(boolean success, String response) {

				if (response.length() > 0) {
					JSONArray jsonArray;
					try {
						jsonArray = new JSONArray(response);

						String firstName = "";
						String lastName = "";
						String mobile = "";

						JSONObject jsonObject = jsonArray.getJSONObject(0);

						firstName = jsonObject.getString("user_first_name");
						lastName = jsonObject.getString("user_last_name");
						mobile = jsonObject.getString("user_mobile");

						etFirstName.setText(firstName);
						etLastName.setText(lastName);
						etMobile.setText(mobile);
						etEmail.setText(sharedPref.getLoginId());

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, "");
	}

	public void onClick(View v) {

		Intent intent = null;

		if (v.getId() == R.id.btnCancel) {

			intent = new Intent(this, HomePage.class);
			startActivity(intent);

		} else if (v.getId() == R.id.etTermOfUse) {
			intent = new Intent(this, Terms.class);
			startActivity(intent);

		} else if (v.getId() == R.id.etPrivacyPolicy) {
			intent = new Intent(this, PrivacyPolicy.class);
			startActivity(intent);

		}
	}

}
