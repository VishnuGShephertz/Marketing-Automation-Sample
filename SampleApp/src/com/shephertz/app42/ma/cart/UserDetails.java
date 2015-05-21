package com.shephertz.app42.ma.cart;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shephertz.app42.ma.MaApplication;
import com.shephertz.app42.ma.R;
import com.shephertz.app42.util.App42Event;
import com.shephertz.app42.util.AppContext;
import com.shephertz.app42.util.Utils;

/**
 * Each category tab activity.
 * 
 * @author itcuties
 * 
 */
public class UserDetails extends Activity {
	private ApplicationState applicationState;

	UserData userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_details);
		buildHeader();
		RelativeLayout mainParent = (RelativeLayout) findViewById(R.id.main_parent);
		mainParent.setBackgroundResource(R.drawable.news);
		applicationState = ApplicationState.instance(this);
	}

	public void onMyCategoryClicked(View view) {
		
		finish();
	}

	public void onStart() {
		super.onStart();
	
			CartCotext.isOrederPlaced = false;
			intializeViews();
	}

	private void intializeViews() {
		userInfo = applicationState.getUserData();
		((TextView) findViewById(R.id.userInfo_name_txt)).setText(userInfo
				.getName());
		((TextView) findViewById(R.id.userInfo_phone_txt)).setText(userInfo
				.getPhoneNo());
		((TextView) findViewById(R.id.userInfo_email_txt)).setText(userInfo
				.getEmail());
		((TextView) findViewById(R.id.userInfo_address_txt)).setText(userInfo
				.getAddress());
	}

	public void onBackClicked(View view) {
		finish();
	}

	public void placeOrder(View view) {
		
		CartCotext.isOrederPlaced = true;
		MaApplication.resetCartId();
		showMessagePopup();
		try {
			Utils.trackEvent(App42Event.Payment.toString(),
					getCheckOutJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void exitFrom() {
		CartCotext.isOrederPlaced = true;
		MaApplication.resetCartId();
		finish();
	}

	public void showMessagePopup() {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(UserDetails.this);
		alertbox.setTitle("Place Order Status");
		alertbox.setCancelable(false);
		alertbox.setMessage("Order request has been sent ,you will notify via Email");
		alertbox.setIcon(R.drawable.shop1);
		// set a positive/yes button and create a listener
		alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				exitFrom();
			}
		});

		alertbox.show();
	}

	public JSONObject getCheckOutJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("cartId", MaApplication.getCartId());
		json.put("userName", userInfo.getName());
		json.put("email", userInfo.getEmail());
		json.put("phone", userInfo.getPhoneNo());
		json.put("address", userInfo.getAddress());
		return json;
	}



	public void editDetails(View view) {
		Intent intent = new Intent(this, UserInfoForm.class);
		startActivityForResult(intent, 0);
	}

	private void buildHeader() {
		LinearLayout lowestLayout = (LinearLayout) this
				.findViewById(R.id.player_header_bg);
		TextView header = (TextView) findViewById(R.id.page_header);
		lowestLayout.setBackgroundResource(AppContext.headerimage);
		header.setText("Confirmation");
		header.setTextColor(AppContext.headerColor);

	}

	// private StringBuffer getCartData(){
	// try {
	// StringBuffer strCartData=new StringBuffer();
	// strCartData.append("Catlogue    : " +CartCotext.catlogName+"\n");
	// strCartData.append("Total Amount: " +MyCartList.totalPrize+"\n");
	// strCartData.append("Total Items : " +MyCartList.totalQuantity+"\n");
	// strCartData.append("Cart Id     : "
	// +applicationState.getMyCartId()+"\n");
	// strCartData.append("Items    : " +"\n");
	// int len=MyCartList.cartItemList.size();
	// for(int i=0;i<len;i++){
	//
	// Item item=MyCartList.cartItemList.get(i);
	// strCartData.append("Item_Name   : "+item.getName()+"\n");
	// strCartData.append("Item_Id     : "+mCartService.getItemId(item.getItemId())+"\n");
	// strCartData.append("item_Prize  :	"+mCartService.RoundTo2Decimals(item.getPrice().doubleValue())+"\n");
	// strCartData.append("Quantity    :"+item.getQuantity()+"\n");
	// strCartData.append("Total Prize : "+mCartService.RoundTo2Decimals((item.getPrice().doubleValue()*item.getQuantity()))+" \n\n ");
	// }
	//
	// return strCartData;
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return null;
	// }
	// }

	// @Override
	// public void onCheckoutStatus(boolean status) {
	// progressDialog.dismiss();
	// if(status){
	// StringBuffer jsonData=getCartData();
	// if(jsonData!=null){
	// UserData userInfo=applicationState.getUserData();
	// StringBuffer strData=new StringBuffer();
	// strData.append("Name :"+userInfo.getName()+"\n");
	// strData.append("Phone No :"+userInfo.getPhoneNo()+"\n");
	// strData.append("Email :"+userInfo.getEmail()+"\n");
	// strData.append("Stree :"+userInfo.getStreet()+"\n");
	// strData.append("City :"+userInfo.getCity()+"\n");
	// strData.append("ZipCode :"+userInfo.getZipCode()+"\n");
	// strData.append("Country :"+userInfo.getCountry()+"\n\n");
	// strData.append(jsonData.toString());
	// applicationState.setMyCartId("");
	// openSendEmailIntent(strData.toString());
	// }
	//
	// }
	// else{
	// Toast.makeText(UserDetails.this,
	// "Unable to place Order Please try again later",
	// Toast.LENGTH_SHORT).show();
	// }
	// }

	// private void openSendEmailIntent(String message) {
	// String email = Utils.getContents(
	// ((GlobalVar)getApplicationContext()).getIndexJsonDataOfApp(),
	// Constants.ContactTxt,Constants. EmailTxt);
	// Intent intent = new Intent(Intent.ACTION_SEND);
	// intent.setType("message/rfc822");
	// intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
	// intent.putExtra(Intent.EXTRA_SUBJECT, "Shopping Cart Order");
	// intent.putExtra(Intent.EXTRA_TEXT, message);
	// try {
	// startActivity(Intent.createChooser(intent, "Send mail..."));
	// } catch (android.content.ActivityNotFoundException ex) {
	// Toast.makeText(UserDetails.this,
	// "There are no email clients installed.", Toast.LENGTH_SHORT)
	// .show();
	// }
	// }
	//

}
