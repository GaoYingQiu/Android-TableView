package com.lvj.bookoneday.activity.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvj.bookoneday.R;
import com.lvj.bookoneday.entity.MenuNav;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/10,12:43
 */
public class HomeNextPageCell extends RecyclerViewCell<Object> {

    private int viewTag;  //界面标识

    public HomeNextPageCell(int layout, Context context, int viewTag) {
        super(layout, context);
        this.viewTag = viewTag;
    }

    @Override
    protected void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, Object model) {

    }
}
