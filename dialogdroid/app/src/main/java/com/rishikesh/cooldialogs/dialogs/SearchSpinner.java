package com.rishikesh.cooldialogs.dialogs;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rishikesh.cooldialogs.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Administrator on 11/9/2015.
 */
public class SearchSpinner {

    private SearchListAdpater dataAdapter = null;

    public void searchSpinner(Context ctx,View search_spinner,final ArrayList<SearchPopup.SearchKeyValue> keyValList,final CustomSearchDialogCallback obj ){
        LayoutInflater layoutInflater
                = (LayoutInflater) ctx
                .getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.search_spinner, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.setWidth(search_spinner.getWidth() + 20);

       popupWindow.setAnimationStyle(R.style.spinner_animation);


        ListView listView = (ListView) popupView.findViewById(R.id.listView1);

        dataAdapter = new SearchListAdpater(ctx, keyValList);
        listView.setAdapter(dataAdapter);
        listView.setDivider(null);
        listView.setDividerHeight(0);

        listView.setTextFilterEnabled(true);



        final EditText myFilter = (EditText) popupView.findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                dataAdapter.filter(s.toString());

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                popupWindow.dismiss();
                obj.onItemClick(keyValList.get(position));
            }
        });

        popupWindow.showAsDropDown(search_spinner, -10, -15);
    }

    public interface CustomSearchDialogCallback {
        public void onItemClick(SearchPopup.SearchKeyValue svObj);
    }


    class SearchListAdpater extends BaseAdapter {

        private Activity activity;
        private ArrayList<SearchPopup.SearchKeyValue> data;
        private ArrayList<SearchPopup.SearchKeyValue> arraylist;

        private LayoutInflater inflater = null;
        SearchPopup.SearchKeyValue tempValues = null;
        int i = 0;

        public SearchListAdpater(Context a, ArrayList<SearchPopup.SearchKeyValue> d) {

            activity = (Activity) a;

            Log.d(ConstantClass.LOGTAG, "" + d.toString());
            this.data = d;

            this.arraylist = new ArrayList<SearchPopup.SearchKeyValue>();
            this.arraylist.addAll(d);

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        // Filter Class
        public void filter(String charText) {

            Log.d(ConstantClass.LOGTAG, "Function calles");

            charText = charText.toLowerCase(Locale.getDefault());
            data.clear();
            if (charText.length() == 0) {
                data.addAll(arraylist);
            } else {
                for (SearchPopup.SearchKeyValue kv : arraylist) {
                    if (kv.getValue().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        data.add(kv);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public int getCount() {

            if (data != null) {
                if (data.size() <= 0)
                    return 1;
                else
                    return data.size();
            }
            return 1;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {

            public TextView key;
            public TextView value;

        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View vi = convertView;
            ViewHolder holder;

            if (convertView == null) {

                vi = inflater.inflate(R.layout.dialog_searchlist_row, null);

                holder = new ViewHolder();
                holder.key = (TextView) vi.findViewById(R.id.key);
                holder.value = (TextView) vi.findViewById(R.id.value);

                vi.setTag(holder);

            } else
                holder = (ViewHolder) vi.getTag();

            if (data != null) {
                if (data.size() <= 0) {

                    holder.key.setText("No Data");
                    holder.value.setText("");

                } else {
                    tempValues = null;
                    tempValues = (SearchPopup.SearchKeyValue) data.get(position);

                    holder.key.setText("" + tempValues.getKey());
                    holder.value.setText(tempValues.getValue());

                }
            } else {
                holder.key.setText("No Data");
                holder.value.setText("");

            }

            return vi;
        }

    }

}
