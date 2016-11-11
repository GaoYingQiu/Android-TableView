package com.lvj.bookoneday.activity.baseController;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.lvj.bookoneday.helper.LvJinKuPaginator;
import com.lvj.bookoneday.helper.PaginatorDelegate;
import com.lvj.bookoneday.services.baseService.BaseResponse;
import com.lvj.bookoneday.widget.view.table.*;
import com.lvj.bookoneday.widget.view.table.refresh.adapter.RefreshControlType;

public abstract class TableViewController<T> extends FragmentController implements
        TableViewDataSource<T>, TableViewDelegate ,PaginatorDelegate {

    protected TableView<T> tableView;
    private static LvJinKuPaginator paginator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //分页
        paginator  = new LvJinKuPaginator(10,this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getRootFragment() {

        if (tableView == null) {

            TableView.Builder<T> builder = new GroupTableView.Builder<>();
            builder.setContext(this);
            builder.setRefreshType(getRefreshControlType());
            builder.setEnableLoadMore(enableLoadMore());
            //绑定数据源和事件委托
            builder.setDataSource(this);
            builder.setDelegate(this);
            tableView = builder.build();
        }
        return new TableViewFragment<>(tableView);
    }


    //Paginator Delegate
    @Override
    public void fetchResutlsWithPage(int page){

    }
    @Override
    public void showLoadMoreView(boolean showStatuTag){

    }

    //Request Delegate
    @Override
    public void onStatusOk(BaseResponse response, Class<?> type) {
        super.onStatusOk(response, type);
        onEnd();
    }

    @Override
    public void onStatusError(BaseResponse response) {
        super.onStatusError(response);
        onEnd();
    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                               Throwable throwable) {
        super.onRequestError(errorCode, errorResponse, throwable);
        onEnd();
    }

    // TableView delegate ， 继承该类的子类可自控事件。
    @Override
    public void onTableViewDidSelectRow(int row) {

    }
    @Override
    public void onTableViewDidLongClickRow(int row) {

    }

    @Override
    public void onTableViewDidChangeCheckRow(int row, boolean isChecked) {

    }

    @Override
    public void onItemChildClick(View childView, int position) {

    }

    @Override
    public void onTableViewLoadMore() {

    }

    @Override
    public void onTableViewRefresh() {

    }

    //加载结束
    public void onEnd() {
        tableView.reloadData();
        tableView.endRefresh();
        tableView.endLoadMore();
    }

    //下拉刷新样式
    protected RefreshControlType getRefreshControlType() {
        return RefreshControlType.BGANormal;
    }

    //加载结束
    protected void loadOver(boolean over) {
        if (over) {
            Toast toast = Toast.makeText(this, "加载完毕", Toast.LENGTH_SHORT);
            toast.show();
        }
        tableView.loadOver(over);
    }

    //是否有加载更多
    protected boolean enableLoadMore() {
        return true;
    }

    //get,set
    public static LvJinKuPaginator getPaginator() {
        return paginator;
    }

    public TableView<T> getTableView() {
        return tableView;
    }
}
