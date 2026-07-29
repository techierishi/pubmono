package com.example.sqlitedb;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageChooserActivity extends Activity {

	public ImageView imageView;
	private Button buttonNewPic;
	private Button buttonImage;

	private Bitmap image;

	public TextView imageViewName;

	private Button buttonClick;

	private static final int IMAGE_PICK = 1;
	private static final int IMAGE_CAPTURE = 2;

	private static final int PICKFILE_RESULT_CODE = 3;

	int activity_layout;
	String curFileName;
	String curFilePath;

	Dialog dialog;

	File fileOrPhotoObj;

	public void setChildsContentView(int ccv) {
		activity_layout = ccv;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void fakeOnCreate() {
		// Custom Dialog
		buttonClick = (Button) findViewById(R.id.Attach_photo_file_add);

		imageViewName = (TextView) findViewById(R.id.imageViewName);

		// add listener to button
		buttonClick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// custom dialog
				dialog = new Dialog(ImageChooserActivity.this);
				dialog.setContentView(R.layout.image_chooser_dialog);
				dialog.setTitle("Choose Option.");

				// set the custom dialog components - text, image and button

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
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
	 * Save the file in sd card
	 */

	/**
	 * Update the imageView with new bitmap
	 * 
	 * @param newImage
	 */
	private void updateImageView(Bitmap newImage) {
		StringRandomGen rs = new StringRandomGen();

		String fn = rs.generateRandomString() + ".png";
		this.image = storeImage(newImage, "" + fn);
		if (this.image != null) {
			imageViewName.setText(fn); // Keeping the name of the iMAGe here

			if (this.imageView.getVisibility() == View.VISIBLE) {
				this.imageView.setImageBitmap(this.image);
			} else {
				this.imageView.setVisibility(View.VISIBLE);
				this.imageView.setImageBitmap(this.image);
			}

		}
	}

	/**
	 * Update the imageView with new bitmap
	 * 
	 * @param newImage
	 */
	private void updateImageViewByteArray(Bitmap newImage) {

		String imgString = Base64.encodeToString(getBytesFromBitmap(newImage),
				Base64.NO_WRAP);

		this.image = newImage;
		if (this.image != null) {
			imageViewName.setText(imgString); // Keeping the base64

			if (this.imageView.getVisibility() == View.VISIBLE) {
				this.imageView.setImageBitmap(this.image);
			} else {
				this.imageView.setVisibility(View.VISIBLE);
				this.imageView.setImageBitmap(this.image);
			}

		}
	}

	// convert from bitmap to byte array
	public byte[] getBytesFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 70, stream);
		return stream.toByteArray();
	}

	/**
	 * Image result from camera
	 * 
	 * @param resultCode
	 * @param data
	 */
	private void imageFromCamera(int resultCode, Intent data) {
		this.updateImageViewByteArray((Bitmap) data.getExtras().get("data"));
	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(contentUri, proj, null,
				null, null);
		if (cursor == null)
			return null;
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
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

		this.updateImageViewByteArray(BitmapFactory.decodeFile(filePath));
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
					Intent.createChooser(intent, "Choose Image"), IMAGE_PICK);

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

	private Bitmap storeImage(Bitmap imageData, String filename) {
		// get path to external storage (SD card)
		String iconsStoragePath = Environment.getExternalStorageDirectory()
				+ "/TimeOK/Images/";
		File sdIconStorageDir = new File(iconsStoragePath);

		// create storage directories, if they don't exist
		sdIconStorageDir.mkdirs();

		try {
			String filePath = sdIconStorageDir.toString() + "/" + filename;
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);

			BufferedOutputStream bos = new BufferedOutputStream(
					fileOutputStream);

			// choose another format if PNG doesn't suit you
			imageData.compress(CompressFormat.PNG, 100, bos);

			bos.flush();
			bos.close();

		} catch (FileNotFoundException e) {
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return null;
		} catch (IOException e) {
			Log.w("TAG", "Error saving image file: " + e.getMessage());
			return null;
		}

		return imageData;
	}

}