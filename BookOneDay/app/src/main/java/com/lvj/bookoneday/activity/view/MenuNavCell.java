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
public class MenuNavCell extends RecyclerViewCell<MenuNav> {

    //有多个界面公用，通过该视图tag来判断是哪个界面。
    private int viewTag;  //界面标识 0：我

    public MenuNavCell(int layout, Context context, int viewTag) {
        super(layout, context);
        this.viewTag = viewTag;
    }

    @Override
    protected void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, MenuNav model) {

        ImageView menuIcon = viewHolderHelper.getView(R.id.menu_iconImageView);
        Glide.with(getContext()).load(model.getIcon()).into(menuIcon);
        viewHolderHelper.setText(R.id.menu_titleLabel, model.getTitle());
        viewHolderHelper.setTextColor(R.id.menu_detailLabel, R.color.lj_color_gray2);

        switch (viewTag){
            case 0:{
                //控制section header显示
                boolean showHeaderFlag = false;
                if(position == 1|| position == 4|| position == 6){
                    showHeaderFlag = true;
                }
                viewHolderHelper.setVisibility(R.id.menu_item_sectionHeader, showHeaderFlag ? View.VISIBLE : View.GONE);
                //控制括号显示
                String detailTextStr = model.getViewTypeCellPosition() == 0 ? "" :"("+ model.getDesc()+")";
                viewHolderHelper.setText(R.id.menu_detailLabel, detailTextStr);
                break;
            }
        }
    }
}
