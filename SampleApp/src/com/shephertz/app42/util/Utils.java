package com.shephertz.app42.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;

public class Utils {

	
	public static boolean appClose = false;
	

	public static String getJsonString(JSONObject json, String key) {
		try {
			return json.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String urlSpaceEncode(String sUrl) {
		StringBuffer urlOK = new StringBuffer();
		for (int i = 0; i < sUrl.length(); i++) {
			char ch = sUrl.charAt(i);
			switch (ch) {
			case ' ':
				urlOK.append("%20");
				break;
			default:
				urlOK.append(ch);
				break;
			}
		}
		return urlOK.toString();
	}

	/**
	 * Function to convert milliseconds time to Timer Format
	 * Hours:Minutes:Seconds
	 * */
	public static String milliSecondsToTimer(long milliseconds) {
		String finalTimerString = "";
		String secondsString = "";

		// Convert total duration into time
		int hours = (int) (milliseconds / (1000 * 60 * 60));
		int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
		int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
		// Add hours if there
		if (hours > 0) {
			finalTimerString = hours + ":";
		}

		// Prepending 0 to seconds if it is one digit
		if (seconds < 10) {
			secondsString = "0" + seconds;
		} else {
			secondsString = "" + seconds;
		}

		finalTimerString = finalTimerString + minutes + ":" + secondsString;

		// return timer string
		return finalTimerString;
	}

	public static String getContents(JSONObject jsonObject, String jsonKey,
			String contentKey) {
		try {
			JSONObject jsonObjectForAboutUs = jsonObject.getJSONObject(jsonKey);
			return jsonObjectForAboutUs.getString(contentKey);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getAccountId(Context context) {
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		String accountId = null;
		Account[] accounts = AccountManager.get(context).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				accountId = account.name;

				return accountId;

			} else if (account.type.equalsIgnoreCase("com.whatsapp")) {
				accountId = account.name;

				return accountId;
			}
		}

		return getUnique_Id();

	}

	/*
	 * this function is used to generate Unique randon Id
	 * 
	 * @return encoded Id
	 */
	public static String getUnique_Id() {
		Date dt = new Date();
		Long ln = new Long(dt.getTime());
		return ln.toString();
	}
	public static void clearCache(Context context) {
		try {
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		// final int buffer_size = ;
		try {
			byte[] bytes = new byte[1024 * 8];
			for (;;) {
				int count = is.read(bytes, 0, 1024 * 8);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	/*
	 * This method is used to check availability of network connection in
	 * android device uses CONNECTIVITY_SERVICE of android device to get desired
	 * network internet connection
	 * 
	 * @return status of availability of internet connection in true or false
	 * manner
	 */
	public static boolean haveNetworkConnection(Context context) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] netInfo = cm.getAllNetworkInfo();
			for (NetworkInfo ni : netInfo) {
				if (ni.getTypeName().equalsIgnoreCase("WIFI"))
					if (ni.isConnected())
						haveConnectedWifi = true;
				if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
					if (ni.isConnected())
						haveConnectedMobile = true;
			}

		} catch (Exception e) {

		}
		return haveConnectedWifi || haveConnectedMobile;
	}

	public static void trackEvent(String eventName, JSONObject dataProps) {
		App42API.buildEventService().trackEvent(eventName, dataProps,
				new App42CallBack() {

					@Override
					public void onSuccess(Object arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onException(Exception arg0) {
						// TODO Auto-generated method stub

					}
				});
	}
	public static void trackEvent(String eventName) {
		App42API.buildEventService().trackEvent(eventName, 
				new App42CallBack() {

					@Override
					public void onSuccess(Object arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onException(Exception arg0) {
						// TODO Auto-generated method stub

					}
				});
	}
}