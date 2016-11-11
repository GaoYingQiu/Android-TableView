package com.lvj.bookoneday.config;


import com.lvj.bookoneday.R;

public class GlobalAppearance {

	private int statusBarTintColor;

	public GlobalAppearance(){
		statusBarTintColor = R.color.lj_color_orange;
	}

	public void setStatusBarTintColor(int color) {
		statusBarTintColor = color;
	}

	public int getStatusBarTintColor() {
		return statusBarTintColor;
	}
}
