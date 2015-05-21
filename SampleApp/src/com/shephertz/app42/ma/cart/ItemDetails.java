package com.shephertz.app42.ma.cart;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.ma.R;
import com.shephertz.app42.util.App42Event;
import com.shephertz.app42.util.AppContext;
import com.shephertz.app42.util.Utils;

/**
 * Each category tab activity.
 * 
 * @author Vishnu Garg
 * 
 */
public class ItemDetails extends Activity  {

	private String catName;
	
	private MyCartItem myItem;
	
	private EditText quantityEdit;
	private ApplicationState applicationState;
	private ProgressDialog dialog;
	private long ONE_DAY = 1000 * 24 * 60 * 60;
	private String contents = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(AppContext.appThemeStyle);
		setContentView(R.layout.item_detail);
	
			RelativeLayout mainParent = (RelativeLayout) findViewById(R.id.rel_parent);
			mainParent.setBackgroundResource(R.drawable.news);
		
		parseIntent();
		buildHeader();
		dialog = new ProgressDialog(this);
		
		applicationState = ApplicationState.instance(this);
		intializeView();
	}

	public void onStart() {
		super.onStart();
		
		if (CartCotext.isOrederPlaced) {
			finish();
		}
	}

	public void onMyCategoryClicked(View view) {
		
		finish();
	}

	private void parseIntent() {
		Intent intent = getIntent();
		catName = intent.getStringExtra("cat_Name");
		myItem = new MyCartItem();
		myItem.setId(intent.getStringExtra("item_id"));
		myItem.setName(intent.getStringExtra("item_name"));
		myItem.setPrize((intent.getDoubleExtra("item_prize", 0.0)));
		myItem.setDiscountedPrize((intent.getDoubleExtra("item_disc", 0.0)));
		myItem.setDescription(intent.getStringExtra("contents"));
	}

	private void intializeView() {
		TextView name = (TextView) findViewById(R.id.item_name);
		name.setText(myItem.getName());
		name.setTextColor(AppContext.textColor);

		((TextView) findViewById(R.id.mrp_text))
				.setTextColor(AppContext.textColor);
		TextView prize = (TextView) findViewById(R.id.item_prize);
		prize.setText(CartCotext.prizeUnit + ""
				+ myItem.getPrize());
		prize.setPaintFlags(prize.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		prize.setTextColor(AppContext.textColor);

		TextView des_prize = (TextView) findViewById(R.id.item_disc_prize);
		des_prize.setText(CartCotext.prizeUnit + ""
				+ myItem.getDiscountedPrize());
		((TextView) findViewById(R.id.qnt_text))
				.setTextColor(AppContext.textColor);
		((TextView) findViewById(R.id.desc_text))
				.setTextColor(AppContext.textColor);

		Display display = getWindowManager().getDefaultDisplay();
		int reuireSize = (display.getWidth() * 5) / 10;
		ImageView img = (ImageView) findViewById(R.id.item_img);
	       
		quantityEdit = (EditText) findViewById(R.id.edit_quantity);

		TextView webView = (TextView) findViewById(R.id.item_contents);
		webView.setTextColor(AppContext.textColor);
		webView.setText(myItem.getDescription());
	}

	

	public void onBackClicked(View view) {
		finish();
	}
	private boolean isQuantityValid() {
		String txt = quantityEdit.getText().toString().trim();
		int no = 0;
		if (!txt.equalsIgnoreCase(""))
			no = Integer.parseInt(txt);
		if (no == 0) {
			Toast.makeText(this, "Item quantity should not be 0",
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			myItem.setQuantity(no);
			return true;
		}

	}

	public void onAddToCartClicked(View view) {
			try {
				if(isQuantityValid()){
				 addItem(myItem);
				  Toast.makeText(this, "Item Added in cart", Toast.LENGTH_SHORT).show();
				  Utils.trackEvent(App42Event.AddToCart.toString(), myItem.getItemJson(catName));
				  finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void addItem(MyCartItem myitem){
		boolean found=false;
		int size=MyCartList.cartItemList.size();
		for(int i=0;i<size;i++){
			MyCartItem item=MyCartList.cartItemList.get(i);
					if(myitem.getId().equalsIgnoreCase(item.getId())){
						item.setQuantity(item.getQuantity()+myitem.getQuantity());
						found=true;
						break;
					}
		}
		if(!found)
			MyCartList.cartItemList.add(myitem);
	}

	public void onMyCartClicked(View view) {
		Intent intent = new Intent(this, MyCartList.class);
		startActivityForResult(intent, 0);
	}

	private void buildHeader() {
		LinearLayout lowestLayout = (LinearLayout) this
				.findViewById(R.id.player_header_bg);
		TextView header = (TextView) findViewById(R.id.page_header);
		lowestLayout.setBackgroundResource(AppContext.headerimage);
		header.setText(catName);
		header.setTextColor(AppContext.headerColor);
	}

	public JSONObject getItemJson(String cartId) throws JSONException{
		JSONObject json=new JSONObject();
		json.put("cartId",cartId);
		return json;
	}
}
