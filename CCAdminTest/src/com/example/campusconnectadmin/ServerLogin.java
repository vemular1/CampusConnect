package com.example.campusconnectadmin;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;




public class ServerLogin extends Activity {

    EditText et_IPUpdate;
    LinearLayout ll_IPVisible;
    SharedPrefUtility sharedPref;
    String updateIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_login);

        
        et_IPUpdate = (EditText) findViewById(R.id.et_ServerIP);
        sharedPref = new SharedPrefUtility(getApplicationContext());
        
    }

    public void onClick(View v) throws Exception {
        if (v.getId() == R.id.btn_Update) {
    
        	String ip=et_IPUpdate.getText().toString();
        	
            if (!ip.isEmpty()){
            	Toast.makeText(getApplicationContext(), "Details Updated", Toast.LENGTH_LONG).show();            	            
            	sharedPref.setUrl(ip);            	
            }
            else  Toast.makeText(getApplicationContext(), "Please enter IP address", Toast.LENGTH_LONG).show();        	        	
        }
    }

}
