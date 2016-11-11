package com.lvj.bookoneday.activity.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.entity.FoundList;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.transformer.RotatePageTransformer;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/10,12:43
 */
public class FoundBannerCell extends RecyclerViewCell<FoundList> {

    private int viewTag;  //界面标识 0：发现

    public FoundBannerCell(int layout, Context context, int viewTag) {
        super(layout, context);
        this.viewTag = viewTag;
    }

    @Override
    protected void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, FoundList model) {

        switch (viewTag){
            case 0:{
                //banner切换
                BGABanner banner = (BGABanner)viewHolderHelper.getView(R.id.banner_splash_pager);
                banner.setTransitionEffect(BGABanner.TransitionEffect.Default);
                banner.setPageChangeDuration(1000);
                List<View> views = new ArrayList<>();
                for (int i=0; i<model.getBookList().size(); i++) {
                    views.add(getPageView(R.drawable.test_banner));
                }
                banner.setViews(views);
                break;
            }
        }
    }

    private View getPageView(@DrawableRes int resid) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(resid);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
