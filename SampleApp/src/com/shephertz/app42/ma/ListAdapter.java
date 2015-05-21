package com.shephertz.app42.ma;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private Activity activity;
	private String[] items;
	private static LayoutInflater inflater = null;

	public ListAdapter(Activity a, String[] appName) {
		activity = a;
		items = appName;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return items.length;
	}

	public Object getItem(int position) {
		return items[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView text1;

		// public ImageView image;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(
					R.layout.custom_row_view_for_list_of_projects, null);
		
			holder = new ViewHolder();
			holder.text1 = (TextView) vi.findViewById(R.id.text11);
		
			vi.setTag(holder);

		} else

			holder = (ViewHolder) vi.getTag();

		holder.text1.setText("  "+items[position].trim());

		return vi;
	}
}