package com.shephertz.app42.ma.cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shephertz.app42.ma.R;
import com.shephertz.app42.util.AppConstants;


public class CarttemAdapter extends BaseAdapter {
	private Context mContext;
	JSONArray jsonArr;
	private LayoutInflater inflater = null;
	
	public CarttemAdapter(Context context,JSONArray result) {
		mContext = context;
		this.jsonArr = result;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	private static class ViewHolder {
		protected TextView itemName;
		protected TextView itemPrize;
		protected TextView itemDesPrize;
		protected ImageView icon;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.cart_items, null);

			holder.itemName = (TextView) convertView
					.findViewById(R.id.item_name);
			holder.itemPrize = (TextView) convertView
					.findViewById(R.id.item_prize);

			holder.itemDesPrize = (TextView) convertView
					.findViewById(R.id.item_disc_prize);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		JSONObject data;
		try {
			data = jsonArr.getJSONObject(position);
		
		if (holder.itemName != null) {
			holder.itemName.setText(data.optString(AppConstants.ItemName, ""));
		}
		
		if (holder.itemPrize != null) {
			holder.itemPrize.setText(CartCotext.prizeUnit
					+ " "
					+ data.optDouble(AppConstants.ItemMRP, 0.0));
			holder.itemPrize.setPaintFlags(holder.itemPrize.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		
		if (holder.itemDesPrize != null) {
			holder.itemDesPrize.setText(CartCotext.prizeUnit
					+ " "
					+ data.optDouble(AppConstants.ItemPrize, 0.0));
		}
		if (holder.icon != null) 
			 holder.icon.setImageResource(R.drawable.shop1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jsonArr.length();
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

}