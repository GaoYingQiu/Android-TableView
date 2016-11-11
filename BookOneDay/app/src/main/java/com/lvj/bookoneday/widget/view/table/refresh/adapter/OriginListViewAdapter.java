package com.lvj.bookoneday.widget.view.table.refresh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.lvj.bookoneday.R;

/**
 * 这个类为最原始的表格。无下拉刷新功能
 */
public class OriginListViewAdapter extends ListViewAdapter implements
		OnRefreshListener {

	private RecyclerView recyclerView;
	private RelativeLayout layout;

	@SuppressLint("InflateParams")
	public OriginListViewAdapter(Context context) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.base_origin_listview, null);
		layout = (RelativeLayout)view.findViewById(R.id.origin_layout);
		recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
	}

	@Override
	public View getRootView() {
		return layout;
	}

	@Override
	public RecyclerView getRecyclerView() {
		return recyclerView;
	}

	@Override
	public void enableLoadMore(boolean enable) {

	}

	@Override
	public void endRefresh() {


	}

	@Override
	public void endLoadMore() {

	}

	@Override
	public void onRefresh() {

	}

	@Override
	public void loadOver(boolean over) {

	}

}
