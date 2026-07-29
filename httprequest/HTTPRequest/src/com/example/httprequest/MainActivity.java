package com.example.httprequest;

import com.example.httprequest.TestAsyncTask.OnMessageReceivedListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnMessageReceivedListener {

	Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String _url = "http://10.0.2.2/rest/rest.php";
				new TestAsyncTask(MainActivity.this, _url, "").execute();
			}
		});
	}

	@Override
	public void onMessageReceived(String str) {
		Toast.makeText(this, "" + str, Toast.LENGTH_LONG).show();
	}

}
