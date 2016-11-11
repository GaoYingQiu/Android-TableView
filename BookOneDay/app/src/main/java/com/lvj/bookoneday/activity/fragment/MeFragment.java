package com.lvj.bookoneday.activity.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.activity.controllers.TabMainActivity;
import com.lvj.bookoneday.activity.view.MenuNavCell;
import com.lvj.bookoneday.entity.MenuNav;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;
import com.lvj.bookoneday.widget.view.table.RecyclerViewHeader;
import com.lvj.bookoneday.widget.view.table.refresh.adapter.RefreshControlType;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends BaseListFragment<MenuNav>{

	private  List<MenuNav> mineMenuList = new ArrayList<MenuNav>();
	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		initData();
		super.onCreateViewLazy(savedInstanceState);

		//header
 		RecyclerViewHeader recyclerViewHeader = RecyclerViewHeader.fromXml(getContext(), R.layout.me_header);
  	    recyclerViewHeader.attachTo(getTableView().getListViewAdapter().getRecyclerView());
		getTableView().getTableViewAdapter().setDatas(mineMenuList);
	}

	//初始化列表
	private void initData(){
		mineMenuList.add(new MenuNav( R.mipmap.me_ranking,"学分排名","", 0));

		mineMenuList.add(new MenuNav( R.mipmap.me_like_sounds,"喜欢的音频","1", 1));
		mineMenuList.add(new MenuNav(R.mipmap.me_like_words,"喜欢的金句", "0", 1));
		mineMenuList.add(new MenuNav(R.mipmap.me_download,"已下载的音频", "2", 1));

		mineMenuList.add(new MenuNav(R.mipmap.me_coin,"书虫币", "0", 2));
		mineMenuList.add(new MenuNav(R.mipmap.me_orders,"购买记录", "", 0));

		mineMenuList.add(new MenuNav(R.mipmap.me_settings,"设置", "", 0));
	}

	protected RefreshControlType getRefreshControlType(){
		return RefreshControlType.None;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public RecyclerViewCell<MenuNav>[] getRecyclerCells() {
		return new RecyclerViewCell[]{
				new MenuNavCell(R.layout.me_cell_menu,getContext(),0)};
	}

	@Override
	protected void onFragmentStartLazy() {

 		TabMainActivity tabMainActivity = (TabMainActivity ) getActivity();
		tabMainActivity.changeNavToolBarVisibility(0);
		if(getRefreshControlType() != RefreshControlType.None) {
			onEnd();
		}
		super.onFragmentStartLazy();
	}
}
