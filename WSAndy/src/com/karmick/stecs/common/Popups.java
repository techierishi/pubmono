package com.karmick.stecs.common;

import com.karmick.stecs.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class Popups {

	public static void showOkPopup(Context ctx, String txt,
			final CustomDialogCallback obj) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		// Setting Dialog Title
		alertDialogBuilder.setTitle("Message");
		alertDialogBuilder.setIcon(R.drawable.tick);
		alertDialogBuilder.setMessage(txt);

		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						obj.onOkClick();

					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	public interface CustomDialogCallback {
		public void onOkClick();
	}

	public static void showToast(String message, Context ctx) {

		Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();

	}

}
