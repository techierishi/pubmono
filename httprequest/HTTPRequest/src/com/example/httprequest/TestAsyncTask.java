package com.example.httprequest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class TestAsyncTask extends AsyncTask<String, String, String> {
	HTTPRequest jsonParser = new HTTPRequest();
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	Context ctx;

	String url;
	String message;

	String email_id_from_sms;

	public TestAsyncTask(Context _ctx, String _url, String _message) {
		ctx = _ctx;
		message = _message;
		url = _url;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected String doInBackground(String... args) {

		try {

			if (iNa(ctx)) {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("emaildata", message));

				Log.d("request!", "starting");

				JSONObject jsonobj = new JSONObject();
				jsonobj.put("username", "Rishi");
				jsonobj.put("password", "P@$$W0RD");

				// Posting user data to script
				String json = jsonParser.postData(url, jsonobj);

				// full json response
				Log.d("Login attempt", json.toString());

				return json.toString();
			} else {
				return "Internet Not Available";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}

	protected void onPostExecute(String res) {
		OnMessageReceivedListener listner = (OnMessageReceivedListener) ctx;
		listner.onMessageReceived(res);
	}

	public interface OnMessageReceivedListener {
		public void onMessageReceived(String str);
	}

	public static boolean iNa(Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
