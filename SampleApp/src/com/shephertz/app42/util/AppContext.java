package com.shephertz.app42.util;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.shephertz.app42.ma.R;

public class AppContext {

	public static final String AppName="SampleMa";
	
	public static String appTheme = "l";
	public static int appThemeStyle=R.style.GreenImageTheme;
	public static int textColor=Color.BLACK;;
	public static int headerimage = R.drawable.green;
	public static int headerColor =  Color.WHITE;
	public static int cellBackground =  R.drawable.cell_style_green;

	public static void setHeaderTheme(TextView header, String title) {
		header.setGravity(Gravity.CENTER);
		header.setText(title.trim());
		header.setTextColor(headerColor);
		header.setBackgroundResource(headerimage);
	}

}
