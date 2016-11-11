package com.lvj.bookoneday.activity.baseController;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.services.baseService.BaseResponse;

//customer import

public abstract class FragmentController extends Controller {

    private TextView toolbarTextVeiw;

    protected RelativeLayout containerParent;

    private View container;

    private ProgressBar loadingView;

    private LinearLayout loadFailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        View rootView = getLayoutInflater().inflate(R.layout.base_activity, null);
        setContentView(rootView);

        containerParent = (RelativeLayout) rootView.findViewById(R.id.container_parent);
        container = containerParent.findViewById(R.id.container);
        initNavbar();
        if (getRootFragment() != null){
            initFragment();
        }
        findViewById(R.id.footparent).setVisibility(View.GONE);
    }

    private void initNavbar() {

        toolbarTextVeiw = (TextView) findViewById(R.id.toolbar_title);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (!enableReturnButton()) {
            mToolbar.setNavigationIcon(null);
        }
        toolbarTextVeiw.setText(getNavTitle());
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setBackgroundColor(getStatusBarColor());
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickReturnButton();
            }
        });
    }

    protected void changeTitle(String title) {
        toolbarTextVeiw.setText(title);
    }

    private void initFragment() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, getRootFragment());
        transaction.commit();
    }


    private void showLoading(boolean show) {
        if (show) {
            if (loadingView == null) {
                loadingView = new ProgressBar(this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                containerParent.addView(loadingView, params);

            }
            loadingView.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        } else {
            if (loadingView != null)
                loadingView.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
        }
    }


    private void showLoadFail(boolean show) {


        if (show) {

            if (loadFailView == null) {
                loadFailView = (LinearLayout) getLayoutInflater().inflate(R.layout.customer_load_fail_view, null);
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
            if (loadFailView != null)
                loadFailView.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
        }

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


    protected void hideLoadFailView() {
        showLoadFail(false);
    }


    protected boolean enableAutoLoadStateView() {
        return true;
    }

    @Override
    public void onStatusOk(BaseResponse response, Class<?> type) {
        super.onStatusOk(response, type);
        if (enableAutoLoadStateView()) {
            hideLoadingView();
            hideLoadFailView();
        }
    }

    @Override
    public void onStatusError(BaseResponse response) {
        super.onStatusError(response);
        if (enableAutoLoadStateView()) {
            hideLoadingView();
            showLoadFailView();
        }

    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                               Throwable throwable) {
        super.onRequestError(errorCode, errorResponse, throwable);

        if (enableAutoLoadStateView()) {
            hideLoadingView();
            showLoadFailView();
        }
    }


    protected abstract Fragment getRootFragment();

    protected int getFragmentPadding() {
        return 0;
    }

    protected boolean enableReturnButton() {
        return true;
    }

    protected String getNavTitle() {
        return "";
    }

    protected void onClickReturnButton() {
        this.finish();
    }

    protected void onClickLoadFailView() {
    }

}
