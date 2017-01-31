package com.example.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.campusconnect.utility.SharedPrefUtility;
import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class WelcomePage extends Activity implements OnClickListener {

	EditText etNetId, etPassword;
	Button btnLogin;
	
	
	WebServices ws;
	RequestParams params;
	Intent intent;
	
	
	private SharedPrefUtility sharedPref;

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	//	super.onBackPressed();
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcom_page);

		sharedPref = new SharedPrefUtility(getApplicationContext());
		if (!sharedPref.getLoginId().isEmpty()) {
			Intent intent=new Intent(this,HomePage.class);
			startActivity(intent);

		}else {
			etNetId = (EditText) findViewById(R.id.etNetId);
			etPassword = (EditText) findViewById(R.id.etPassword);

			ws = new WebServices(getApplicationContext());

		}
		
		
		btnLogin=(Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.welcom_page, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent serverLogin = new Intent(this, ServerLogin.class);
			startActivity(serverLogin);
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	public void onClick(View v) {

/*		if (v.getId() == R.id.etNeedHelp) {

			intent = new Intent(this, NeedHelp.class);
			startActivity(intent);

		} else 
			
			*/
		if (v.getId() == R.id.btnLogin) {
			authUser();
		} else if (v.getId() == R.id.btnReset) {
			// clear all fields here
			etNetId.setText(null);
			etPassword.setText(null);

		} else if (v.getId() == R.id.txtForgotPassword) {
			intent = new Intent(this, ForgotPassword.class);
			startActivity(intent);

		} else if (v.getId() == R.id.btnSIGNUP) {
			intent = new Intent(this, SignUp.class);
			startActivity(intent);
		}
	}

	private void authUser() {

		intent = new Intent(this, HomePage.class);

		params = new RequestParams();
		params.add("email_id", etNetId.getText().toString());
		params.add("password", etPassword.getText().toString());

		ws.invokeWebService(params, "AuthUser", new WSResponnse() {
			@Override
			public void onResponse(boolean success, String response) {
				SharedPrefUtility sharedPref = null;
				if (success)
					sharedPref = new SharedPrefUtility(getApplicationContext());
				sharedPref.setLoginId(etNetId.getText().toString());
				startActivity(intent);
			}
		}, "");

	}

}
