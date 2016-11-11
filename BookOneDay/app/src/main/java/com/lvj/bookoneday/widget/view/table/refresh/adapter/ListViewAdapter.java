package com.lvj.bookoneday.widget.view.table.refresh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ListViewAdapter {

	private RefreshListener refreshListener;  //刷新

	public abstract View getRootView();    //获取根View
	public abstract RecyclerView getRecyclerView(); //获取recycleView

	public abstract  void enableLoadMore(boolean enable); //是否开启 加载更多功能
	public abstract void endRefresh(); 		//结束刷新
	public abstract void endLoadMore(); 		//结束加载更多
	public abstract void loadOver(boolean over);//加载结束？

	//刷新
	protected void refresh() {
		if (refreshListener == null) {
			return;
		}
		refreshListener.onRefresh();
	}
	//加载更多
	protected void loadMore() {
		if (refreshListener == null) {
			return;
		}
		refreshListener.onLoadMore();
	}


	//get,set
	public RefreshListener getRefreshListener() {
		return refreshListener;
	}
	public void setRefreshListener(RefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}
}
