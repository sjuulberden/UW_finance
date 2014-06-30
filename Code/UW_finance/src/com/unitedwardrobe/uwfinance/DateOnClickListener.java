package com.unitedwardrobe.uwfinance;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class DateOnClickListener implements OnClickListener {
	
	private int year;
	private int month;
	private int day;
	
	private DatePickerDialog dialog;
	
	public DateOnClickListener(final EditText field, Context context) {
		
		// Use current date as default date
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create dialog
		dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				
				// Save picked date
				DateOnClickListener.this.year = year;
				month = monthOfYear;
				day = dayOfMonth;
				
				// Set text field to picked date
				field.setText(year + "-" + (month+1) + "-" + day);
				
			}
			
		}, year, month, day);
	}
	
	@Override
	public void onClick(View v) {
		
		// Show dialog
		dialog.show();
		
	}

}
