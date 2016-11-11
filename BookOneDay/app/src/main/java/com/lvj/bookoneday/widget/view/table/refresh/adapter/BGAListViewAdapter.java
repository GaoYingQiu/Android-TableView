package com.lvj.bookoneday.widget.view.table.refresh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.lvj.bookoneday.R;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout.BGARefreshLayoutDelegate;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

public class BGAListViewAdapter extends ListViewAdapter implements
        BGARefreshLayoutDelegate {

    private BGARefreshLayout refreshLayout;
    private BGARefreshViewHolder refreshViewHolder;
    private RecyclerView recyclerView;
    private boolean loadMoreOver; //加载更多是否到达最后一页
    private Context contenxt;
    private boolean bHasFooterRefresh;

    @SuppressLint("InflateParams")
    public BGAListViewAdapter(Context context,
                              RefreshType type,boolean bHasFooterRefresh) {
        this.contenxt = context;
        this.bHasFooterRefresh = bHasFooterRefresh;
        View view = LayoutInflater.from(context).inflate(R.layout.bga, null);
        refreshLayout = (BGARefreshLayout) view
                .findViewById(R.id.rl_modulename_refresh);
        refreshLayout.setDelegate(this);
        refreshLayout.setIsShowLoadingMoreView(true);

        switch (type) {
            case Normal:
                refreshViewHolder = new BGANormalRefreshViewHolder(context, true);
                break;
            case MoocStyle:
                refreshViewHolder = new BGAMoocStyleRefreshViewHolder(context,
                        true);
                break;
            case Stickiness:
                refreshViewHolder = new BGAStickinessRefreshViewHolder(context,
                        true);
                break;
            default:
                break;
        }

        refreshLayout.setRefreshViewHolder(refreshViewHolder);

        refreshViewHolder.setLoadingMoreText("载入中...");
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.lj_color_gray3);
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.lj_color_gray3);
        recyclerView = (RecyclerView) view
                .findViewById(R.id.rl_modulename_refresh_recycle_view);
    }

    @Override
    public View getRootView() {
        return refreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    //控制是否加载更多 开关。
    @Override
    public void enableLoadMore(boolean enable) {

//        if(enable) {
//            listView.setOnBottomListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listView.onBottomBegin();
//                    loadMore();
//                }
//            });
//        }
    }

    @Override
    public void endRefresh() {
        refreshLayout.endRefreshing();
    }
    @Override
    public void endLoadMore() {
        refreshLayout.endLoadingMore();
    }
    @Override
    public void loadOver(boolean over) {
        loadMoreOver = over;
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if(!bHasFooterRefresh){ //如果没有底部加载更多
            return  false;
        }

        if(loadMoreOver){
            endLoadMore();
            Toast toast = Toast.makeText(this.contenxt, "没有更多数据了", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        loadMore(); //加载更多
        return true;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();  //刷新
    }

    public static enum RefreshType {
        Normal, MoocStyle, Stickiness
    }
}
