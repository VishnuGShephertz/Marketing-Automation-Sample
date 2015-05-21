package com.shephertz.app42.ma;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

import com.shephertz.app42.iam.App42CampaignAPI;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42Log;
import com.shephertz.app42.paas.sdk.android.app.App42Application;
import com.shephertz.app42.util.AppConstants;

public class MaApplication extends App42Application {
	public static JSONObject JsonElectroNics;
	public static JSONObject jsonMens;
	private static String themeOfApp = "l";
	public static String cartId="";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		App42API.initialize(
				this,
				AppConstants.APIKey,
				AppConstants.SecretKey	);
		App42Log.setDebug(true);
		App42API.enableAppStateEventTracking(true);
		App42API.setLoggedInUser("vis");
		App42CampaignAPI.enable(true);
		//Time in minutes default is one hour
		App42CampaignAPI.setConfigCacheTime(5);
		App42API.enableAppALiveTrack(true);
		
	}
	public void broadCastMessage(String message) {
		Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
		intent.putExtra("referrer", "dfsfdsfd");
		this.sendBroadcast(intent);
	}
	public static String getThemeOfApp() {
		return themeOfApp;
	}

	public static void setElectroNics(JSONObject data) {
		JsonElectroNics = data;
	}

	public static void setMens(JSONObject data) {
		jsonMens = data;
	}

	public static JSONObject getElectroNicsJson() {
		return JsonElectroNics;
	}

	public static JSONObject getMensJson() {
		return jsonMens;
	}

	public static void generateCartId() {
		if (cartId == null||cartId.equals(""))
			cartId = "" + new Date().getTime();
	}

	public static JSONArray getECJsonArray(String keyName) {
		try {
			return JsonElectroNics.getJSONArray(keyName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new JSONArray();
		}
	}

	public static JSONArray getMenssonArray(String keyName) {
		try {
			return jsonMens.getJSONArray(keyName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new JSONArray();
		}
	}

	public static void resetCartId() {
		cartId = "";
	}

	public static String getCartId() {
		if (cartId == null)
			generateCartId();
		return cartId;
	}
}
