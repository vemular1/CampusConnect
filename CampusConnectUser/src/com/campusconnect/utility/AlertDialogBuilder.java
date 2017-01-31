package com.campusconnect.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class AlertDialogBuilder extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);

	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder
	        .setTitle("Test")
	        .setMessage("Are you sure you want to exit?")
	        .setCancelable(false)
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
	        {
	            public void onClick(DialogInterface dialog, int id) 
	            {
	                dialog.cancel();
	                Intent gpsOptionsIntent = new Intent(
	        				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	        		startActivity(gpsOptionsIntent);
	                
	            }
	        })
	        .setNegativeButton("No", new DialogInterface.OnClickListener() 
	        {
	            public void onClick(DialogInterface dialog, int id) 
	            {
	                dialog.cancel();
	                finish();
	            }
	        });
	    AlertDialog alert = builder.create();
	    alert.show();
	}

}
