package com.unitedwardrobe.uwfinance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkListener {
	
	private Activity activity;
	private ProgressDialog progress;
	
	public NetworkListener(Activity activity) {
		
		Log.d("UW", "Network Listener created");
		
		this.activity = activity;
		
		progress = ProgressDialog.show(activity, "No internet.", "Waiting for connection...");
		
		activity.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		
	}
	
	private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			
			Log.d("UW", "onReceive called");
			
			// Get network info
			ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			
			// Check if we have a connection
			if(ni != null && ni.isConnected()) {
				// We have a connection, hide progress dialog
				progress.hide();
			} else {
				// We have no connection, show progress dialog
				progress.show();
			}
		}
	};
	
}
