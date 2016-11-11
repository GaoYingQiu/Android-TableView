package com.lvj.bookoneday.activity.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvj.bookoneday.R;
import com.lvj.bookoneday.entity.Book;
import com.lvj.bookoneday.entity.FoundList;
import com.lvj.bookoneday.entity.MenuNav;
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
public class FoundBookListCell extends RecyclerViewCell<FoundList> implements BGAOnRVItemClickListener {

    private RecyclerView bookRecyclerView;
    private BookGalleryDelegate bookGalleryDelegate;

    public FoundBookListCell(int layout, Context context, BookGalleryDelegate bookGalleryDelegate) {
        super(layout, context);
        this.bookGalleryDelegate = bookGalleryDelegate;
    }

    @Override
    protected void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, FoundList model) {

        viewHolderHelper.setVisibility(R.id.find_section_header, (position > 1 ? View.VISIBLE : View.GONE));
        viewHolderHelper.setText(R.id.find_section_titleLabel, model.getTitle());
        bookRecyclerView = (RecyclerView) viewHolderHelper.getView(R.id.find_booklist_recycle_view);
        //设置管理布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bookRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        BookGalleryAdapter bookGalleryAdapter = new BookGalleryAdapter(bookRecyclerView);
        bookGalleryAdapter.setOnRVItemClickListener(this); //item点击事件
        bookRecyclerView.setAdapter(bookGalleryAdapter);
        bookGalleryAdapter.setDatas(model.getBookList()); //bookGalleryAdapter设置数据源
    }


    //bookItem　点击事件
    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if (bookGalleryDelegate != null) {
            bookGalleryDelegate.onBookGalleryDidSelectItem(position);
        }
    }


    //bookRecyclerView适配器
    public class BookGalleryAdapter extends BGARecyclerViewAdapter<Book> {

        public BookGalleryAdapter(RecyclerView recyclerView){
            super(recyclerView,0);
        }

        @Override
        protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, Book model) {

            ImageView bookThumb = viewHolderHelper.getView(R.id.find_book_thumb);
            Glide.with(getContext()).load(R.drawable.test_banner).into(bookThumb);
            viewHolderHelper.setText(R.id.find_book_title, model.getTitle());
        }

        @Override
        public BGARecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BGARecyclerViewHolder viewHolder = new BGARecyclerViewHolder(mRecyclerView,
                    LayoutInflater.from(mContext).inflate(R.layout.find_item_book, parent, false),
                    mOnRVItemClickListener, mOnRVItemLongClickListener);

            return viewHolder;
        }
    }
}
