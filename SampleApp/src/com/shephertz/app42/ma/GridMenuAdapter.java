package com.shephertz.app42.ma;

import java.util.Vector;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shephertz.app42.util.AppContext;

public class GridMenuAdapter extends BaseAdapter {
	private Context mContext;

	// Keep all Images in array
	private Vector<String> vctr_Icon_Text;

	private int gridWidth;
	private final int GridSpace = 20;
     private int[] iconArr={R.drawable.shop1,R.drawable.shop2,R.drawable.contact1};
	// Constructor
	public GridMenuAdapter(Context context, Vector<String> iconText,
			 int gridWidth) {
		mContext = context;
		vctr_Icon_Text = iconText;
		
		this.gridWidth = gridWidth - GridSpace;
		
	}

	@Override
	public int getCount() {
		return vctr_Icon_Text.size();
	}

	@Override
	public Object getItem(int position) {
		return vctr_Icon_Text.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LinearLayout linLay = new LinearLayout(mContext);
	//	linLay.setBackgroundColor(Color.GRAY);
		linLay.setOrientation(LinearLayout.VERTICAL);
		linLay.setGravity(Gravity.CENTER);
		linLay.setPadding(GridSpace, GridSpace*2, 0, 0);
		//linLay.setBackgroundColor(Color.argb(250, 204, 204, 204));
		ImageView imageView = new ImageView(mContext);
		imageView.setLayoutParams(new LinearLayout.LayoutParams(gridWidth,
				gridWidth));
			imageView.setImageResource(iconArr[position]);
		
		TextView tvSerNumber = new TextView(mContext);
		tvSerNumber.setText(formatName(vctr_Icon_Text.get(position).trim()));
		
		tvSerNumber.setGravity(Gravity.CENTER);
		tvSerNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		tvSerNumber.setSingleLine(true);
		tvSerNumber.setTextColor(AppContext.textColor);
		linLay.addView(imageView);
		linLay.addView(tvSerNumber);

		return linLay;
	}
	

	public String formatName(String text){
//		if(text.length()>10){
//			return text.substring(0, 8)+"..";
//		}
	return text;
	}
}