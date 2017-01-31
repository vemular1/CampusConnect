package com.example.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.campusconnect.utility.SharedPrefUtility;
import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class Feedback extends Activity {

	EditText etAbout, etFeedback;

	private RequestParams params;
	private WebServices ws;
	private SharedPrefUtility sharedPref;

	protected Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		etAbout = (EditText) findViewById(R.id.etAbout);
		etFeedback = (EditText) findViewById(R.id.etFeedback);
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

		Intent intent = null;

		if (v.getId() == R.id.btnCancel) {

			intent = new Intent(this, HomePage.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnSend) {
			// send sign up details to server and take to login page

			sendFeedback();
		}
	}

	private void sendFeedback() {
		intent = new Intent(this, HomePage.class);
		params = new RequestParams();
		ws = new WebServices(getApplicationContext());
		sharedPref = new SharedPrefUtility(getApplicationContext());

		params.add("feedback", etFeedback.getText().toString());
		params.add("about", etAbout.getText().toString());
		params.add("email_id", sharedPref.getLoginId());

		ws.invokeWebService(params, "Feedback", new WSResponnse() {
			@Override
			public void onResponse(boolean success, String response) {
				Toast.makeText(getApplicationContext(), "Feedback sent",
						Toast.LENGTH_LONG).show();

				startActivity(intent);
			}
		}, "");
	}
}
