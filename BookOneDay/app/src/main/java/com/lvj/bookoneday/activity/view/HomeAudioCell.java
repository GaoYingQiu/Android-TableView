package com.lvj.bookoneday.activity.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvj.bookoneday.R;
import com.lvj.bookoneday.activity.fragment.HomeFragment;
import com.lvj.bookoneday.entity.Audio;
import com.lvj.bookoneday.entity.MenuNav;
import com.lvj.bookoneday.widget.view.AppUtil;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/10,12:43
 */
public class HomeAudioCell extends RecyclerViewCell<Audio> {

    private int viewTag;

    public HomeAudioCell(int layout, Context context, int viewTag) {
        super(layout, context);
        this.viewTag = viewTag;
    }

    @Override
    protected void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, Audio model) {

        viewHolderHelper.setText(R.id.home_audio_title, model.getTitle());
        viewHolderHelper.setText(R.id.home_audio_time, model.getTime());
        TextView audioTitleView = viewHolderHelper.getView(R.id.home_audio_title);
        audioTitleView.setTextColor(getContext().getResources().getColor(R.color.lj_color_black1));

        viewHolderHelper.setVisibility(R.id.progressTotalLine, View.GONE);
        viewHolderHelper.setVisibility(R.id.home_cell_audio_playing, View.INVISIBLE); //Gone不占用空间，INVISIBLE 占用空间
        if(position == QiuMediaPlayerManage.currentListItme + 1 ) {
            audioTitleView.setTextColor(getContext().getResources().getColor(R.color.lj_color_orange));
            viewHolderHelper.setVisibility(R.id.progressTotalLine, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.home_cell_audio_playing, View.VISIBLE);
            TextView progressView = viewHolderHelper.getView(R.id.progressLine);
            ViewGroup.LayoutParams params = progressView.getLayoutParams();
            params.width = (int) (AppUtil.getScreenDispaly(getContext())[0] * model.getCurrentPlayProgress() / 100);
            progressView.setLayoutParams(params);
        }
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.home_message_icon);
    }
}
