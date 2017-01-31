package com.example.campusconnectadmin;

import com.loopj.android.http.RequestParams;
import com.sun.jersey.core.header.InBoundHeaders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	EditText etNetId, etPassword;
	private SharedPrefUtility sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		sharedPref = new SharedPrefUtility(getApplicationContext());
		etNetId = (EditText) findViewById(R.id.etNetId);
		etPassword = (EditText) findViewById(R.id.etPassword);

		if (!sharedPref.getLoginId().isEmpty()) {
			callNotificationService();
			Intent intent = new Intent(this, HomePage.class);
			startActivity(intent);
		}
		
		
		
		
	}

	
 private void callNotificationService(){
	 GetApiKey apiKey=new GetApiKey(getApplicationContext());
		
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
		
		
		
		
		
		RequestParams params=new RequestParams();
		params.put("key",response);
		params.put("type","admin");
		
		
		WebServices ws=new WebServices(getApplicationContext());
		
		ws.invokeWebService(params, "SendApi", new  WSResponnse() {
			
			@Override
			public void onResponse(boolean success, String response) {
				
				
			}
		}, "");
		
	}
	
	
	
	

	public void onClick(View v) {
		if (v.getId() == R.id.btnLogin) {

			String netId = null, password = null;
			netId = etNetId.getText().toString();
			password = etPassword.getText().toString();
			if (!netId.isEmpty() && !password.isEmpty()) {
				if (netId.equals("admin") && password.equals("admin")) {
					Intent intent = new Intent(this, HomePage.class);
					startActivity(intent);
					sharedPref.setLoginId("admin");
					callNotificationService();
				}
			} else
				Toast.makeText(getApplicationContext(),
						"Please provide login details", Toast.LENGTH_LONG)
						.show();
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent serverLogin=new Intent(this,ServerLogin.class);
			startActivity(serverLogin);
		}
		return super.onOptionsItemSelected(item);
	}
}
