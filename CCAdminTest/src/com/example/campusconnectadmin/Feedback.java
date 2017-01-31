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

import com.loopj.android.http.RequestParams;

public class Feedback extends ListActivity {
	private List<String> item = null;
	private Intent intent;
	private RequestParams params;
	private ArrayAdapter<String> listAdpt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		getFeedbacks();

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

	private void getFeedbacks() {
		WebServices ws = new WebServices(getApplicationContext());

		params = new RequestParams();
		params.add("tag", "all");

		ws.invokeWebService(params, "GetFeedbacks", new WSResponnse() {
			@Override
			public void onResponse(boolean success, String response) {
				if (success)
					showFeedbacks(response);
			}
		}, "");
	}

	private void showFeedbacks(String contactList) {
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
					String text = null;
					text = "Student ID"
							+ jObj.get("feedback_student_id").toString() + "\n"
							+ "About:" + jObj.get("feedback_about").toString()
							+ "\n" + "Description:"
							+ jObj.get("feedback_description").toString()
							+ "\n";
					item.add(text);
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
