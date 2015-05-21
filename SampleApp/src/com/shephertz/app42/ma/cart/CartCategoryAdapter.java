package com.shephertz.app42.ma.cart;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shephertz.app42.ma.R;

public class CartCategoryAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> categoryList;
	private LayoutInflater inflater = null;

	public CartCategoryAdapter(Context context, ArrayList<String> result) {
		mContext = context;
		this.categoryList = result;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.cart_cat_items, null);
			holder.itemName = (TextView) convertView
					.findViewById(R.id.cat_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (holder.itemName != null) {
			holder.itemName
					.setText(categoryList.get(position).trim());
		}
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categoryList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private static class ViewHolder {
		protected TextView itemName;

	}
}