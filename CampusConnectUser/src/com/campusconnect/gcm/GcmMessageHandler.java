package com.campusconnect.gcm;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.campusconnect.NotificationEvent;
import com.example.campusconnect.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class  GcmMessageHandler extends IntentService {

	NotificationManager nManager;
	SharedPreferences sharedPref;
	SharedPreferences.Editor sharedPrefEdit;
	//DBHelper db;
	private final String COUNTER="COUNTER";
	int counter=0;
	
	 
	public  GcmMessageHandler(String name) {
		super("GcmMessageHandler");
		// TODO Auto-generated constructor stub
	}
	
	public GcmMessageHandler() {
		// TODO Auto-generated constructor stub
		super("");
	}
	
	String mes;
	private Handler handler;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
		nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sharedPref=getSharedPreferences(COUNTER, Activity.MODE_PRIVATE);
        sharedPrefEdit=sharedPref.edit();
        counter=sharedPref.getInt(COUNTER, 0);
       // db=new DBHelper(getBaseContext());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);		
		String messageType=gcm.getMessageType(intent);

//		for (int i=0;i<extras.size()-1;i++){
	//		mes="hi"+extras.toString();
		//}
		mes = extras.getString("title"); 

		if (mes!=null){
		
	//	mes="hi";
	//	showToast();
		
		// store in to DB
		//db.insertData(String.valueOf(counter), mes);
		// Show Notification here
		
		try {
			JSONObject jObj=new JSONObject(mes);
			String tag=jObj.getString("tag");
			String tag_value=jObj.getString("tag_value");
			
			createNotification(tag+": "+tag_value);	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		
		
		
						
	//	Log.i("GCM","Recieved:"+messageType+" "+mes);
		GCMReciever.completeWakefulIntent(intent);
	}	

	public void showToast() {
		// TODO Auto-generated method stub
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
			  Toast.makeText(getApplicationContext(), mes, 10000).show();	
			}
		});
	}

	public void createNotification(String mes) {

	//	Toast.makeText(this, "Notification ", 40).show();
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		Intent intent = new Intent(Intent.ACTION_DIAL,
				Uri.parse("tel:(+49)12345789"));
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
		
		Intent myIntent=new Intent(this, NotificationEvent.class);
		PendingIntent pi1 = PendingIntent.getActivity(this, 0, myIntent, 0);		
		
		NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
		
		notification.setContentTitle("New Event");
		counter++;
		notification.setContentText(mes);
		
		notification.setTicker("new message");
		notification.setNumber(counter);
		notification.setSound(soundUri);

		notification.setSmallIcon(R.drawable.montclair);
		notification.setContentIntent(pi1);
		notification.setAutoCancel(true);
		notification.build();
		Notification notify = notification.build();
		
		//Vibrate while notified		
		//Vibrator vibrate=(Vibrator)getSystemService(VIBRATOR_SERVICE);
		//vibrate.vibrate(5000);		
		sharedPrefEdit.putInt(COUNTER, counter);		
		nManager.notify(counter, notify);			
	}
}
