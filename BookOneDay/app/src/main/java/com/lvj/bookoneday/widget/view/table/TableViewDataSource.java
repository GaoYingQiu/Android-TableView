package com.lvj.bookoneday.widget.view.table;

/*
 * 定义表格的数据源接口
 */
public interface TableViewDataSource<T> {

	RecyclerViewCell<T>[]  getRecyclerCells(); //获取cell
	int getItemViewType(int position);  //获取position: cell类型的tag
}
