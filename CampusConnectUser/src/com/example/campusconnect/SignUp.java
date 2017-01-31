package com.example.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class SignUp extends Activity {

	EditText etEmailId, etRetypePassword, etFirstName, etLastName, etMobile,
			etPassword;
	private Intent intent;
	private WebServices ws;
	private RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		etEmailId = (EditText) findViewById(R.id.etEmail);
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etMobile = (EditText) findViewById(R.id.etMobile);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etRetypePassword = (EditText) findViewById(R.id.etRetypePassword);
	}

	public void onClick(View v) {

		Intent intent = null;

		if (v.getId() == R.id.btnCancel) {

			intent = new Intent(this, WelcomePage.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnSignup) {
			// send sign up details to server and take to login page
			String password=etPassword.getText().toString();
			String rePassword=etRetypePassword.getText().toString();
			
			Log.e("test",password+" "+rePassword);
			if(!password.isEmpty()  && password.equals(rePassword)){
				signup();
			}
			else Toast.makeText(getApplicationContext(), "Please fill both the password " +
					"fields and retype correctly",Toast.LENGTH_LONG).show(); 

		} else if (v.getId() == R.id.btnReset) {
			// clear all fields here
		
			
			etEmailId.setText("");etFirstName.setText("");
			etPassword.setText("");etLastName.setText("");
			etMobile.setText("");
			
		}

	}

	private void signup() {
	
		intent = new Intent(this, WelcomePage.class);

		ws = new WebServices(getApplicationContext());
		params = new RequestParams();
		params.add("email_id", etEmailId.getText().toString());
		params.add("first_name", etFirstName.getText().toString());
		params.add("last_name", etLastName.getText().toString());
		params.add("mobile", etMobile.getText().toString());
		params.add("password", etPassword.getText().toString());

		
		ws.invokeWebService(params, "RegisterUser", new WSResponnse() {
			
			@Override
			public void onResponse(boolean success, String response) {

				Toast.makeText(getApplicationContext(), "User registered. Please login",Toast.LENGTH_LONG).show();
				
				startActivity(intent);
				
			}
		}, "");
		
		
		
		
	}

}
