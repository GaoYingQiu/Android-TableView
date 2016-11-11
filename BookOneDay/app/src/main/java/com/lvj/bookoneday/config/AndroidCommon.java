package com.lvj.bookoneday.config;

import android.content.Context;

public class AndroidCommon {

	private static Context ctx;

	private static GlobalAppearance appearence = new GlobalAppearance();

	public final static AndroidCommon common = new AndroidCommon();
	public static AndroidCommon sharedInstance() {
		return common;
	}

	public static Context getContext() {
		return ctx;
	}

	public static GlobalAppearance getAppearence() {
		return appearence;
	}

	//通过controller创建的时候给这个单列中的变量赋值赋值
	public void onCreateActivity(Context context){
		this.ctx = context;
	}
}

