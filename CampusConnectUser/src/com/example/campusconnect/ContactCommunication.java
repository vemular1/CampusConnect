package com.example.campusconnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.campusconnect.chat.Chat;
import com.campusconnect.utility.SharedPrefUtility;
import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class ContactCommunication extends Activity {

	TextView tvName, tvPhone, tvEmail;
	private SharedPrefUtility sharedPref;
	private String email_id;
	private Intent intent;
	private RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_communication);
		sharedPref = new SharedPrefUtility(getApplicationContext());

		Bundle B = getIntent().getExtras();
		setEmail_id(B.getString("email_id"));

		tvName = (TextView) findViewById(R.id.tvContactName);
		tvPhone = (TextView) findViewById(R.id.tvPhone);
		tvEmail = (TextView) findViewById(R.id.tvEmail);

		tvEmail.setText(getEmail_id());
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
		WebServices ws = new WebServices(getApplicationContext());
		intent = new Intent(this, WelcomePage.class);
		params = new RequestParams();
		params.add("email_id", getEmail_id());

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

						JSONObject jsonObject =
						jsonArray.getJSONObject(0);
						
						firstName=jsonObject.getString("user_first_name");
						lastName=jsonObject.getString("user_last_name");
						mobile=jsonObject.getString("user_mobile");
						
						tvName.setText(firstName+" "+lastName);
						
						tvPhone.setText(mobile);
						
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

		if (v.getId() == R.id.btnCall) {

			//intent = new Intent(this, CallPage.class);
			//startActivity(intent);
			
			Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
            phoneCallIntent.setData(Uri.parse("tel:+1"+tvPhone.getText().toString()));
            startActivity(phoneCallIntent);

		} else if (v.getId() == R.id.btnMessage) {
			intent = new Intent(ContactCommunication.this, Chat.class);
			intent.putExtra("name", sharedPref.getLoginId());

			startActivity(intent);
		} else if (v.getId() == R.id.btnShare) {
			intent = new Intent(this, ShareFiles.class);
			startActivity(intent);
		}

	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

}
