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

public class ForgotPassword extends Activity {
	EditText etEmail;
	private Intent intent;
	private RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

		etEmail = (EditText) findViewById(R.id.etEmail);
	}
	
	public void onClick(View v) {

		Intent intent = null;

		if (v.getId() == R.id.btnCancel) {

			intent = new Intent(this, WelcomePage.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnEnter) {
			// send sign up details to server and take to login page
			forgotPassword();

		}
	}

	private void forgotPassword() {

		WebServices ws = new WebServices(getApplicationContext());

		intent = new Intent(this, WelcomePage.class);
		params = new RequestParams();
		params.add("email_id", etEmail.getText().toString());

		ws.invokeWebService(params, "ForgotPassword", new WSResponnse() {

			@Override
			public void onResponse(boolean success, String response) {
				if (success)
					Toast.makeText(getApplicationContext(),
							"Password sent to email", Toast.LENGTH_LONG).show();

				startActivity(intent);

			}
		}, "");
	}

}
