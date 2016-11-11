package com.lvj.bookoneday.activity.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvj.bookoneday.R;
import com.lvj.bookoneday.entity.Audio;
import com.lvj.bookoneday.entity.Book;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/10,12:43
 */
public class HomeRecommendCell extends RecyclerViewCell<Book> {

    private int viewTag;

    public HomeRecommendCell(int layout, Context context, int viewTag) {
        super(layout, context);
        this.viewTag = viewTag;
    }

    @Override
    protected void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, Book model) {

        boolean showHeaderFlag = true;
        if(position >5){
            showHeaderFlag = false;
        }
        viewHolderHelper.setVisibility(R.id.menu_item_sectionHeader, showHeaderFlag ? View.VISIBLE : View.GONE);
        viewHolderHelper.setVisibility(R.id.sectionHeader_today_recommend, showHeaderFlag ? View.VISIBLE : View.GONE);
        ImageView bookIcon = viewHolderHelper.getView(R.id.home_recommend_book_icon);
        Glide.with(getContext()).load(R.drawable.test_banner).into(bookIcon);
        viewHolderHelper.setText(R.id.home_recommend_title, model.getTitle());
        viewHolderHelper.setText(R.id.home_recommend_desc, model.getDesc());
        viewHolderHelper.setText(R.id.home_recommend_time, model.getAudioTime());
        viewHolderHelper.setText(R.id.home_recommend_money, model.getReadCoin()+"阅读币");
    }
}
