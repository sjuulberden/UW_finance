package com.unitedwardrobe.uwfinance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddCostActivity extends Activity {
	
	private EditText description;
	private EditText amount;
	private EditText from;
	private EditText to;
	private Button setFrom;
	private Button setTo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call parent method
		super.onCreate(savedInstanceState);
		
		// Set content view
		setContentView(R.layout.activity_addcost);
		
		// Create network listener (shows 'connecting' dialog when connection gets lost)
		new NetworkListener(this);
		
		// Get references to the views
		description = (EditText) findViewById(R.id.description);
		amount = (EditText) findViewById(R.id.amount);
		from = (EditText) findViewById(R.id.from);
		to = (EditText) findViewById(R.id.to);
		setFrom = (Button) findViewById(R.id.setFrom);
		setTo = (Button) findViewById(R.id.setTo);
		
		// Set on-click listeners for the set-buttons
		setFrom.setOnClickListener(new DateOnClickListener(from, this));
		setTo.setOnClickListener(new DateOnClickListener(to, this));
		
	}
	
	/**
	 * Method called when "add"-button is pressed
	 * @param v Button-view that was pressed
	 */
	public void add(View v) {
		
		// Check if all fields were entered
		if(from.getText().toString().equals("") || to.getText().toString().equals("") || description.getText().toString().equals("") || amount.getText().toString().equals("")) {
			Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// Check if the dates have a logical order
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			Date fromDate = sdf.parse(from.getText().toString());
			Date toDate = sdf.parse(to.getText().toString());
			
			// Check date order
			if(fromDate.after(toDate)) {
				Toast.makeText(this, "The 'from' date should be after the 'to' date.", Toast.LENGTH_SHORT).show();
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Get a HTTP client
		AsyncHttpClient client = new AsyncHttpClient();
		
		// Set request (post) parameters
		RequestParams params = new RequestParams();
		params.put("description", description.getText().toString());
		params.put("amount", amount.getText().toString());
		params.put("from", from.getText().toString());
		params.put("to", to.getText().toString());
		
		// For very low-level 'security' we include this secret key. This way only the app can add things through the API 
		params.put("api_key", "lyqnHWqCDONqtOSubFS1");
		
		// Show loading dialog
		final ProgressDialog progress = ProgressDialog.show(this, "Contacting server", "Sending request");
		
		// Do the post request
		client.post("http://www.unitedwardrobe.fr/sjuul/add.php", params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, final byte[] response) {
				
				// Request succeeded, continue code on UI thread
				AddCostActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						// JSON conversion and byte-array to UTF-8 string conversion may throw errors
						try {
							
							// Get response as JSON
							JSONObject json = new JSONObject(new String(response, "UTF-8"));
							
							// Check if the server was successful
							if(json.getBoolean("success")) {
								// Create intent
								Intent intent = new Intent(AddCostActivity.this, MainActivity.class);
								
								// Set message to show when activity starts
								intent.putExtra("message", json.getString("msg"));
								
								// Start main activity
								startActivity(intent);
							} else {
								// Show error message that the server returned
								Toast.makeText(getApplicationContext(), json.getString("msg"), Toast.LENGTH_SHORT).show();
							}
							
						} catch (Exception e) {
							// Failed, show error
							Toast.makeText(getApplicationContext(), "Could not read server response.", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
						
						// Dismiss progress dialog
						progress.dismiss();
					}
					
				});
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] response, final Throwable error) {
				// Request failed, show error on UI thread
				AddCostActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// Show error
						Toast.makeText(getApplicationContext(), "Could not add costs to server. Error: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
						
						// Dismiss progress dialog
						progress.dismiss();
					}
				});
				
			}
		});
		
	}
	
}
