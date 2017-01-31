package com.example.campusconnectadmin;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

public class Events extends Activity {
	TextView btnSelectDate, btnSelectTime;
	EditText etDetails;

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	String selectedDate = "", selectedTime = "";

	// variables to save user selected date and time
	public int year, month, day, hour, minute;
	// declare the variables to Show/Set the date and time when Time and Date
	// Picker Dialog first appears
	private int mYear, mMonth, mDay, mHour, mMinute;

	// constructor

	public Events() {

		// Assign current Date and Time Values to Variables
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		
		btnSelectDate = (TextView) findViewById(R.id.buttonSelectDate);
		btnSelectTime = (TextView) findViewById(R.id.buttonSelectTime);
		etDetails=(EditText) findViewById(R.id.etEventDetails);
		
		// Set ClickListener on btnSelectDate
		btnSelectDate.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				// Show the DatePickerDialog
				showDialog(DATE_DIALOG_ID);
			}
		});

		// Set ClickListener on btnSelectTime
		btnSelectTime.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				// Show the TimePickerDialog
				showDialog(TIME_DIALOG_ID);
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
				
				Intent logout = new Intent(this, Login.class);
				startActivity(logout);
				return true;
			}
			return super.onOptionsItemSelected(item);

		}

	// Register DatePickerDialog listener
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// the callback received when the user "sets" the Date in the
		// DatePickerDialog
		public void onDateSet(DatePicker view, int yearSelected,
				int monthOfYear, int dayOfMonth) {
			year = yearSelected;
			month = monthOfYear + 1;
			day = dayOfMonth;
			// Set the Selected Date in Select date Button
			selectedDate = year + "-" + month + "-" + day;
			btnSelectDate.setText("Date selected :" + selectedDate);
		}
	};

	// Register TimePickerDialog listener
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		// the callback received when the user "sets" the TimePickerDialog in
		// the dialog
		public void onTimeSet(TimePicker view, int hourOfDay, int min) {
			hour = hourOfDay;
			minute = min;
			selectedTime = hour + ":" + minute;
			// Set the Selected Date in Select date Button
			btnSelectTime.setText("Time selected :" + selectedTime);
		}
	};
	private Intent intent;
	private RequestParams params;
	private WebServices ws;
	private SharedPrefUtility sharedPref;

	// Method automatically gets Called when you call showDialog() method
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// create a new DatePickerDialog with values you want to show
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
			// create a new TimePickerDialog with values you want to show
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);

		}
		return null;
	}
	
	
	public void onClick(View v){
		sendEvent();
		
		
	}
	
	
	
	private void sendEvent() {

		intent = new Intent(this, HomePage.class);
		params = new RequestParams();
		ws = new WebServices(getApplicationContext());
		sharedPref = new SharedPrefUtility(getApplicationContext());

		params.add("details", etDetails.getText().toString());
		params.add("date", selectedDate);
		params.add("time", selectedTime);

		ws.invokeWebService(params, "RegisterEvent", new WSResponnse() {
			@Override
			public void onResponse(boolean success, String response) {
				Toast.makeText(getApplicationContext(), "Event Registered",
						Toast.LENGTH_LONG).show();

				startActivity(intent);
			}
		}, "");
	}
}
