package com.unitedwardrobe.uwfinance;

import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ViewCostsActivity extends Activity {
	
	/**
	 * Text view that contains the time span
	 */
	private TextView timespan;
	
	/**
	 * List view that shows the costs
	 */
	private ListView costs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call parent method
		super.onCreate(savedInstanceState);
		
		// Set content view
		setContentView(R.layout.activity_viewcosts);
		
		// Create network listener (shows 'connecting' dialog when connection gets lost)
		new NetworkListener(this);
		
		// Get intent
		Intent intent = getIntent();
		
		// Get references to the views in the layout
		timespan = (TextView) findViewById(R.id.timespan);
		costs = (ListView) findViewById(R.id.costs);
		
		// Set time span
		timespan.setText(intent.getStringExtra("timespan"));
		
		// Get currency formatter
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
		
		// Get the costs
		Cost[] values = null;
		try {
			// Get JSON array with results
			JSONArray json = new JSONArray(intent.getStringExtra("costs"));
			
			// Initialize value array
			values = new Cost[json.length()];
			
			// Add every result to the array
			for(int i = 0; i < json.length(); i++) {
				JSONObject obj = json.getJSONObject(i);
				values[i] = new Cost(obj.getString("description"), format.format(Double.valueOf(obj.getString("amount"))));
			}
			
		} catch(Exception e) {
			// TODO: handle error
			e.printStackTrace();
		}
		
		// Check if any results are found
		if(values == null || values.length == 0) {
			values = new Cost[] { new Cost("No results found.", "") };
		}
		
		// Set list adapter
		costs.setAdapter(new CostAdapter(this, values));
		
	}
}
