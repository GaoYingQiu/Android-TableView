package com.lvj.bookoneday.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.helper.LvJinKuPaginator;
import com.lvj.bookoneday.helper.PaginatorDelegate;
import com.lvj.bookoneday.services.baseService.BaseResponse;
import com.lvj.bookoneday.services.baseService.RequestCallBackDelegate;
import com.lvj.bookoneday.widget.view.table.GroupTableView;
import com.lvj.bookoneday.widget.view.table.TableView;
import com.lvj.bookoneday.widget.view.table.TableViewDataSource;
import com.lvj.bookoneday.widget.view.table.TableViewDelegate;
import com.lvj.bookoneday.widget.view.table.TableViewFragment;
import com.lvj.bookoneday.widget.view.table.refresh.adapter.RefreshControlType;
import com.shizhefei.fragment.LazyFragment;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/10,16:43
 */
public abstract class BaseListFragment<T> extends LazyFragment implements RequestCallBackDelegate, TableViewDataSource, TableViewDelegate,PaginatorDelegate {

    private TableView tableView;
    RelativeLayout containerParent;
    View container;
    private ProgressBar loadingView;
    private LinearLayout loadFailView;
    private LvJinKuPaginator paginator;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        paginator  = new LvJinKuPaginator(10,this);

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.base_activity_without_toolbar, null);
        setContentView(rootView);

        containerParent = (RelativeLayout) rootView.findViewById(R.id.base_noToolBar_container_parent);
        container = containerParent.findViewById(R.id.base_noToolbar_container);
        initFragment();
    }

    private void initFragment() {

        FragmentManager fm =  getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.base_noToolbar_container, getRootFragment());
        transaction.commit();
    }

    private Fragment getRootFragment() {
        if (tableView == null) {
            TableView.Builder<T> builder = new GroupTableView.Builder<>();
            builder.setContext(getActivity());
            builder.setRefreshType(getRefreshControlType());
            builder.setEnableLoadMore(bHasFooterRefresh());

            //绑定数据源和事件委托
            builder.setDataSource(this);
            builder.setDelegate(this);
            tableView = builder.build();
        }
        return new TableViewFragment<>(tableView);
    }

    private void showLoading(boolean show) {
        if (show) {
            if (loadingView == null) {
                loadingView = new ProgressBar(getActivity());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                containerParent.addView(loadingView, params);

            }
            loadingView.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        } else {
            if (loadingView != null) {
                loadingView.setVisibility(View.GONE);
            }
            container.setVisibility(View.VISIBLE);
        }
    }

    private void showLoadFail(boolean show) {
        if (show) {
            if (loadFailView == null) {
                loadFailView = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.customer_load_fail_view, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                containerParent.addView(loadFailView, params);
                loadFailView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickLoadFailView();
                    }
                });
            }
            loadFailView.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        } else {
            if (loadFailView != null) {
                loadFailView.setVisibility(View.GONE);
            }
            container.setVisibility(View.VISIBLE);
        }
    }

    //Pagitor delegate
    @Override
    public void fetchResutlsWithPage(int page){

    }
    @Override
    public void showLoadMoreView(boolean showStatuTag){

    }

    //tableView delegate
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
        fetchResutlsWithPage(getPaginator().getCurrentPage() + 1);
    }
    @Override
    public void onTableViewRefresh() {
        getPaginator().fetchFirstPage();
    }

    //Request Delegate methods
    @Override
    public void onStatusOk(BaseResponse response, Class<?> type) {
        onEnd();
        hideLoadingView();
        showLoadFail(false);
    }

    @Override
    public void onStatusError(BaseResponse response) {
        Toast toast = Toast.makeText(getActivity(), response.getErrorMsg(),
                Toast.LENGTH_SHORT);
        toast.show();
        onEnd();
        hideLoadingView();
        showLoadFailView();
    }
    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                               Throwable throwable) {
        if (errorCode == -2) {
            Toast toast = Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (errorCode == -1) {
            Toast toast = Toast.makeText(getActivity(), "解析失败", Toast.LENGTH_SHORT);
            toast.show();
        }

        onEnd();

        hideLoadingView();
        showLoadFailView();
    }

    //customer methods
    protected void onClickLoadFailView() {
        onTableViewRefresh();
    }
    public void onEnd() {
        tableView.endRefresh();
        tableView.endLoadMore();
        tableView.reloadData();
    }
    protected void loadOver(boolean over) {
        if (over) {
            Toast toast = Toast.makeText(getActivity(), "加载完毕", Toast.LENGTH_SHORT);
            toast.show();
        }
        tableView.loadOver(over);
    }
    protected void showLoadingView() {
        showLoading(true);
    }
    protected void hideLoadingView() {
        showLoading(false);
    }
    protected void showLoadFailView() {
        showLoadFail(true);
    }
    protected RefreshControlType getRefreshControlType(){
        return RefreshControlType.BGANormal;
    }
    protected boolean bHasFooterRefresh(){
        return true;
    }

    //get,set
    public  LvJinKuPaginator getPaginator() {
        return paginator;
    }
    public TableView getTableView() {
        return tableView;
    }
}
