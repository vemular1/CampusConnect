package com.campusconnect.gcm;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.campusconnect.utility.WSResponnse;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GetApiKey extends Activity {

	GoogleCloudMessaging gcm;
	String regId;
	String PROJECT_NUMBER = "704061218817";
	String key = null;
	Context context;

	public GetApiKey(Context c) {
		// TODO Auto-generated constructor stub
		context = c;
	}

	public String getRegId(final WSResponnse dataResponse) {

		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging
								.getInstance(context);
						// Toast.makeText(getApplicationContext(), "test",
						// 1000).show();
					//	Log.i("GSMTest", "testing");
					}
					regId = gcm.register(PROJECT_NUMBER);
				//	Log.i("GSMTest", "testing");
					msg = regId;
				} catch (IOException ex) {
					msg = "Error:" + ex.getMessage();
				}
				return msg;
			}

			// //APA91bHt87HlkII7dhWUaSWgwJnVYyw1I9c5DjRB1WXlv7GyXj_-GeV5-9Sh0qcTaN9tMmbPUtbuVr0p2e6_Yi_g2tOmRFZZjVUg1w0rVg0pNGU0TfeyNkk7maEfjwJ1D9d-_A2EuXHtGbymzDegTR0MVAafzszuqw

			@Override
			protected void onPostExecute(String msg) {
				key = msg;
				dataResponse.onResponse(true, msg);
				// Log.i("test",msg);
			}
		}.execute(null, null, null);
		return key;
	}

}
