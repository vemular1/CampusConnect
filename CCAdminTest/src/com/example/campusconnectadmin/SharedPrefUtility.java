package com.example.campusconnectadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tlc on 23/4/15.
 */
public class SharedPrefUtility {

	Context c;
	static SharedPreferences sharedPref;
	private final String SHARED_LIB = "DETAILS";
	private final static String IP = "IP";
	private final String LOGINID = "LOGINID";


	public SharedPrefUtility(Context context) {
		c = context;

		sharedPref = c.getSharedPreferences(SHARED_LIB, Activity.MODE_PRIVATE);
	}
	
	

	public String getLoginId() {

		return sharedPref.getString(LOGINID, "");
	}

	public void setLoginId(String loginId) {

		SharedPreferences.Editor edit = sharedPref.edit();
		edit.putString(LOGINID, loginId);
		edit.commit();
	}

	public void setUrl(String ip) {
		SharedPreferences.Editor edit = sharedPref.edit();
		edit.putString(IP, ip);
		edit.commit();
	}



	public static String getUrl() {
		return sharedPref.getString(IP, "");// +Constants.getWebServiceURl();
	}

	

}
