package com.lvj.bookoneday.activity.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.activity.controllers.TabMainActivity;
import com.lvj.bookoneday.activity.view.BookShelfCell;
import com.lvj.bookoneday.activity.view.FoundBannerCell;
import com.lvj.bookoneday.entity.Book;
import com.lvj.bookoneday.entity.MenuNav;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;
import com.lvj.bookoneday.widget.view.table.refresh.adapter.RefreshControlType;

import java.util.ArrayList;
import java.util.List;

public class BookshelfFragment extends BaseListFragment<Object>{

	private List<Book> bookShelfList = new ArrayList<Book>();

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		initData();

		super.onCreateViewLazy(savedInstanceState);

		//设置背景
		RelativeLayout layout = (RelativeLayout)getTableView().getListViewAdapter().getRecyclerView().getParent();
		layout.setBackgroundColor(getResources().getColor(R.color.lj_color_white));
		//设置recyclerview 布局
		RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),3);
		getTableView().getListViewAdapter().getRecyclerView().setLayoutManager(layoutManager);
		getTableView().getTableViewAdapter().setDatas(bookShelfList);
	}

	private void initData() {

		bookShelfList.add(new Book(1,"http://图片路径","罗马人的故事",22.1f));
		bookShelfList.add(new Book(2,"http://图片路径","想恋爱,先变坏",0.99f));
		bookShelfList.add(new Book(3,"http://图片路径","融资时,拿谁的钱最有利",9.99f));
		bookShelfList.add(new Book(4,"http://图片路径","巨富",9.99f));
		bookShelfList.add(new Book(5,"http://图片路径","Google 改变时代的技术",0.99f));
		bookShelfList.add(new Book(6,"http://图片路径","想恋爱,先变坏",0.99f));
	}

	protected RefreshControlType getRefreshControlType(){
		return RefreshControlType.None;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public RecyclerViewCell<Object>[] getRecyclerCells() {
		return new RecyclerViewCell[]{
				new BookShelfCell(R.layout.bookshelf_cell_book,getContext()),
		};
	}

	@Override
	protected void onFragmentStartLazy() {

		TabMainActivity tabMainActivity = (TabMainActivity ) getActivity();
		tabMainActivity.setProjectTitleText("书架");
		tabMainActivity.changeNavToolBarVisibility(1);
		if(getRefreshControlType() != RefreshControlType.None) {
			onEnd();
		}
		super.onFragmentStartLazy();
	}
}
