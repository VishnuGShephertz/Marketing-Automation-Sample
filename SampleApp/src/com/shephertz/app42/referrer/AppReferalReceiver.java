/**
 * 
 */
package com.shephertz.app42.referrer;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.App42Log;
import com.shephertz.app42.util.AppConstants;

/**
 * @author Vishnu
 *
 */
public final class AppReferalReceiver extends BroadcastReceiver
{
  public void onReceive(Context ctx, Intent intent)
  {
	
	  Log.d("APP42", intent.toString());
	
    String str = intent.getStringExtra("referrer");
    Log.d("APP42","Referrer");
    if ((!"com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) || (str == null))
      return;
    System.out.println("ReferId"+str);
    Log.d("APP42",str);
    App42API.initialize(
    		ctx,
			AppConstants.APIKey,
			AppConstants.SecretKey);
	App42Log.setDebug(true);
	App42API.enableAppStateEventTracking(true);
	try {
		App42API.buildEventService().trackEvent("RefererTrack", getJson(str), new App42CallBack() {
			
			@Override
			public void onSuccess(Object arg0) {
				// TODO Auto-generated method stub
				System.out.println("REFRER SUCCESSS");
			}
			
			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	} catch (App42Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  private JSONObject getJson(String referId) throws JSONException{
	  JSONObject jj=new JSONObject();
	  jj.put("referId", referId);
	  return jj;
  }
}