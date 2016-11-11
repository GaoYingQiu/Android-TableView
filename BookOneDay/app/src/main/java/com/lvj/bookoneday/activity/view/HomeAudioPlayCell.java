package com.lvj.bookoneday.activity.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvj.bookoneday.R;
import com.lvj.bookoneday.entity.Audio;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;
import com.nineoldandroids.view.ViewHelper;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/10,12:43
 */
public class HomeAudioPlayCell extends RecyclerViewCell<Integer> {

    private int viewTag;

    public HomeAudioPlayCell(int layout, Context context, int viewTag) {
        super(layout, context);
        this.viewTag = viewTag;
    }

    @Override
    protected void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, Integer model) {
        ImageView menuIcon = viewHolderHelper.getView(R.id.home_audio_play);
        Glide.with(getContext()).load(model == 1? R.drawable.home_audio_playing : R.drawable.home_audio_play).into(menuIcon);
    }

}
