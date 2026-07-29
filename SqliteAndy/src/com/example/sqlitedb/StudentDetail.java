package com.example.sqlitedb;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentDetail extends Activity {

	TextView student_name;
	TextView student_marks;
	ImageView stu_imageView;
	Button student_update;
	private ArrayList<Student> items = new ArrayList<Student>();

	int stu_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_detail);
		initViews();

		Bundle extra = getIntent().getExtras();

		if (extra != null) {
			stu_id = extra.getInt("stu_id");
		}

		Database_adapter mDbHelper = new Database_adapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		items = mDbHelper.getAllStudents("detail", "" + stu_id);
		mDbHelper.close();

		student_name.setText("" + items.get(0).getStu_name());
		student_marks.setText("" + items.get(0).getStu_marks());
		stu_imageView.setImageBitmap(CC.stringToBitMap(items.get(0).getStu_img()));

		student_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intt = new Intent(StudentDetail.this, AddStudent.class);
				intt.putExtra("stu_id", items.get(0).getStu_id());
				startActivity(intt);
			}
		});

	}

	public void initViews() {

		student_name = (TextView) findViewById(R.id.student_name);
		student_marks = (TextView) findViewById(R.id.student_marks);
		stu_imageView = (ImageView) findViewById(R.id.stu_imageView);

		student_update = (Button) findViewById(R.id.student_update);
	}
}
