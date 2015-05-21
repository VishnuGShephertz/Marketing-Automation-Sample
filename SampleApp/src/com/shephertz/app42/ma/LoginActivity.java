/**
 * -----------------------------------------------------------------------
 *     Copyright © 2015 ShepHertz Technologies Pvt Ltd. All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.shephertz.app42.ma;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.push.plugin.App42GCMController;
import com.shephertz.app42.push.plugin.App42GCMController.App42GCMListener;
import com.shephertz.app42.util.App42Event;
import com.shephertz.app42.util.AppConstants;
import com.shephertz.app42.util.Utils;

/**
 * Login UI Screen and handle login integration API with App42,FaceBook or
 * Twitter
 * 
 * @author Vishnu Garg
 */
public class LoginActivity extends Activity implements App42GCMListener {

	private EditText edUserName;
	private EditText edPassword;
	private EditText edEmailid;
	private EditText edFullName;
	private TextView tvSelectedLogin;
	private TextView tvSelectedRegister;
	private Button btnRegister;
	private Button btnLogin;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.app42_login);

		initComponent();
	}

	/**
	 * Initialize UI component
	 */
	private void initComponent() {
		edUserName = (EditText) this.findViewById(R.id.uname);
		edPassword = (EditText) this.findViewById(R.id.pswd);
		edEmailid = (EditText) this.findViewById(R.id.email);
		edFullName = (EditText) this.findViewById(R.id.fullName);
		btnLogin = (Button) this.findViewById(R.id.btn_login);
		btnRegister = (Button) this.findViewById(R.id.btn_register);
		tvSelectedRegister = (TextView) this
				.findViewById(R.id.current_register);
		tvSelectedLogin = (TextView) this.findViewById(R.id.current_login);
		tvSelectedRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Switching to Register screen
				showLoginScreen();

			}
		});
		tvSelectedLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				showRegistrationScreen();
			}
		});
	}

	/**
	 * Clear View
	 */
	private void clearView() {
		edUserName.setText("");
		edPassword.setText("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc) Fetch userInfo if already exist in local preferences
	 * 
	 * @see android.app.Activity#onStart()
	 */
	public void onStart() {
		super.onStart();

	}

	/*
	 * (non-Javadoc) Handle Twitter CallBack
	 * 
	 * @see android.app.Activity#onResume()
	 */
	public void onResume() {
		super.onResume();
	}

	/**
	 * Authorization processof User with App42
	 * 
	 * @param view
	 */
	public void onSignInClicked(View view) {
		doGCMRegistration(edUserName.getText().toString());
		trackLoginEvent(edUserName.getText().toString());
		Intent intent = new Intent(this, Menu.class);
		startActivity(intent);
	}

	/**
	 * Register User on App42
	 * 
	 * @param view
	 */
	public void onRegisterClicked(View view) {
		doGCMRegistration(edUserName.getText().toString());
		trackRegistrationEvent(edFullName.getText().toString(), edUserName
				.getText().toString(), edEmailid.getText().toString());
		Intent intent = new Intent(this, Menu.class);
		startActivity(intent);
	}

	/**
	 * Show registration Screen while navigation
	 */
	public void showRegistrationScreen() {
		clearView();
		edFullName.setVisibility(View.VISIBLE);
		edEmailid.setVisibility(View.VISIBLE);
		tvSelectedRegister.setVisibility(View.VISIBLE);
		tvSelectedLogin.setVisibility(View.GONE);
		btnLogin.setVisibility(View.GONE);
		btnRegister.setVisibility(View.VISIBLE);
	}

	/**
	 * Show Login Screen while navigation
	 */
	public void showLoginScreen() {
		edFullName.setVisibility(View.GONE);
		edEmailid.setVisibility(View.GONE);
		tvSelectedLogin.setVisibility(View.VISIBLE);
		tvSelectedRegister.setVisibility(View.GONE);
		btnLogin.setVisibility(View.VISIBLE);
		btnRegister.setVisibility(View.GONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	private void trackRegistrationEvent(String name, String userId,
			String emailID) {
		try {
			JSONObject data = new JSONObject();
			data.put("userName", userId);
			data.put("displayName", name);
			data.put("emailId", emailID);
			Utils.trackEvent(App42Event.Register.toString(), data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void trackLoginEvent(String userId) {
		try {
			JSONObject data = new JSONObject();
			data.put("userName", userId);
			Utils.trackEvent(App42Event.Login.toString(), data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doGCMRegistration(String userId) {
		App42API.setLoggedInUser(userId);
		if (App42GCMController.isPlayServiceAvailable(this)) {
			App42GCMController.registerOnGCM(LoginActivity.this,
					AppConstants.GoogleProjectNo, this);
		} else {
			Log.i("App42PushNotification",
					"No valid Google Play Services APK found.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shephertz.app42.push.plugin.App42GCMController.App42GCMListener#onError
	 * (java.lang.String)
	 */
	@Override
	public void onError(String errorMsg) {
		// TODO Auto-generated method stub
		Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shephertz.app42.push.plugin.App42GCMController.App42GCMListener#
	 * onGCMRegistrationId(java.lang.String)
	 */
	@Override
	public void onGCMRegistrationId(String gcmRegId) {
		// TODO Auto-generated method stub
		App42GCMController.storeRegistrationId(this, gcmRegId);
		App42GCMController.registerOnApp42(App42API.getLoggedInUser(),
				gcmRegId, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shephertz.app42.push.plugin.App42GCMController.App42GCMListener#
	 * onApp42Response(java.lang.String)
	 */
	@Override
	public void onApp42Response(String responseMessage) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shephertz.app42.push.plugin.App42GCMController.App42GCMListener#
	 * onRegisterApp42(java.lang.String)
	 */
	@Override
	public void onRegisterApp42(String responseMessage) {
		// TODO Auto-generated method stub

	}

}
