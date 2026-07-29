package com.karmick.stecs;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.karmick.stecs.async.GetIncidentsTask;
import com.karmick.stecs.async.GetIncidentsTask.IncidentReceivedListener;
import com.karmick.stecs.async.SendMessageTask;
import com.karmick.stecs.async.SendMessageTask.OnMessageSendUrlCalledListener;
import com.karmick.stecs.common.CC;
import com.karmick.stecs.common.Popups;
import com.karmick.stecs.common.Popups.CustomDialogCallback;

public class IncidentsActivity extends Activity implements
		IncidentReceivedListener, OnMessageSendUrlCalledListener {

	private ListView mainListView;
	private ArrayList<Incident> incidents = new ArrayList<Incident>();
	private Button Submit;
	private IncidentArrayAdapter listAdapter;
	String id;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mainListView = (ListView) findViewById(R.id.mainListView);
		Submit = (Button) findViewById(R.id.Submit);

		new GetIncidentsTask(IncidentsActivity.this).execute();

		eventCalls();
	}

	public Incident findChecked() {
		Incident locl_inc = new Incident();
		for (Incident iss : incidents) {
			if (iss.isChecked()) {
				locl_inc = iss;
			}
		}
		return locl_inc;
	}

	public void eventCalls() {
		Submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				JSONObject jobj = new JSONObject();

				try {

					JSONArray jar = new JSONArray();
					Incident iobj = findChecked();

					jar.put("" + iobj.getName());

					jobj.put("id", "" + iobj.getId());
					jobj.put("type", jar);

					JSONObject jobj2 = new JSONObject();
					jobj2.put("emaildata", jobj);

					Log.d(" JSON ", jobj.toString());

					if (jar.length() > 0) {
						new SendMessageTask(IncidentsActivity.this, jobj2)
								.execute();
					} else {
						Popups.showOkPopup(IncidentsActivity.this,
								"Please check atleast one event !",
								new CustomDialogCallback() {

									@Override
									public void onOkClick() {

									}
								});
					}

					Log.d(" JSON ", jobj.toString());
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		});
	}

	public ArrayList<Incident> uncheckRest(int position) {
		ArrayList<Incident> locl_inc = new ArrayList<Incident>();
		int i = 0;
		for (Incident is : incidents) {

			Incident is_obj = new Incident();
			if (i == position) {
				is_obj.setName(is.getName());
				is_obj.setChecked(true);
			} else {
				is_obj.setName(is.getName());
				is_obj.setChecked(false);
			}

			locl_inc.add(is_obj);
			i++;
		}

		return locl_inc;
	}

	private class IncidentArrayAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		List<Incident> incidentList;

		public IncidentArrayAdapter(Context context,
				List<Incident> _incidentList) {
			incidentList = _incidentList;
		}

		public void changeData(List<Incident> _incidentList) {
			incidentList = _incidentList;
			notifyDataSetChanged();
		}

		private class ViewHolder {
			private CheckBox checkBox;
			private TextView textView;

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder = new ViewHolder();

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.simplerow,
						null);

				holder.textView = (TextView) convertView
						.findViewById(R.id.rowTextView);
				holder.checkBox = (CheckBox) convertView
						.findViewById(R.id.CheckBox01);

				convertView.setTag(R.layout.simplerow, holder);

				holder.checkBox.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						incidents = uncheckRest(position);
						listAdapter.changeData(incidents);
					}
				});
			} else {

				holder = (ViewHolder) convertView.getTag(R.layout.simplerow);
			}

			Incident incident = incidentList.get(position);

			holder.checkBox.setTag(incident);
			holder.checkBox.setChecked(incident.isChecked());
			holder.textView.setTextColor(Color.parseColor("#FFFFFF"));
			holder.textView.setText(incident.getName());

			return convertView;
		}

		@Override
		public int getCount() {
			return incidentList.size();
		}

		@Override
		public Incident getItem(int position) {
			return incidentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

	public Object onRetainNonConfigurationInstance() {
		return incidents;
	}

	@Override
	public void onIncidentReceivedListener(String str) {

		JSONArray jArr = null;
		try {
			jArr = new JSONArray(str);

			for (int i = 0; i < jArr.length(); i++) {
				JSONObject jObj = jArr.getJSONObject(i);

				String id = jObj.getString("id");
				String caption = jObj.getString("caption");

				Incident iobj = new Incident();

				iobj.setId("" + id);
				iobj.setName("" + caption);
				iobj.setChecked(false);
				incidents.add(iobj);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

		listAdapter = new IncidentArrayAdapter(this, incidents);
		mainListView.setAdapter(listAdapter);

		mainListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View item,
							int position, long id) {
						incidents = uncheckRest(position);

						listAdapter.changeData(incidents);

					}
				});

	}

	@Override
	public void onMessageSendUrlCalled(String str) {
		Log.d(" FROM SERVER ", "" + str);
		String msg = "";
		try {
			JSONObject jobj = new JSONObject(str);

			msg = jobj.getString("msg");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Popups.showOkPopup(IncidentsActivity.this, msg,
				new CustomDialogCallback() {

					@Override
					public void onOkClick() {
						finish();

					}
				});
	}
}