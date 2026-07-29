package com.example.sqlitedb;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudent extends ImageChooserActivity {

	EditText student_name;
	EditText student_marks;
	Button student_add;
	private ArrayList<Student> items = new ArrayList<Student>();

	boolean editmode = false;
	int stu_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.stu_add);

		super.fakeOnCreate();
		initViews();

		Bundle extra = getIntent().getExtras();

		if (extra != null) {

			stu_id = extra.getInt("stu_id");
			if (stu_id != 0) {
				editmode = true;
				fillStuData(stu_id);
			}

		}

		eventCalls();

	}

	public void fillStuData(int stu_id) {
		Database_adapter mDbHelper = new Database_adapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		items = mDbHelper.getAllStudents("detail", "" + stu_id);
		mDbHelper.close();

		student_name.setText("" + items.get(0).getStu_name());
		student_marks.setText("" + items.get(0).getStu_marks());
	}

	public void initViews() {

		student_name = (EditText) findViewById(R.id.student_name);
		student_marks = (EditText) findViewById(R.id.student_marks);
		student_add = (Button) findViewById(R.id.student_add);

	}

	public void eventCalls() {
		student_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				validateAndSaveStudent();
			}
		});
	}

	public void validateAndSaveStudent() {

		String student_name_val = student_name.getText().toString();
		String student_marks_val = student_marks.getText().toString();

		Database_adapter mDbHelper = new Database_adapter(AddStudent.this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		Student stu_obj = new Student();
		stu_obj.setStu_name("" + student_name_val);
		stu_obj.setStu_marks("" + student_marks_val);
		
		stu_obj.setStu_img("" + imageViewName.getText());

		if (editmode) {

			stu_obj.setStu_id(stu_id);

			if (mDbHelper.update_student_data(stu_obj)) {
				Toast.makeText(AddStudent.this, " Student updated succesfully",
						Toast.LENGTH_LONG).show();
				student_name.setText("");
				student_marks.setText("");

				editmode = false;
			}
		} else {

			if (mDbHelper.insert_student_data(stu_obj)) {
				Toast.makeText(AddStudent.this, " Student  added succesfully",
						Toast.LENGTH_LONG).show();
				student_name.setText("");
				student_marks.setText("");
			}
		}
		mDbHelper.close();

	}

}
