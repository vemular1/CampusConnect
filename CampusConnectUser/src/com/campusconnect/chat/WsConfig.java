package com.campusconnect.chat;

import android.app.Activity;

import com.campusconnect.utility.SharedPrefUtility;

public class WsConfig extends Activity {
	
    public static final String URL_WEBSOCKET = "ws://"+SharedPrefUtility.getUrl()+":8080/CampusConnectChat/chat?name=";
}
