package com.unitedwardrobe.uwfinance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call parent method
		super.onCreate(savedInstanceState);
		
		// Set content view
		setContentView(R.layout.activity_main);
		
		// Create network listener (shows 'connecting' dialog when connection gets lost)
		new NetworkListener(this);
		
		// Get intent
		Intent intent = getIntent();
		
		// Get message
		String message = intent.getStringExtra("message");
		
		// If a message was set, show it
		if(message != null && !message.equals("")) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
		
		// Make sure the message is only shown once by setting the message to an empty string
		intent.putExtra("message", "");
		
	}
	
	/**
	 * Method called when "add costs"-button is pressed
	 * @param v Button-view that was pressed
	 */
	public void add(View v) {
		Intent intent = new Intent(this, AddCostActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Method called when "look up costs"-button was pressed
	 * @param v Button-view that was pressed
	 */
	public void lookUp(View v) {
		Intent intent = new Intent(this, LookUpCostActivity.class);
		startActivity(intent);
	}
	
}
