package com.karmick.stecs.async;

import org.json.JSONObject;

import com.karmick.stecs.common.CC;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class SendMessageTask extends AsyncTask<String, String, String> {
	HttpRequest jsonParser = new HttpRequest();
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	Context ctx;

	JSONObject message;
	ProgressDialog dialog;

	String email_id_from_sms;

	public SendMessageTask(Context _ctx, JSONObject _message) {
		ctx = _ctx;
		message = _message;
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

			// Posting user data to script
			String json = jsonParser.postData(CC.SENDINCIDENTS, message);

			// full json response
			Log.d("Login attempt", json.toString());
			return json.toString();
		} else {
			return "Internet Not Available";
		}

	}

	protected void onPostExecute(String file_url) {
		dialog.dismiss();

		OnMessageSendUrlCalledListener listner = (OnMessageSendUrlCalledListener) ctx;
		listner.onMessageSendUrlCalled(file_url);
	}

	public interface OnMessageSendUrlCalledListener {
		public void onMessageSendUrlCalled(String str);
	}

}
