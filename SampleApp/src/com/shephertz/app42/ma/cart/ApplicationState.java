package com.shephertz.app42.ma.cart;

import android.content.Context;
import android.content.SharedPreferences;

import com.shephertz.app42.util.AppContext;

// reviewed
public class ApplicationState {
	private static ApplicationState mInstance = null;
	private SharedPreferences sharedPreference;
	private final String USERNAME = "cart_user";
	private final String PHONE_NO = "user_mob";
	private final String EMAIL = "user_email";

	private final String USER_ADDRESS = "user_address";
	private final String IS_ADDRESS = "is_address";
	
	
	static ApplicationState instance(Context context) {
		if (mInstance == null)
			mInstance = new ApplicationState(context);
		return mInstance;
	}

	private ApplicationState(Context context) {
		sharedPreference = context.getSharedPreferences(AppContext.AppName,
				context.MODE_PRIVATE);
	}


	public boolean isAddressOk() {
		return sharedPreference.getBoolean(IS_ADDRESS, false);
	}

	public UserData getUserData() {
		UserData userData = new UserData();
		userData.setName(sharedPreference.getString(USERNAME, ""));
		userData.setEmail(sharedPreference.getString(EMAIL, ""));
		userData.setPhoneNo(sharedPreference.getString(PHONE_NO, ""));

		userData.setAddress(sharedPreference.getString(USER_ADDRESS, ""));

		return userData;
	}

	public void setUserData(UserData userData) {
		SharedPreferences.Editor prefEditor = sharedPreference.edit();
		prefEditor.putString(USERNAME, userData.getName());
		prefEditor.putString(PHONE_NO, userData.getPhoneNo());
		prefEditor.putString(EMAIL, userData.getEmail());

		prefEditor.putString(USER_ADDRESS, userData.getAddress());

		prefEditor.putBoolean(IS_ADDRESS, true);
		prefEditor.commit();
	}

}
