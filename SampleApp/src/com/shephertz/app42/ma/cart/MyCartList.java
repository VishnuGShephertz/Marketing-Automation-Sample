package com.shephertz.app42.ma.cart;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.ma.MaApplication;
import com.shephertz.app42.ma.R;
import com.shephertz.app42.util.App42Event;
import com.shephertz.app42.util.AppContext;
import com.shephertz.app42.util.Utils;



public class MyCartList extends Activity {

	public static ArrayList<MyCartItem> cartItemList;
	public static String prizeUnit = "Rs.";
	
	private ProgressDialog dialog;
	private ListView categoryLitsView;
	
	public TextView totalAmt, totalQunat;
	
	MyCartListAdapter adapter;
	private int indexSelected = -1;
	public static double totalPrize;
	public static int totalQuantity;
	static {
		cartItemList=new ArrayList<MyCartItem>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(AppContext.appThemeStyle);
		setContentView(R.layout.my_cart_list);
	
			RelativeLayout mainParent = (RelativeLayout) findViewById(R.id.main_parent);
			mainParent.setBackgroundResource(R.drawable.news);
	
		buildHeader();
		dialog = new ProgressDialog(this);
	
		categoryLitsView = (ListView) findViewById(R.id.list);
		categoryLitsView.setVisibility(View.GONE);
		totalAmt = (TextView) findViewById(R.id.total_amt);
		totalQunat = (TextView) findViewById(R.id.item_qnt);
		
		makeDummyList();
	}

	public void onStart() {
		super.onStart();
		if (CartCotext.isOrederPlaced) {
			finish();
		}
	}
	public void onCheckout(View view) {

		if (cartItemList != null && cartItemList.size() > 0
			) {
			boolean isAddress = false;
				isAddress = ApplicationState.instance(this).isAddressOk();
			if (!isAddress) {
				Intent intent = new Intent(this, UserInfoForm.class);
				startActivityForResult(intent, 0);
			} else {
				Intent intent = new Intent(this, UserDetails.class);
				startActivityForResult(intent, 0);
			}
			try {
				Utils.trackEvent(App42Event.CheckOut.toString(), getCheckOutJson());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT)
					.show();
		}
	}
  public void onMyCategoryClicked(View view){
	
	finish();
	}
	public void getCartDetails() {

		if (!MaApplication.getCartId().equals("")) {
//			dialog.setMessage("Fetching Cart....");
//			dialog.setCancelable(false);
//			dialog.show();
//			mCartService.loadCartItems(applicationContext.getMyCartId(), this);
		} else {
			Toast.makeText(this, "Your Cart is Empty", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void onBackClicked(View view) {
		finish();

	}

	

	public void onMyCartClicked(View view) {
		Intent intent = new Intent(this, MyCartList.class);
		startActivity(intent);
	}

	private void makeDummyList() {

		if (cartItemList.size() > 0) {
			totalAmt.setText(CartCotext.prizeUnit + " " + getTotalPrize());
			totalQunat.setText(" " + cartItemList.size()+ " Items");
			categoryLitsView.setVisibility(View.VISIBLE);
			adapter = new MyCartListAdapter(this, cartItemList);
			categoryLitsView.setAdapter(adapter);
		}
		else{
			Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT)
			.show();
		}
	}

	private void buildHeader() {

		LinearLayout lowestLayout = (LinearLayout) this
				.findViewById(R.id.player_header_bg);
		TextView header = (TextView) findViewById(R.id.page_header);
		lowestLayout.setBackgroundResource(AppContext.headerimage);
		header.setText("My Cart");
		header.setTextColor(AppContext.headerColor);

	}

	

	public void showDialogPopup(final String id, final int pos) {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(MyCartList.this);
		alertbox.setTitle("Confirmation");
		alertbox.setMessage("Are you sure you want to delete this Item?");
		alertbox.setIcon(R.drawable.shop1);
		// set a positive/yes button and create a listener
		alertbox.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						removeFromCart(id, 0, pos);
					}
				});

		// set a negative/no button and create a listener
		alertbox.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					// do something when the button is clicked
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});

		alertbox.show();
	}

	private void removeFromCart(String id, int qnt, int pos) {
		try {
			Utils.trackEvent(App42Event.RemoveFromCart.toString(), getItemJson(pos, qnt));
			cartItemList.remove(pos);
			totalAmt.setText(CartCotext.prizeUnit + " " + getTotalPrize());
			totalQunat.setText(" " + cartItemList.size()+ " Items");
			adapter.notifyDataSetChanged();
			Toast.makeText(this, "Item is removed from cart", Toast.LENGTH_SHORT)
					.show();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONObject getItemJson(int pos,int qnt) throws JSONException{
		MyCartItem item=cartItemList.get(pos);
		JSONObject json=new JSONObject();
		json.put("itemId", item.getId());
		json.put("itemName", item.getName());
		json.put("itemPrize", item.getDiscountedPrize());
		json.put("itemTotalPrize", item.getQuantity()*item.getDiscountedPrize());
		json.put("itemQuantity", item.getQuantity());
		return json;
	}
	private double getTotalPrize() {
		double bTotalPrize = 0;
		for (int i = 0; i < cartItemList.size(); i++) {
			bTotalPrize += cartItemList.get(i).getDiscountedPrize()
					* cartItemList.get(i).getQuantity();
		}
		totalPrize =bTotalPrize;
		return totalPrize;
	}

	private int getTotalQuantity() {
		totalQuantity = 0;
		for (int i = 0; i < cartItemList.size(); i++) {
			totalQuantity += cartItemList.get(i).getQuantity();
		}
		return totalQuantity;
	}

	
	public void onItemRemoved() {
		// TODO Auto-generated method stub
		dialog.dismiss();
		cartItemList.remove(indexSelected);
		totalAmt.setText(CartCotext.prizeUnit + " " + getTotalPrize());
		totalQunat.setText(" " + cartItemList.size()+ " Items");
		adapter.notifyDataSetChanged();
		Toast.makeText(this, "Item is removed from cart", Toast.LENGTH_SHORT)
				.show();
		resetIndex();
	}
	private void resetIndex() {
		indexSelected = -1;
	}
	public JSONObject getCheckOutJson() throws JSONException{
		JSONObject json=new JSONObject();
		json.put("cartId", MaApplication.getCartId());
		json.put("totalAmount", totalPrize);
		json.put("totalQuantity", getTotalQuantity() );
		return json;
	}
}
