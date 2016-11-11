package com.lvj.bookoneday.activity.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvj.bookoneday.R;
import com.lvj.bookoneday.entity.Book;
import com.lvj.bookoneday.entity.FoundList;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/10,12:43
 */
public class BookShelfCell extends RecyclerViewCell<Book> {


    public BookShelfCell(int layout, Context context) {
        super(layout, context);
    }

    @Override
    protected void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, Book model) {

        ImageView bookThumb = viewHolderHelper.getView(R.id.bookshelf_book_thumb);
        Glide.with(getContext()).load(R.drawable.test_banner).into(bookThumb);
        viewHolderHelper.setText(R.id.bookshelf_book_title, model.getTitle());
    }
}
