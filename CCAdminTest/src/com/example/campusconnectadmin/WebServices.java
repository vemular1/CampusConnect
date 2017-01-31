package com.example.campusconnectadmin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WebServices extends Activity {

	/**
	 * Method that performs RESTful webservice invocations
	 * 
	 * @param params
	 */

	Context context;
	List<String> data;
	SharedPrefUtility sharedPref;

	public WebServices(Context c) {
		this.context = c;
		sharedPref = new SharedPrefUtility(c);
		data = new ArrayList<String>();
	}

	public List<String> invokeWebService(RequestParams params,
			final String tag, final WSResponnse dataResponse,
			final String... tagValues) {
		// Show Progress Dialog

		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();

		Log.e("test", getUrl(tag));

		client.get(getUrl(tag), params, new AsyncHttpResponseHandler() {
			// When the response returned by REST has Http response code
			// '200'
			@Override
			public void onSuccess(String response) {
				// Hide Progress Dialog
				Log.i("test", response + " is the data");

				try {
					data = parseJSON(response, tag, tagValues);
					if (data.size() > 0)
						dataResponse.onResponse(true, data.get(0));

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//Toast.makeText(
						//	context,
							//"Error Occured [Server's JSON response might be invalid]!",
							//Toast.LENGTH_LONG).show();
					e.printStackTrace();

				}
			}

			// When the response returned by REST has Http response code
			// other than '200'
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				// Hide Progress Dialog
				Log.i("test", String.valueOf(statusCode) + "is status code");
				// When Http response code is '404'
				if (statusCode == 404) {
					Toast.makeText(context,
							"Requested resourc" + "e not found",
							Toast.LENGTH_LONG).show();
				}
				// When Http response code is '500'
				else if (statusCode == 500) {
					Toast.makeText(context,
							"Something went wrong at server end",
							Toast.LENGTH_LONG).show();
				}
				// When Http response code other than 404, 500
				else {
					Toast.makeText(
							context,
							"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		return data;
	}

	public List<String> parseJSON(String response, String tag,
			String... tagValues) throws JSONException {
		List<String> values = new ArrayList<String>();
		// JSON Object
		if (!tag.equals("GetEmergencies") && !tag.equals("GetSuggessions")
				&& !tag.equals("GetFeedbacks")&& !tag.equals("GetContacts")) {

			Log.i("test", response);

			JSONObject obj = new JSONObject(response);
			// When the JSON response has status boolean value assigned with
			// true
			// if (obj.getBoolean("status")) {
			// Set Default Values for Edit View controls
			// Display successfully registered message using Toast
			if (tag.equalsIgnoreCase("RegisterEvent")) {
				if (obj.getBoolean("status"))
					values.add("done");
				else
					Toast.makeText(context,
							"Event not registered. Please try again.",
							Toast.LENGTH_LONG).show();
			}
		} else if (tag.equalsIgnoreCase("GetEmergencies")) {
			values.add(response);
		} else if (tag.equalsIgnoreCase("GetSuggessions")
				|| tag.equalsIgnoreCase("GetFeedbacks")  || tag.equalsIgnoreCase("GetContacts")) {
			values.add(response);
		}
		return values;
	}

	private void showToast(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public String getUrl(String tag) {
		String url = null;

		SharedPrefUtility sharedPref = new SharedPrefUtility(context);
		String urlIp = "http://" + sharedPref.getUrl() + ":8080"; // +

		if (tag.equalsIgnoreCase("RegisterEvent")) {
			url = urlIp + "/CampusConnect/Services/doRegisterEvent";
		} else if (tag.equalsIgnoreCase("GetSuggessions")) {
			url = urlIp + "/CampusConnect/Services/doGetSuggessions";
		} else if (tag.equalsIgnoreCase("GetFeedbacks")) {
			url = urlIp + "/CampusConnect/Services/doGetFeedbacks";
		} else if (tag.equalsIgnoreCase("GetEmergencies")) {
			url = urlIp + "/CampusConnect/Services/doGetEmergencies";
		}else if (tag.equalsIgnoreCase("SendApi")) {
			url = urlIp + "/CampusConnect/Services/doSendApi";
		}else if (tag.equalsIgnoreCase("GetContacts")) {
			url = urlIp + "/CampusConnect/Services/doGetContacts";
		}
		return url;
	}
}
