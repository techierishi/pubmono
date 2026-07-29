package com.karmick.stecs.async;

import com.karmick.stecs.common.CC;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class GetIncidentsTask extends AsyncTask<String, String, String> {
	HttpRequest jsonParser = new HttpRequest();
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	Context ctx;

	ProgressDialog dialog;
	String email_id_from_sms;

	public GetIncidentsTask(Context _ctx) {
		ctx = _ctx;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(ctx, "", "Loading. Please wait...", true);
		dialog.show();

	}

	@Override
	protected String doInBackground(String... args) {

		// Check if connected to internet
		if (CC.iNa(ctx)) {

			Log.d("request!", "starting");

			String json = jsonParser.makeHttpRequest(CC.GETINCIDENTS, "GET",
					null);

			Log.d("Login attempt", json.toString());
			return json.toString();
		} else {
			return "Internet Not Available";
		}

	}

	protected void onPostExecute(String file_url) {
		dialog.dismiss();
		IncidentReceivedListener listner = (IncidentReceivedListener) ctx;
		listner.onIncidentReceivedListener(file_url);
	}

	public interface IncidentReceivedListener {
		public void onIncidentReceivedListener(String str);
	}

}
