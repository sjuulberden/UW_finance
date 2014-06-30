package com.unitedwardrobe.uwfinance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CostAdapter extends ArrayAdapter<Cost> {
	
	/**
	 * Holds the context to enable the adapter to access system services like the layout inflater
	 */
	private Context context;
	
	/**
	 * Holds the data displayed in the list view
	 */
	private Cost[] values;
	
	/**
	 * The constructor initializes the array adapter
	 * @param context
	 * @param values
	 */
	public CostAdapter(Context context, Cost[] values) {
		super(context, R.layout.cost_row, R.id.description, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Get or create the row view
		View rowView;
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.cost_row, parent, false);
		} else {
			rowView = convertView;
		}
		
		// Get text views
		TextView description = (TextView) rowView.findViewById(R.id.description);
		TextView amount = (TextView) rowView.findViewById(R.id.amount);
		
		// Set description
		String dValue = values[position].getDescription();
		if(dValue != null) { description.setText(dValue); }
		
		// Set amount
		String aValue = values[position].getAmount();
		if(aValue != null) { amount.setText(values[position].getAmount()); }
		
		// Return the row view
		return rowView;
		
	}
	
}
