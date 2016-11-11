package com.lvj.bookoneday.widget.view.table;

import android.view.View;

public interface TableViewDelegate {

	void onTableViewDidSelectRow(int row);//点击单行
    void onTableViewDidLongClickRow(int row); //长按单行
 	void onTableViewDidChangeCheckRow(int row, boolean isChecked); //选中行
	void onItemChildClick(View childView, int position); //item 点击


	void onTableViewRefresh();//下拉刷新
	void onTableViewLoadMore();  //加载更多
}
