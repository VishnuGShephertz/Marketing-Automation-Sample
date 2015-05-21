package com.shephertz.app42.ma.cart;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shephertz.app42.ma.R;

public class MyCartListAdapter extends BaseAdapter {
	private Context mContext;
	
	private ArrayList<MyCartItem> cartItemList;
	private LayoutInflater inflater = null;
	
	public MyCartListAdapter(Context context,
			 ArrayList<MyCartItem> cartItemList) {
		mContext = context;
		this.cartItemList = cartItemList;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.my_cart_items, null);

			holder.itemName = (TextView) convertView
					.findViewById(R.id.item_name);
		//	holder.count = (TextView) convertView.findViewById(R.id.item_count);
			holder.itemPrize = (TextView) convertView
					.findViewById(R.id.item_prize);
			holder.totalPrize = (TextView) convertView
					.findViewById(R.id.item_total_prize);

			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.delete=(ImageView)convertView.findViewById(R.id.remove_btn);
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder)convertView.getTag();
		}

		final MyCartItem item = cartItemList.get(position);
		

		if (holder.itemName != null) {

			holder.itemName.setText(item.getName());
		}
		if (holder.itemPrize != null) {
			holder.itemPrize .setText(CartCotext.prizeUnit + " "
					+ item.getDiscountedPrize()+" X "+item.getQuantity());
		}

		if (holder.totalPrize != null) {
			holder.totalPrize.setText(CartCotext.prizeUnit
					+ " "
					+ item.getDiscountedPrize()
							 * item.getQuantity());
		}
		if (holder.icon != null) {
			holder.icon.setVisibility(View.GONE);
		}
	
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MyCartList)mContext).showDialogPopup(item.getId(), position);
			}
		});
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cartItemList.size();
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
		protected TextView itemPrize;
		//protected TextView count;
		protected TextView totalPrize;
		protected ImageView icon;
		protected ImageView delete;

	}

}