package com.example.campusconnectadmin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.loopj.android.http.RequestParams;

public class EmergencyReports extends ListActivity {
	private List<String> item = null;
	private Intent intent;
	private RequestParams params;
	private ArrayAdapter<String> listAdpt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_reports);
		getEmergencies();
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
				
				Intent logout = new Intent(this, Login.class);
				startActivity(logout);
				return true;
			}
			return super.onOptionsItemSelected(item);

		}

	private void getEmergencies() {
		WebServices ws = new WebServices(getApplicationContext());

		params = new RequestParams();
		params.add("tag", "all");

		ws.invokeWebService(params, "GetEmergencies", new WSResponnse() {
			@Override
			public void onResponse(boolean success, String response) {
				if (success)
					showEmergencies(response);
			}
		}, "");
	}

	private void showEmergencies(String contactList) {
		item = new ArrayList<String>();
		JSONArray jArray = null;
		try {
			jArray = new JSONArray(contactList);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		if (jArray != null) {
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObj = new JSONObject();
				try {
					jObj = jArray.getJSONObject(i);
					String emergencyText = null;
					emergencyText = "Emergency Category:"
							+ jObj.get("emergency_category").toString() + "\n"
							+ "Emergency Priority:"
							+ jObj.get("emergency_priority").toString() + "\n"
							+ "Student Id :"
							+ jObj.get("emergency_student_id").toString() + "\n"
							+ "Time:" + jObj.get("emergency_timestamp").toString();

					item.add(emergencyText);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			listAdpt = new ArrayAdapter<String>(this, R.layout.row, item);
			setListAdapter(listAdpt);
		}
	}

}
