package com.lvj.bookoneday.widget.view.table;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.lvj.bookoneday.widget.view.table.refresh.adapter.*;

import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildCheckedChangeListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;


/**
 * Created Description
 * 封装表格,具有刷新，单行点击，单行长按，单行中item点击，item 长按，item 选中。 的功能
 * @Author: qiugaoying
 * @createTime 2015/11/10,16:43
 */
public class TableView<T> implements RefreshListener, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener,
        BGAOnItemChildLongClickListener, BGAOnItemChildCheckedChangeListener {

    private Map<RefreshControlType, ListViewAdapter> adapterMap = new HashMap<>();
    private TableViewAdapter tableViewAdapter;
    private TableViewDataSource<T> dataSource;
    private TableViewDelegate delegate;
    private RefreshControlType refreshType;
    private Context context;
    private boolean enableLoadMore = false;
    private ItemTouchHelper mItemTouchHelper;
    private ListViewAdapter listViewAdapter;

    protected TableView(Context context, RefreshControlType type,
                         boolean enableLoadMore,
                          TableViewDataSource<T> dataSource, TableViewDelegate delegate) {
        this.context = context;
        this.enableLoadMore = enableLoadMore;
        refreshType = type;
        this.dataSource = dataSource;
        this.delegate = delegate;
        init();
        //绑定recycleView的头部
    }


    private void init() {

        listViewAdapter = getAdapter();

        //setListener
        tableViewAdapter = new TableViewAdapter(listViewAdapter.getRecyclerView(),dataSource.getRecyclerCells());
        tableViewAdapter.setOnRVItemClickListener(this);
        tableViewAdapter.setOnRVItemLongClickListener(this);
        tableViewAdapter.setOnItemChildClickListener(this);
        tableViewAdapter.setOnItemChildLongClickListener(this);
        tableViewAdapter.setOnItemChildCheckedChangeListener(this);

        //设置刷新
//        ListViewAdapter listViewAdapter = getAdapter();
        listViewAdapter.setRefreshListener(this);

        //设置布局样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        listViewAdapter.getRecyclerView().setLayoutManager(layoutManager);
       // listViewAdapter.getRecyclerView().addItemDecoration(new Divider(getContext()));

        //给RecyclerView 设置item Touch Event. 右滑动，拖动功能
//         mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
//         mItemTouchHelper.attachToRecyclerView(listViewAdapter.getRecyclerView());
//        tableViewAdapter.setItemTouchHelper(mItemTouchHelper);

        //给RecyclerView设置适配器
        listViewAdapter.getRecyclerView().setAdapter(tableViewAdapter);
    }

    private ListViewAdapter getAdapter(RefreshControlType type) {
        ListViewAdapter adapter = adapterMap.get(type);
        if (adapter == null) {
            switch (type) {
                case None:
                    adapter = new OriginListViewAdapter(context);
                    break;
                case BGANormal:
                    adapter = new BGAListViewAdapter(context, BGAListViewAdapter.RefreshType.Normal,this.enableLoadMore);
                    break;
                case BGAMooc:
                    adapter = new BGAListViewAdapter(context, BGAListViewAdapter.RefreshType.MoocStyle,this.enableLoadMore);
                    break;
                case BGAStickiness:
                    adapter = new BGAListViewAdapter(context, BGAListViewAdapter.RefreshType.Stickiness,this.enableLoadMore);
                    break;
                default:
                    break;
            }

            adapter.enableLoadMore(enableLoadMore);
            adapterMap.put(type, adapter);
        }
        return adapter;
    }

    public Context getContext() {
        return context;
    }

    private ListViewAdapter getAdapter() {
        return getAdapter(refreshType);
    }

    public View getView() {
        return listViewAdapter.getRootView();
    }

    public ListViewAdapter getListViewAdapter() {
        return listViewAdapter;
    }

    public TableViewAdapter getTableViewAdapter() {
        return tableViewAdapter;
    }

    public void reloadData() {
        tableViewAdapter.notifyDataSetChanged();
    }

    public class TableViewAdapter extends BGARecyclerViewAdapter {

        private ItemTouchHelper mItemTouchHelper;
        private RecyclerViewCell[] recyclerViewCells; //cell 数组

        public TableViewAdapter(RecyclerView recyclerView,RecyclerViewCell[] recyclerViewCells) {
            super(recyclerView, 0); //初始化一些参数
            this.recyclerViewCells = recyclerViewCells;
        }

        @Override
        public int getItemViewType(int position) {
           //描述哪行是哪个视图Tag
            return dataSource.getItemViewType(position);
        }

        @Override
        public BGARecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            BGARecyclerViewHolder viewHolder = new BGARecyclerViewHolder(mRecyclerView, LayoutInflater.from(mContext).inflate(recyclerViewCells[viewType].getLayout(), parent, false), mOnRVItemClickListener, mOnRVItemLongClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildClickListener(mOnItemChildClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(mOnItemChildLongClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildCheckedChangeListener(mOnItemChildCheckedChangeListener);
            setCellItemChildListeners(viewHolder.getViewHolderHelper(), viewType);
            return viewHolder;
        }

        @Override
        //填充数据
        public void fillData(BGAViewHolderHelper viewHolderHelper, int position, Object model) {

           recyclerViewCells[getItemViewType(position)].refreshCellData(viewHolderHelper, position, model);
        }

        public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
            mItemTouchHelper = itemTouchHelper;
        }

        //为对应的cell注册item 事件
        public void setCellItemChildListeners(final BGAViewHolderHelper viewHolderHelper,int viewType) {
            recyclerViewCells[viewType].setItemChildListener(viewHolderHelper);
        }
    }

        @Override
        public void onRefresh() {

            if (delegate == null) {
                return;
            }

            delegate.onTableViewRefresh();
        }

        @Override
        public void onLoadMore() {
            if (delegate == null) {
                return;
            }
            delegate.onTableViewLoadMore();
        }

        //Customer Methods
        public void endRefresh() {
            listViewAdapter.endRefresh();
        }
        public void endLoadMore() {
            listViewAdapter.endLoadMore();
        }
        public void loadOver(boolean over) {
            listViewAdapter.loadOver(over);
        } //加载到最后一页了

        //表格cell的选中事件
        @Override
        public void onItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked) {
            // 在填充数据列表时，忽略选中状态变化
            if (delegate != null) {
                delegate.onTableViewDidChangeCheckRow(position,isChecked);
            }
        }
        //点击cell 里面的 子view
        @Override
        public void onItemChildClick(ViewGroup parent, View childView, int position) {

            if(delegate != null ){
                delegate.onItemChildClick(childView, position);
            }
        }

        // 长按单元格里面的控件
        @Override
        public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
//        if (childView.getId() == R.id.tv_item_normal_delete) {
//            showSnackbar("长按了删除 " + mAdapter.getItem(position).title);
//            return true;
//        }
            return false;
        }

        //长按了cell
        @Override
        public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
            if (delegate != null) {
                delegate.onTableViewDidLongClickRow(position);
            }
            return true;
        }
        //点击了 cell
        @Override
        public void onRVItemClick(ViewGroup parent, View itemView, int position) {
               if (delegate != null) {
                    delegate.onTableViewDidSelectRow(position);
               }
        }


    //TableView Builder
        public static class Builder<T> {

            private RefreshControlType refreshType = RefreshControlType.None;
            private TableViewDataSource<T> dataSource;
            private TableViewDelegate delegate;
            private Context context;
            private boolean enableLoadMore = false;

            public Builder<T> setRefreshType(RefreshControlType refreshType) {
                this.refreshType = refreshType;
                return this;
            }

            public Builder<T> setDelegate(TableViewDelegate delegate) {
                this.delegate = delegate;
                return this;
            }

            public Builder<T> setContext(Context context) {
                this.context = context;
                return this;
            }

            public Builder<T> setEnableLoadMore(boolean enableLoadMore) {
                this.enableLoadMore = enableLoadMore;
                return this;
            }

            public Builder<T> setDataSource(TableViewDataSource<T> dataSource) {
                this.dataSource = dataSource;
                return this;
            }
            public RefreshControlType getRefreshType() {
                return refreshType;
            }

            public TableViewDelegate getDelegate() {
                return delegate;
            }

            public Context getContext() {
                return context;
            }
            public boolean isEnableLoadMore() {
                return enableLoadMore;
            }

            public TableViewDataSource<T> getDataSource() {
                return dataSource;
            }

            public TableView<T> build() {
                return new TableView<>(context, refreshType, enableLoadMore, dataSource, delegate);
            }
        }

    /**
     * 该类参考：https://github.com/iPaulPro/Android-ItemTouchHelper-Demo
     */
    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
        public static final float ALPHA_FULL = 1.0f;

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            }

            tableViewAdapter.moveItem(source.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            tableViewAdapter.removeItem(viewHolder.getAdapterPosition());
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
                ViewCompat.setAlpha(viewHolder.itemView, alpha);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setSelected(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
            viewHolder.itemView.setSelected(false);
        }
    }
}
