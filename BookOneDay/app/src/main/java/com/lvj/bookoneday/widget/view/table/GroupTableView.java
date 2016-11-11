package com.lvj.bookoneday.widget.view.table;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.lvj.bookoneday.widget.view.table.refresh.adapter.RefreshControlType;

public class GroupTableView<T> extends TableView<T> {

    protected GroupTableView(Context context, RefreshControlType type,
                             boolean enableLoadMore,
                             TableViewDataSource<T> dataSource,
                             TableViewDelegate delegate) {
       super(context, type, enableLoadMore, dataSource, delegate);
    }

    public static class Builder<T> extends TableView.Builder<T> {
        public GroupTableView<T> build() {
            return new GroupTableView<>(getContext(), getRefreshType(),
                    isEnableLoadMore(),  getDataSource(), getDelegate());
        }
    }
}
