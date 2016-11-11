package com.lvj.bookoneday.activity.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.activity.fragment.BookshelfFragment;
import com.lvj.bookoneday.activity.fragment.FindFragment;
import com.lvj.bookoneday.activity.fragment.HomeFragment;
import com.lvj.bookoneday.activity.fragment.MeFragment;
import com.lvj.bookoneday.activity.fragment.WordsFragment;
import com.lvj.bookoneday.config.AndroidCommon;
import com.lvj.bookoneday.widget.view.BadgeView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.IndicatorViewPager.IndicatorFragmentPagerAdapter;
import com.shizhefei.view.viewpager.SViewPager;

import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("ResourceType")
public class TabMainActivity extends FragmentActivity {

	private IndicatorViewPager indicatorViewPager;
	private Map<Integer, View> badgeMaskMap = new HashMap<>();
	private Map<Integer, View> tipMap = new HashMap<>();
	private Map<Integer, BadgeView> iconBadgeMap = new HashMap<>();

	private TextView projectTitleView;
	private Toolbar navToolBar;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu); //NullPointer Here
		return true;
	}


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//网络状态
		AndroidCommon.sharedInstance().onCreateActivity(this);

		setContentView(R.layout.fragment_maintab);
		navToolBar = (Toolbar)findViewById(R.id.toolbar);
		navToolBar.findViewById(R.id.nav_audio_play).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent musicPlayIntent = new Intent(TabMainActivity.this,PlayAudioController.class);
				startActivity(musicPlayIntent);

//				Intent musicPlayIntent = new Intent(TabMainActivity.this,MediaRecordAudioActivity.class);
//				startActivity(musicPlayIntent);
				//	媒体播放 MediaDemo
				//  短声音播放 SoundPoolActivity
				//	MediaRecordAudioActivity 录音 	AudioRecordActivity  获取录音信息
			}
		});
		navToolBar.findViewById(R.id.nav_audio_date).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Context cxt = TabMainActivity.this;
				Intent dateSearchAudioIntent = new Intent(cxt,DateSearchAudioController.class);
				startActivity(dateSearchAudioIntent);
				TabMainActivity.this.overridePendingTransition(R.anim.activity_dismiss,0);
			}
		});

		projectTitleView = (TextView)findViewById(R.id.projectToolBarTitle);
		SViewPager viewPager = (SViewPager) findViewById(R.id.tabmain_viewPager);
		Indicator indicator = (Indicator) findViewById(R.id.tabmain_indicator);
		indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
		indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setCanScroll(false);
		viewPager.setOffscreenPageLimit(5);
	}


	private class MyAdapter extends IndicatorFragmentPagerAdapter {
		private String[] tabNames = { "首页", "金句", "发现","书架","我"};
		private int[] tabIcons = { R.drawable.maintab_1_selector, R.drawable.maintab_2_selector,
				R.drawable.maintab_3_selector,R.drawable.maintab_4_selector,R.drawable.maintab_5_selector};
		private LayoutInflater inflater;

		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			inflater = LayoutInflater.from(getApplicationContext());
		}

		@Override
		public int getCount() {
			return tabNames.length;
		}

		@Override
		public View getViewForTab(int position, View convertView, ViewGroup container) {

			//tabitem 的View 加红点功能
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.tab_item_badge_main, container, false);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.tab_item_icon);
			icon.setImageDrawable(getResources().getDrawable(tabIcons[position]));

			View mask = convertView.findViewById(R.id.mask);
			badgeMaskMap.put(position, mask);

			View tip = convertView.findViewById(R.id.tab_item_tip);
			tipMap.put(position, tip);

			TextView textView = (TextView) convertView.findViewById(R.id.tab_item_text);
			textView.setText(tabNames[position]);
			Resources resource = getResources();
			ColorStateList csl = resource.getColorStateList(R.drawable.tab_main_selector);
			if (csl != null) {
				textView.setTextColor(csl);
			}
			return convertView;
		}

		@Override
		public Fragment getFragmentForPage(int position) {
			Fragment fragment;
			switch (position) {
				case 0: {
					fragment = new HomeFragment();
					break;
				}
				case 1:{
					fragment = new WordsFragment();
					break;
				}
				case 2:{
					fragment = new FindFragment();
					break;
				}
				case 3:{
					fragment = new BookshelfFragment();
					break;
				}
				default: {
					fragment = new MeFragment();
					break;
				}
			}
			return fragment;
		}
	}

	public void showTip(int index, boolean show) {
		View tip = tipMap.get(index);
		if (show) {
			tip.setVisibility(View.VISIBLE);
		} else {
			tip.setVisibility(View.GONE);
		}
	}


	public void setBadge(int index, int value, Context context) {

		if (index >= 3) return;

		BadgeView badge = iconBadgeMap.get(index);
		View mask = badgeMaskMap.get(index);

		if (badge == null) {
			badge = new BadgeView(context, mask);
			badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
			badge.setTextColor(Color.WHITE);
			badge.setBadgeBackgroundColor(Color.RED);
			badge.setTextSize(12);
			badge.setBadgeMargin(65, 20);

			iconBadgeMap.put(index, badge);


		}
		badge.setText(String.valueOf(value));
		if (value > 0)
			badge.show();
		else
			badge.hide();
	}

	public void  setProjectTitleText(String text){
		projectTitleView.setText(text);
	}

	public void changeNavToolBarVisibility(int statuTag){
		navToolBar.setVisibility(statuTag == 1 ?View.VISIBLE : View.GONE);
	}

	@Override
	protected void onDestroy() {

		//QiuMediaPlayer.getInstance((AudioManager)this.getSystemService(Context.AUDIO_SERVICE),null).cleanMediaResource();
		super.onDestroy();
	}
}
