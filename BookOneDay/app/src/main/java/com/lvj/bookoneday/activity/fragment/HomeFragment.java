package com.lvj.bookoneday.activity.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.activity.controllers.TabMainActivity;
import com.lvj.bookoneday.activity.view.HomeArticleCell;
import com.lvj.bookoneday.activity.view.HomeAudioCell;
import com.lvj.bookoneday.activity.view.HomeAudioPlayCell;
import com.lvj.bookoneday.activity.view.HomeNextPageCell;
import com.lvj.bookoneday.activity.view.HomeRecommendCell;
import com.lvj.bookoneday.config.AppConstant;
import com.lvj.bookoneday.entity.Audio;
import com.lvj.bookoneday.entity.Book;
import com.lvj.bookoneday.activity.view.QiuMediaPlayerManage;
import com.lvj.bookoneday.widget.view.table.RecyclerViewCell;
import com.lvj.bookoneday.widget.view.table.refresh.adapter.RefreshControlType;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.SweetSheet;
import com.mingle.sweetpick.ViewPagerDelegate;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseListFragment<Object> {

	private List homeList = new ArrayList<Object>();
	private PopupWindow mMorePopupWindow;
	private SweetSheet shareSweetSheet;
	private int mShowMorePopupWindowWidth;
	private int mShowMorePopupWindowHeight;
	private Boolean isAudioPlaying = false;
	private QiuMediaPlayerManage qiuMediaPlayerManage;
	private int currentClickAudioCellIndex = 0; //当前点击的row
	private int lastClickAudioCellIndex = 1; 	//上一次点击的row

	//多线程  传递消息
	private Handler mHandler = new Handler(){

		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					Bundle data = msg.getData();
					int currentPosition = data.getInt("currentPosition");
					int duration = data.getInt("duration");
					isAudioPlaying = data.getBoolean("audioPlaying");
					if (duration > 0) {
						long progress = 100 * currentPosition / duration;
						int audioListIndex  = QiuMediaPlayerManage.currentListItme + 1;
						Audio audio = (Audio)homeList.get(audioListIndex);
						audio.setCurrentPlayProgress((int)progress);
						homeList.set(audioListIndex,audio);
						getTableView().getTableViewAdapter().notifyDataSetChanged(); //notifyItemChanged(currentAudioPlayingIndex);
						System.out.println("---------------current audio progress:---------" + (int) progress);
					}
			}
		};
	};

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		initData();
		super.onCreateViewLazy(savedInstanceState);
		getTableView().getTableViewAdapter().setDatas(homeList); //绑定数据

		//播放测试
		AudioManager audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
		qiuMediaPlayerManage = QiuMediaPlayerManage.getInstance(audioManager,getActivity(),mHandler);
		String basePath = Environment.getExternalStoragePublicDirectory("tencent") + "";
		ArrayList<String> paths = new ArrayList<String>();
		String AUDIO_PATH1 = basePath + "/QQfile_recv/xuwei.mp3";
		String AUDIO_PATH2 = basePath + "/QQfile_recv/opendoor.mp3";
		String AUDIO_PATH3 = basePath + "/QQfile_recv/unforget.mp3";
		String AUDIO_PATH4 = basePath + "/QQfile_recv/thennow.mp3";
		paths.add(AUDIO_PATH1);
		paths.add(AUDIO_PATH2);
		paths.add(AUDIO_PATH3);
		paths.add(AUDIO_PATH4);
		qiuMediaPlayerManage.initMusicList(paths);

		setThirdShareSheet();
	}

	protected RefreshControlType getRefreshControlType(){
		return RefreshControlType.BGAStickiness;
	}
	protected boolean bHasFooterRefresh(){
		return false;
	}

	//初始化列表
	private void initData() {

		//header
		homeList.add(0);

		//语音
		homeList.add(new Audio("如何防止被洗脑？", "02:10"));
		homeList.add(new Audio("如何不花钱把握用户需求？", "01:50"));
		homeList.add(new Audio("为何信用卡会刺激购买欲？", "02:00"));
		homeList.add(new Audio("袜子可以单卖，一年卖出30万只", "02:04"));

		//今天值得花时间看
		homeList.add(new Book());

		//推荐
 		homeList.add(new Book(1,"融资时，拿谁的钱最有利？","对于初创企业而言，到底该拿个人投资还是拿机构投资？", "10:40",9.99f));
 		homeList.add(new Book(2, "《人性中的善良天使》", "创业需要对人性的解读，需要对历史规律的把握，给你推荐这本书。", "30:42", 1.99f));

		//下一页
		homeList.add(null);
	}

		@Override
	public RecyclerViewCell<Object>[] getRecyclerCells() {
		return new RecyclerViewCell[]{
				new HomeAudioPlayCell(R.layout.home_header_play,getContext(),0),
				new HomeAudioCell(R.layout.home_cell_audio,getContext(),0),
				new HomeArticleCell(R.layout.home_cell_article,getContext(),0),
				new HomeRecommendCell(R.layout.home_cell_recommend,getContext(),0),
				new HomeNextPageCell(R.layout.home_next_page,getContext(),0)
		};
	}

	//对应的具体哪个cell 类型。
	@Override
	public int getItemViewType(int position) {

		if(position == 0){
			return 0;
		}else if(position == homeList.size() -1){
			return 4;
		}else{
			if(position > 5){
				return 3;
			}else {
				return position == 5 ? 2 : 1;
			}
		}
	}

	//点击cell 的事件,重写父类方法。
	@Override
	public void onTableViewDidSelectRow(int row) {
		switch (row){
			case 0:{
				controlAudioPlay(0);
				break;
			}
			case 1:
			case 2:
			case 3:
			case 4:{
				 //记录当前播放行
				QiuMediaPlayerManage.currentListItme = row - 1;
				controlAudioPlay(row);
				lastClickAudioCellIndex = row;
				break;
			}
		}
	}

	@Override
	protected void onFragmentStartLazy() {

		TabMainActivity tabMainActivity = (TabMainActivity ) getActivity();
		tabMainActivity.setProjectTitleText("首页");
		tabMainActivity.changeNavToolBarVisibility(1);
		onEnd();
		super.onFragmentStartLazy();
	}

	@Override
	public void onItemChildClick(View childView, int position) {
		switch (childView.getId()){
			case R.id.home_message_icon:{
				slideMoreAboutAudio(childView);
				break;
			}
		}
	}

	private void controlAudioPlay(int clickRow){

		boolean isMediaAudioPlayStatu = isAudioPlaying ; // isServiceRunning(getContext(),"QiuMediaService");
		if (clickRow == lastClickAudioCellIndex || clickRow == 0) {
			changeAudioPlayStatu(!isMediaAudioPlayStatu); //在播放设置0 ，不在播放设置1
		}else{
			//清空进度条状态
			cleanLastPlayAudioProgress();
			//播放另外一首
			QiuMediaPlayerManage.stopMusic();
			qiuMediaPlayerManage.playMusic(AppConstant.PlayerMag.PLAY_MAG);
		}
		getTableView().getTableViewAdapter().notifyDataSetChanged();
	}

	private void cleanLastPlayAudioProgress(){
		for (int i=1; i<= QiuMediaPlayerManage.mMusicList.size(); i++) {
			if (i == QiuMediaPlayerManage.currentListItme + 1 ) {
				Audio audio = (Audio)homeList.get(i);
				audio.setCurrentPlayProgress(0);
				homeList.set(i, audio);
				break;
			}
		}
	}

	private void changeAudioPlayStatu(boolean playingStatu){
		homeList.set(0, playingStatu ? 1:0);
		qiuMediaPlayerManage.playMusic(playingStatu ? AppConstant.PlayerMag.PLAY_MAG : AppConstant.PlayerMag.PAUSE);
	}

	/**
	 * 弹出点赞和评论框
	 * @param moreAboutView
	 */
	private void slideMoreAboutAudio(View moreAboutView) {

		if (mMorePopupWindow == null) {

			LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View content = li.inflate(R.layout.home_item_audio_more, null, false);

			mMorePopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			mMorePopupWindow.setBackgroundDrawable(new BitmapDrawable());
			mMorePopupWindow.setOutsideTouchable(true);
			mMorePopupWindow.setTouchable(true);

			content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
			mShowMorePopupWindowWidth = content.getMeasuredWidth();
			mShowMorePopupWindowHeight = content.getMeasuredHeight();

			View parent = mMorePopupWindow.getContentView();

   			ImageView shareImageView = (ImageView) parent.findViewById(R.id.audio_more_share);
			shareImageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mMorePopupWindow.dismiss();
					shareSweetSheet.show();
				}
			});

		}

		if (mMorePopupWindow.isShowing()) {
			mMorePopupWindow.dismiss();
		} else {
			int heightMoreBtnView = moreAboutView.getHeight();

			mMorePopupWindow.showAsDropDown(moreAboutView, -mShowMorePopupWindowWidth,
					-(mShowMorePopupWindowHeight + heightMoreBtnView) / 2);
		}
	}



	//弹出分享
	private void setThirdShareSheet() {
		shareSweetSheet = new SweetSheet(containerParent);

		//从menu 中设置数据源
		shareSweetSheet.setMenuList(R.menu.sheet_share);
		shareSweetSheet.setDelegate(new ViewPagerDelegate(3,650));
		shareSweetSheet.setBackgroundEffect(new DimEffect(0.5f));
		shareSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
			@Override
			public boolean onItemClick(int position, MenuEntity menuEntity1) {

				Toast.makeText(getContext(), menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
				return true;
			}
		});


	}

	protected void onFragmentStopLazy() {

		shareSweetSheet.dismiss();
		super.onFragmentStopLazy();
	}

	@Override
	public void onDestroy() {
		QiuMediaPlayerManage.stopMusic();
		super.onDestroy();
	}
}
