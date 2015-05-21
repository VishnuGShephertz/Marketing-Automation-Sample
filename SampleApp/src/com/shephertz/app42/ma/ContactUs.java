package com.shephertz.app42.ma;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.ma.cart.ValidateInput;
import com.shephertz.app42.util.App42Event;
import com.shephertz.app42.util.AppContext;
import com.shephertz.app42.util.Utils;

public class ContactUs extends Activity implements OnItemSelectedListener {

	private String email;
	private EditText phone;
	private EditText message;
	private final String ContactUsTxt = "Contact Us";
	private ArrayList<String> aarList=new ArrayList<String>();
    private String defaultVal="Order";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.setTheme(AppContext.appThemeStyle);
		setContentView(R.layout.contact_us);
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		// Spinner click listener
		aarList.add("Order");
		aarList.add("Payment");
		aarList.add("Cancellations");
		aarList.add("Shopping");
		aarList.add("Others");
		
		spinner.setOnItemSelectedListener(this);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, aarList);
		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		LinearLayout mainParent = (LinearLayout) findViewById(R.id.main_parent);
		mainParent.setBackgroundResource(R.drawable.news);

		message = (EditText) findViewById(R.id.editText2);
		phone = (EditText) findViewById(R.id.edit_phone);
		email = "";

		((TextView) findViewById(R.id.textView2))
				.setTextColor(AppContext.textColor);
		buildHeader();
	}

	private void buildHeader() {
		RelativeLayout lowestLayout = (RelativeLayout) this
				.findViewById(R.id.header_layout);
		TextView header = (TextView) findViewById(R.id.page_header);
		lowestLayout.setBackgroundResource(AppContext.headerimage);
		header.setText(ContactUsTxt);
		header.setTextColor(AppContext.headerColor);
	}

	public void onBackClicked(View view) {
		finish();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private JSONObject getJson(String teamName) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("phoneNo", phone.getText().toString());
		json.put("query", defaultVal.toString());
		json.put("message", message.getText().toString());
		return json;
	}

	public void onSubmitClicked(View view) {
		if (message.getText().toString().trim().equalsIgnoreCase("")
				|| phone.getText().toString().trim().equalsIgnoreCase("")) {
			showAlertDialog();
		} else {
			if (new ValidateInput().isPhoneNoValid(phone.getText().toString()
					.trim())) {
				try {
					Utils.trackEvent(App42Event.CustomerSupport.toString(), getJson(""));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(this, "Please enter a valid PhoneNumbe",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	// private void openSendEmailIntent() {
	// Intent intent = new Intent(Intent.ACTION_SEND);
	// intent.setType("message/rfc822");
	// intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
	// intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
	// intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
	// try {
	// startActivity(Intent.createChooser(intent, "Send mail..."));
	// } catch (android.content.ActivityNotFoundException ex) {
	// Toast.makeText(ContactUs.this,
	// "There are no email clients installed.", Toast.LENGTH_SHORT)
	// .show();
	// }
	// }

	private void showAlertDialog() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(ContactUs.this);
		// alt_bld.setMessage("All the fields are mandatory!")
		alt_bld.setMessage(
				Html.fromHtml("<font color='" + AppContext.textColor
						+ "'><b>All the fields are mandatory!</b></font>"))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'Yes' Button
					}
				});
		AlertDialog alert = alt_bld.create();
		alert.setTitle(Html.fromHtml("<font color='"
				+ AppContext.textColor + "'><b>Error ! </b></font>"));
		alert.setIcon(R.drawable.shop1);
		alert.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		defaultVal=aarList.get(arg2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android
	 * .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}