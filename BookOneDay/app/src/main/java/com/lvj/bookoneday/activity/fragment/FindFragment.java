package com.lvj.bookoneday.activity.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.activity.controllers.TabMainActivity;
import com.lvj.bookoneday.activity.view.BookGalleryDelegate;
import com.lvj.bookoneday.activity.view.FoundBannerCell;
import com.lvj.bookoneday.activity.view.FoundBookListCell;
import com.lvj.bookoneday.entity.Book;
import com.lvj.bookoneday.entity.FoundList;
import com.lvj.bookoneday.entity.MenuNav;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;
import com.lvj.bookoneday.widget.view.table.refresh.adapter.RefreshControlType;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends BaseListFragment<FoundList> implements BookGalleryDelegate {

	private List<FoundList> sectionBookList = new ArrayList<FoundList>();

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		initData();
		super.onCreateViewLazy(savedInstanceState);
		getTableView().getTableViewAdapter().setDatas(sectionBookList);
	}


	//初始化列表
	private void initData() {
		//banner
		ArrayList<Book> bannerList = new ArrayList<Book>();
		bannerList.add(new Book(1,"http://图片路径"));
		bannerList.add(new Book(2,"http://图片路径"));
		bannerList.add(new Book(3,"http://图片路径"));
		bannerList.add(new Book(4,"http://图片路径"));
		sectionBookList.add(new FoundList("发现 Banner", bannerList));

		//bookList
		ArrayList<Book> bookList1 = new ArrayList<Book>();
		bookList1.add(new Book(1,"http://图片路径","罗马人的故事",22.1f));
		bookList1.add(new Book(2,"http://图片路径","想恋爱,先变坏",0.99f));
		bookList1.add(new Book(3,"http://图片路径","融资时,拿谁的钱最有利",9.99f));
		bookList1.add(new Book(4,"http://图片路径","巨富",9.99f));
		bookList1.add(new Book(5,"http://图片路径","Google 改变时代的技术",0.99f));
		bookList1.add(new Book(6,"http://图片路径","想恋爱,先变坏",0.99f));
		bookList1.add(new Book(7,"http://图片路径","融资时,拿谁的钱最有利",9.99f));
		bookList1.add(new Book(8,"http://图片路径","巨富",9.99f));
		bookList1.add(new Book(9,"http://图片路径","Google 改变时代的技术",0.99f));
		sectionBookList.add(new FoundList("有料音频", bookList1));

		ArrayList<Book> bookList2 = new ArrayList<Book>();
		bookList2.add(new Book(1,"http://图片路径","乔布斯传",22.1f));
		bookList2.add(new Book(2,"http://图片路径","Google 改变时代的技术",0.99f));
		bookList2.add(new Book(3,"http://图片路径","融资时,拿谁的钱最有利",9.99f));
		bookList2.add(new Book(4,"http://图片路径","巨富",9.99f));
		bookList2.add(new Book(5,"http://图片路径","融资时,拿谁的钱最有利",9.99f));
		bookList2.add(new Book(6,"http://图片路径","巨富",9.99f));
		bookList2.add(new Book(7,"http://图片路径","Google 改变时代的技术",0.99f));
		bookList2.add(new Book(8,"http://图片路径","融资时,拿谁的钱最有利",9.99f));
		bookList2.add(new Book(9,"http://图片路径","巨富",9.99f));
		bookList2.add(new Book(10,"http://图片路径","融资时,拿谁的钱最有利",9.99f));
		bookList2.add(new Book(11,"http://图片路径","巨富",9.99f));
		sectionBookList.add(new FoundList("干货图书", bookList2));
	}

	private View getPageView(@DrawableRes int resid) {
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageResource(resid);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		return imageView;
	}

	protected RefreshControlType getRefreshControlType(){
		return RefreshControlType.None;
	}

	@Override
	public int getItemViewType(int position) {
		int resultItemViewType = 1;
		if(position == 0) {
			resultItemViewType = 0;
		}
		return resultItemViewType;
	}

	@Override
	public RecyclerViewCell<FoundList>[] getRecyclerCells() {
		return new RecyclerViewCell[]{
				new FoundBannerCell(R.layout.find_cell_scroll_banner,getContext(),0),
				new FoundBookListCell(R.layout.find_cell_books,getContext(),this)
		};
	}

	@Override
	public void onBookGalleryDidSelectItem(int itemIndex) {

		Log.d("test","点击的bookItem是："+itemIndex);
	}

	@Override
	protected void onFragmentStartLazy() {

		TabMainActivity tabMainActivity = (TabMainActivity ) getActivity();
		tabMainActivity.setProjectTitleText("发现");
		tabMainActivity.changeNavToolBarVisibility(1);
		onEnd();
		super.onFragmentStartLazy();
	}
}
