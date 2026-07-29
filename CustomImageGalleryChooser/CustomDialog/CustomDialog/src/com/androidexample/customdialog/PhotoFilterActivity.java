package com.androidexample.customdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoFilterActivity extends Activity {

	private ImageView imageView;
	private Button buttonNewPic;
	private Button buttonImage;

	private Bitmap image;

	private Button buttonClick;

	private static final int IMAGE_PICK = 1;
	private static final int IMAGE_CAPTURE = 2;
	
	Dialog dialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_dialog_main);

		// Custom Dialog
		buttonClick = (Button) findViewById(R.id.buttonClick);

		// add listener to button
		buttonClick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// custom dialog
				dialog = new Dialog(PhotoFilterActivity.this);
				dialog.setContentView(R.layout.custom_dialog);
				dialog.setTitle("Custom Dialog");

				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.textDialog);
				text.setText("Custom dialog Android example.");
				ImageView image = (ImageView) dialog
						.findViewById(R.id.imageDialog);
				image.setImageResource(R.drawable.image0);

				dialog.show();

				Button declineButton = (Button) dialog
						.findViewById(R.id.declineButton);
				// if button is clicked, close the custom dialog
				declineButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				buttonNewPic = (Button) dialog.findViewById(R.id.button_camera);
				buttonImage = (Button) dialog
						.findViewById(R.id.button_from_phone);

				buttonImage.setOnClickListener(new ImagePickListener());
				buttonNewPic.setOnClickListener(new TakePictureListener());
			}

		});

		// Custom Dialog ends

		this.imageView = (ImageView) this.findViewById(R.id.imageView);

	}

	/**
	 * Receive the result from the startActivity
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case IMAGE_PICK:
				this.imageFromGallery(resultCode, data);
				break;
			case IMAGE_CAPTURE:
				this.imageFromCamera(resultCode, data);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Update the imageView with new bitmap
	 * 
	 * @param newImage
	 */
	private void updateImageView(Bitmap newImage) {
		BitmapProcessor bitmapProcessor = new BitmapProcessor(newImage, 1000,
				1000, 90);

		this.image = bitmapProcessor.getBitmap();
		this.imageView.setImageBitmap(this.image);
	}

	/**
	 * Image result from camera
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void imageFromCamera(int resultCode, Intent data) {
		this.updateImageView((Bitmap) data.getExtras().get("data"));
	}

	/**
	 * Image result from gallery
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void imageFromGallery(int resultCode, Intent data) {
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();

		this.updateImageView(BitmapFactory.decodeFile(filePath));
	}

	/**
	 * Click Listener for selecting images from phone gallery
	 * 
	 * @author tscolari
	 * 
	 */
	class ImagePickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			dialog.dismiss();
			
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			startActivityForResult(
					Intent.createChooser(intent, "Escolha uma Foto"),
					IMAGE_PICK);

		}
	}

	/**
	 * Click listener for taking new picture
	 * 
	 * @author tscolari
	 * 
	 */
	class TakePictureListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			dialog.dismiss();
			Intent intent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, IMAGE_CAPTURE);

		}
	}
}