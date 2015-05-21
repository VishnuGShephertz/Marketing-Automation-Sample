package com.shephertz.app42.ma.cart;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

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
public class CartCategoryList extends Activity implements OnItemClickListener{

	private ArrayList<String> categoryList=new ArrayList<String>();
	
	private ListView categoryLitsView;

	private CartCategoryAdapter adapter;
	private String catalogue="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(AppContext.appThemeStyle);
		setContentView(R.layout.shopping_list);
	
		buildHeader();
		ImageButton img = (ImageButton) findViewById(R.id.cart_category);
		img.setClickable(false);
		img.setVisibility(View.GONE);
		LinearLayout mainParent = (LinearLayout) findViewById(R.id.main_parent);
		mainParent.setBackgroundResource(R.drawable.news);
		categoryLitsView = (ListView) findViewById(R.id.list);
		categoryLitsView.setVisibility(View.GONE);
		loadProducts();
	}

	public void onBackClicked(View view) {
		finish();

	}
	public void onStart() {
		super.onStart();
		if (CartCotext.isOrederPlaced) {
			finish();
		}
	}

	

	public void onMyCartClicked(View view) {
		if (categoryList != null && categoryList.size() > 0) {
			Intent intent = new Intent(this, MyCartList.class);
			startActivityForResult(intent, 0);
		} else {
			Toast.makeText(this, "Products are not available",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void buildHeader() {
		catalogue=getIntent().getStringExtra(AppConstants.Catalogue);
		LinearLayout lowestLayout = (LinearLayout) this
				.findViewById(R.id.player_header_bg);
		TextView header = (TextView) findViewById(R.id.page_header);
		lowestLayout.setBackgroundResource(AppContext.headerimage);
		header.setText(catalogue);
		header.setTextColor(AppContext.headerColor);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
			Intent intent = new Intent(CartCategoryList.this,
					CartItemList.class);
			intent.putExtra(AppConstants.Products, categoryList.get(pos));
			intent.putExtra(AppConstants.Catalogue, catalogue);
			startActivityForResult(intent, 0);
	}
private void loadProducts(){
	JSONArray jsonArr;
	if(catalogue.equalsIgnoreCase(AppConstants.TxtElectroNics)){
		jsonArr=MaApplication.getECJsonArray(AppConstants.Products);
	}
	else{
		jsonArr=MaApplication.getMenssonArray(AppConstants.Products);
	}
	for(int i=0;i<jsonArr.length();i++){
		try {
			categoryList.add(jsonArr.getString(i));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	makeList();
}
	private void makeList() {
		if (categoryList.size() > 0) {
			categoryLitsView.setVisibility(View.VISIBLE);
			adapter = new CartCategoryAdapter(this, categoryList);
			categoryLitsView.setAdapter(adapter);
			categoryLitsView.setOnItemClickListener(this);
		}
	}
}
