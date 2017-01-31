package com.example.campusconnect;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.campusconnect.utility.SharedPrefUtility;
import com.campusconnect.utility.WSResponnse;
import com.campusconnect.utility.WebServices;
import com.loopj.android.http.RequestParams;

public class RecentContacts extends ListActivity   {
	private List<String> item = null;
	private List<String> path = null;
	TextView contactname;
	private Intent intent;
	private RequestParams params;
	private ArrayAdapter<String> contactListAdpt;
	EditText etSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recent_contacts);
		contactname = (TextView) findViewById(R.id.rowtext);
		etSearch=(EditText) findViewById(R.id.etSearchContact);		
		getContacts("all","");
		
		etSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				getContacts("all",etSearch.getText().toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		

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
	
	private void getContacts(String tag,String tagValue) {
		WebServices ws = new WebServices(getApplicationContext());
		intent = new Intent(this, WelcomePage.class);
		params = new RequestParams();
		params.add("tag", tag);
		params.add("tag_value", tagValue);

		ws.invokeWebService(params, "GetContacts", new WSResponnse() {
			@Override
			public void onResponse(boolean success, String response) {
				if (success)			
				showContacts(response);
			}
		}, "");
	}

	private void showContacts(String contactList) {

		item = new ArrayList<String>();

		JSONArray jArray = null;
		try {
			jArray = new JSONArray(contactList);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// add contacts to array list by parsing json

		if (jArray != null) {
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObj = new JSONObject();

				try {
					jObj = jArray.getJSONObject(i);

					item.add(jObj.get("user_email_id").toString());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 contactListAdpt = new ArrayAdapter<String>(
					this, R.layout.contact_row, item);
			setListAdapter(contactListAdpt);
		}
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		//Log.e("test",item.get(position));
		
		//go to collaboration page
		
		Intent intent=new Intent(RecentContacts.this,ContactCommunication.class);
		intent.putExtra("email_id", item.get(position));
		startActivity(intent);
	}



	






	
	
	
	

}
