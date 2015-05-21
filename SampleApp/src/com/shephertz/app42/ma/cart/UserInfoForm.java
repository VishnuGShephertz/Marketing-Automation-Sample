package com.shephertz.app42.ma.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.ma.R;
import com.shephertz.app42.util.AppContext;

/**
 * Each category tab activity.
 * 
 * @author itcuties
 * 
 */
public class UserInfoForm extends Activity {

	private EditText name, phoneNo, emailId;
	private EditText address;
	private ApplicationState applicationState;
	private boolean isAddressFilled = false;
	private ValidateInput inputValidator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo_form_merge);

		LinearLayout mainParent = (LinearLayout) findViewById(R.id.main_parent);
		mainParent.setBackgroundResource(R.drawable.news);

		buildHeader();

		inputValidator = new ValidateInput();
		applicationState = ApplicationState.instance(this);
		intializeViews();
	}

	public void onBackClicked(View view) {
		finish();

	}

	public void onStart() {
		super.onStart();
		
	}

	public void onMyCategoryClicked(View view) {
		
		finish();
	}

	public void resetDetails(View view) {
		name.setText("");
		phoneNo.setText("");
		emailId.setText("");
		address.setText("");

	}

	public void saveDetails(View view) {
		if (validateInformation()) {
			UserData userInfo = new UserData();

			userInfo.setName(name.getText().toString().trim());
			userInfo.setPhoneNo(phoneNo.getText().toString().trim());
			userInfo.setEmail(emailId.getText().toString().trim());
			userInfo.setAddress(address.getText().toString().trim());
			applicationState.setUserData(userInfo);

			if (!isAddressFilled) {
				Intent intent = new Intent(this, UserDetails.class);
				startActivity(intent);
			}
			finish();
		}
	}

	private void intializeViews() {

		isAddressFilled = applicationState.isAddressOk();
		UserData userInfo = applicationState.getUserData();
		name = (EditText) findViewById(R.id.userInfo_name);
		name.setText(userInfo.getName());

		phoneNo = (EditText) findViewById(R.id.userInfo_phone);
		phoneNo.setText(userInfo.getPhoneNo());

		emailId = (EditText) findViewById(R.id.userInfo_email);
		emailId.setText(userInfo.getEmail());

		address = (EditText) findViewById(R.id.userInfo_address);
		address.setText(userInfo.getAddress());

	}

	private void showMessage(String field) {
		Toast.makeText(this, "Please enter a valid " + field,
				Toast.LENGTH_SHORT).show();
	}

	private boolean validateFields() {

		if (!inputValidator.isNameValid(name.getText().toString().trim())) {
			showMessage("Name");
			return false;
		} else if (!inputValidator.isPhoneNoValid(phoneNo.getText().toString()
				.trim())) {
			showMessage("PhoneNo");
			return false;
		} else if (!inputValidator.isEmailValid(emailId.getText().toString()
				.trim())) {
			showMessage("Email Address");
			return false;
		} else if (!inputValidator.isAddressValid(address.getText().toString()
				.trim())) {
			showMessage("Address (Contains only (.-))");
			return false;
		} else {
			return true;
		}

	}

	private boolean validateInformation() {

		if (name.getText().toString().trim().equalsIgnoreCase("")
				|| phoneNo.getText().toString().trim().equalsIgnoreCase("")
				|| emailId.getText().toString().trim().equalsIgnoreCase("")
				|| address.getText().toString().trim().equalsIgnoreCase("")) {
			Toast.makeText(this, "All the fields are mandatory!",
					Toast.LENGTH_SHORT).show();
			return false;

		} else {
			return validateFields();
		}

	}

	private void buildHeader() {

		LinearLayout lowestLayout = (LinearLayout) this
				.findViewById(R.id.player_header_bg);
		TextView header = (TextView) findViewById(R.id.page_header);
		lowestLayout.setBackgroundResource(AppContext.headerimage);
		header.setText("User Info");
		header.setTextColor(AppContext.headerColor);

	}
}
