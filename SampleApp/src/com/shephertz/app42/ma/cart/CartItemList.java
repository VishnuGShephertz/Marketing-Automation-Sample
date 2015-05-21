package com.shephertz.app42.ma.cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.ma.MaApplication;
import com.shephertz.app42.ma.R;
import com.shephertz.app42.util.AppConstants;
import com.shephertz.app42.util.AppContext;

/**
 * Each category tab activity.
 * 
 * @author itcuties
 * 
 */
public class CartItemList extends Activity implements OnItemClickListener {

	public static String prizeUnit = "Rs.";

	private String categoryName;
	private ListView categoryItemList;
    private JSONArray itemArr=new JSONArray();;
	private String catalogue = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(AppContext.appThemeStyle);
		setContentView(R.layout.shopping_list);
		LinearLayout mainParent = (LinearLayout) findViewById(R.id.main_parent);
		mainParent.setBackgroundResource(R.drawable.news);

		categoryName = getIntent().getStringExtra(AppConstants.Products);
		catalogue = getIntent().getStringExtra(AppConstants.Catalogue);
		buildHeader();
		categoryItemList = (ListView) findViewById(R.id.list);
		categoryItemList.setVisibility(View.GONE);
		((ImageButton) findViewById(R.id.cart_category))
				.setVisibility(View.GONE);
		makeItemList();
	}

	public void onStart() {
		super.onStart();
		if (CartCotext.isOrederPlaced) {
			finish();
		}
	}

	public void onBackClicked(View view) {
		finish();
	}

	public void onMyCategoryClicked(View view) {
		
		finish();
	}

	private void makeItemList() {
		
		if (catalogue.equalsIgnoreCase(AppConstants.TxtElectroNics)) {
			itemArr = MaApplication.getECJsonArray(categoryName);
		} else {
			itemArr = MaApplication.getMenssonArray(categoryName);
		}
		categoryItemList.setVisibility(View.VISIBLE);
		CarttemAdapter adapter = new CarttemAdapter(this, itemArr);
		categoryItemList.setAdapter(adapter);
		categoryItemList.setOnItemClickListener(this);

	}

	private void buildHeader() {
		
		LinearLayout lowestLayout = (LinearLayout) this
				.findViewById(R.id.player_header_bg);
		TextView header = (TextView) findViewById(R.id.page_header);
		lowestLayout.setBackgroundResource(AppContext.headerimage);
		header.setText(categoryName.trim());
		header.setTextColor(AppContext.headerColor);
	}

	public void onMyCartClicked(View view) {
		Intent intent = new Intent(this, MyCartList.class);
		startActivityForResult(intent, 0);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		// intent.putParcelableArrayListExtra("poad_Items", poadCastItems);
		Intent intent = new Intent(CartItemList.this, ItemDetails.class);
	
		try {
			JSONObject json=itemArr.getJSONObject(pos);
			intent.putExtra("item_name",json.optString(AppConstants.ItemName, ""));
			intent.putExtra("item_prize",json.optDouble(AppConstants.ItemMRP, 0.0));
			intent.putExtra("item_disc",json.optDouble(AppConstants.ItemPrize, 0.0));
			intent.putExtra("item_id",json.optString(AppConstants.ItemId, ""));
			intent.putExtra("contents",json.optString(AppConstants.Desc, "Welcome to shooping, many offers are awaiting for you."));
			intent.putExtra("cat_Name", categoryName);
			startActivityForResult(intent, 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(CartItemList.this, "Please try again", Toast.LENGTH_SHORT).show();
		}
		

	}
}
