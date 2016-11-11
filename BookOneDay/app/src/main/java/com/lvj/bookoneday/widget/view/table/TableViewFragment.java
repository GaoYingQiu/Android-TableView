package com.lvj.bookoneday.widget.view.table;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TableViewFragment<T> extends Fragment {

	private TableView<T> tableView;

	@SuppressLint("ValidFragment")
	public TableViewFragment(TableView<T> tableView) {
		this.tableView = tableView;
	}
	
	public TableViewFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return tableView.getView();  //这里会崩溃，空指针异常
	}
}
