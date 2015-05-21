package com.shephertz.app42.ma;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.iam.App42CampaignAPI;
import com.shephertz.app42.ma.cart.CartCategoryList;
import com.shephertz.app42.ma.cart.CartCotext;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.push.plugin.App42GCMController;
import com.shephertz.app42.push.plugin.App42GCMController.App42GCMListener;
import com.shephertz.app42.util.App42Event;
import com.shephertz.app42.util.AppConstants;
import com.shephertz.app42.util.AppContext;
import com.shephertz.app42.util.Utils;

public class Menu extends Activity implements OnItemClickListener{

	private Vector<String> vctr_Menu_Text;
	private GridView gridMenu;
	private int gird_width;
    private final String GoogleProjectNo="1043599038916";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(AppContext.appThemeStyle);
		setContentView(R.layout.grid_menu);
		LinearLayout mainParent = (LinearLayout) findViewById(R.id.main_parent);
		mainParent.setBackgroundResource(R.drawable.news);
		buildHeader();
		App42CampaignAPI.setActivityContext(this);
		gridMenu = buildGridView();
		parseJsonMenu();
		MaApplication.generateCartId();
		try {
			loadDataFromFile();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void buildHeader() {
		RelativeLayout lowestLayout = (RelativeLayout) this
				.findViewById(R.id.header_layout);
		TextView header = (TextView) findViewById(R.id.page_header);
		lowestLayout.setBackgroundResource(AppContext.headerimage);
		header.setText("Marketing Automation");
		header.setTextColor(AppContext.headerColor);
	}
	
	public void onBackClicked(View view) {
		finish();
	}
	private void parseJsonMenu() {
		vctr_Menu_Text = new Vector<String>();
		
		Resources res = getResources();
		CartCotext.prizeUnit = res.getString(R.string.unit_rupee);
		vctr_Menu_Text.add(AppConstants.TxtElectroNics);
		vctr_Menu_Text.add(AppConstants.TxtMen);
		vctr_Menu_Text.add(AppConstants.TxtSupport);
		GridMenuAdapter gridAdapter = new GridMenuAdapter(this, vctr_Menu_Text,
			 gird_width);
		gridMenu.setAdapter(gridAdapter);
		gridMenu.setOnItemClickListener(this);
	}
	public void onStart() {
		super.onStart();
		if (CartCotext.isOrederPlaced) {
			CartCotext.isOrederPlaced=false;
			MaApplication.generateCartId();
		}
	}
	private GridView buildGridView() {

		GridView grid = (GridView) findViewById(R.id.menu_grid_view);
		
		Display display = getWindowManager().getDefaultDisplay();
		gird_width = (display.getWidth() / 4);
		if (gird_width > AppConstants.MAX_GRID_WIDTH) {
			gird_width = AppConstants.MAX_GRID_WIDTH;
		}
		grid.setColumnWidth(gird_width);
		return grid;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (vctr_Menu_Text.get(position).equalsIgnoreCase(AppConstants.TxtSupport)) {
			Intent productIntent = new Intent(Menu.this, ContactUs.class);
			startActivityForResult(productIntent, 0);
		} else if (vctr_Menu_Text.get(position).equalsIgnoreCase(
				AppConstants.TxtElectroNics)) {
			Intent productIntent = new Intent(Menu.this, CartCategoryList.class);
			productIntent.putExtra(AppConstants.Catalogue,
					AppConstants.TxtElectroNics);
			startActivityForResult(productIntent, 0);
		} else if (vctr_Menu_Text.get(position).equalsIgnoreCase(
				AppConstants.TxtMen)) {
			Intent productIntent = new Intent(Menu.this, CartCategoryList.class);
			productIntent.putExtra(AppConstants.Catalogue,
					AppConstants.TxtMen);
			startActivityForResult(productIntent, 0);
		} else {
			Toast.makeText(this, "No data is available", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void loadDataFromFile() throws JSONException{
		String electronicsData=loadJSONFromAsset("electronics.json");
		if(electronicsData!=null){
			JSONObject data=new JSONObject(electronicsData);
		MaApplication.setElectroNics(data);
		}
		 electronicsData=loadJSONFromAsset("mens.json");
		if(electronicsData!=null){
			JSONObject data=new JSONObject(electronicsData);
		MaApplication.setMens(data);
		}
		
	}
	
	public String loadJSONFromAsset(String fileName) {
		String json = null;
		try {
			InputStream is = getAssets().open(fileName);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}

	
	
}
