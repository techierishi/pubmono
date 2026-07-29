package com.example.sqlitedb;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentsList extends Activity {

	private ArrayList<Student> items = new ArrayList<Student>();
	private CustomListAdapter mAdapter;
	private ListView mListView;
	Context mContext;

	Button student_add;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_list);

		initViews();

		items = getAllStudents();

		mAdapter = new CustomListAdapter(items);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> vw, View view, int position,
					long id) {

				Toast.makeText(StudentsList.this,
						" Student name : " + items.get(position).getStu_name(),
						Toast.LENGTH_LONG).show();

				Intent intt = new Intent(StudentsList.this, StudentDetail.class);
				intt.putExtra("stu_id", items.get(position).getStu_id());
				startActivity(intt);
			}
		});

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				deleteStudent(position);
				return false;
			}
		});

		eventCalls(); // Listeners

	}

	protected ArrayList<Student> getAllStudents() {
		Database_adapter mDbHelper = new Database_adapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		ArrayList<Student> stu_items = mDbHelper.getAllStudents(null, null);
		mDbHelper.close();

		return stu_items;
	}

	public void deleteStudent(final int pos) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Confirm");
		builder.setMessage("Are you sure want to delete this ?");

		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				Database_adapter mDbHelper = new Database_adapter(
						StudentsList.this);
				mDbHelper.createDatabase();
				mDbHelper.open();
				mDbHelper.delete_row_from_table("stu", "id", ""
						+ items.get(pos).getStu_id());
				items.remove(pos);
				mAdapter.changeData(items);

				mDbHelper.close();
				dialog.dismiss();
			}

		});

		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void initViews() {
		mListView = (ListView) findViewById(R.id.listView);
		student_add = (Button) findViewById(R.id.student_add);
	}

	public void eventCalls() {

		student_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intt = new Intent(StudentsList.this, AddStudent.class);
				startActivity(intt);

			}
		});
	}

	public void onSetData(View v) {
		mAdapter.changeData(items);
	}

	public class CustomListAdapter extends BaseAdapter {

		private ArrayList<Student> mData;

		public CustomListAdapter(ArrayList<Student> data) {
			mData = data;
		}

		public void setListData(ArrayList<Student> data) {
			mData = data;
		}

		public void clear() {
			mData = null;
		}

		public void changeData(ArrayList<Student> data) {
			mData = data;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			TextView stu_name;
			TextView stu_marks;
			ImageView stu_img;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.stu_list_row, null);

				holder.stu_name = (TextView) convertView
						.findViewById(R.id.stu_name);
				holder.stu_marks = (TextView) convertView
						.findViewById(R.id.stu_marks);
				holder.stu_img = (ImageView) convertView
						.findViewById(R.id.stu_img);

				convertView.setTag(R.layout.stu_list_row, holder);
			} else {
				holder = (ViewHolder) convertView.getTag(R.layout.stu_list_row);
			}
			convertView.setTag(position);

			holder.stu_name.setText("" + items.get(position).getStu_name());
			holder.stu_marks.setText("" + items.get(position).getStu_marks());
			holder.stu_img.setImageBitmap(CC.stringToBitMap(items.get(position).getStu_img()));
			return convertView;
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		items = getAllStudents();
		mAdapter.changeData(items);
	}
	
	

}
