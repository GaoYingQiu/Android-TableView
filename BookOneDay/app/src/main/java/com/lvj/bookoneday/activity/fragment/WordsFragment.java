package com.lvj.bookoneday.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.activity.controllers.TabMainActivity;
import com.lvj.bookoneday.widget.view.swipeview.cardstack.CardStack;
import com.shizhefei.fragment.LazyFragment;

public class WordsFragment extends LazyFragment {

	private CardStack wordsCardStack;
	private WordCardsDataAdapter cardsDataAdapter;
	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.words_cards);

		wordsCardStack = (CardStack)findViewById(R.id.cards_container);
		wordsCardStack.setContentResource(R.layout.words_card_item);
		wordsCardStack.setStackMargin(20);

		cardsDataAdapter = new WordCardsDataAdapter(getApplicationContext(),0);
		cardsDataAdapter.add("你猜猜这是什么效果");
		cardsDataAdapter.add("哈哈哈可以哦，真棒！");
		cardsDataAdapter.add("今天天气好冷呀，我靠！牛逼的天。");
		cardsDataAdapter.add("快要回家过年了，可惜的是没钱，呜呜~~~~~~~~");
		cardsDataAdapter.add("还有几个界面还没做完哟，看来又得加班了高颖。");
		wordsCardStack.setAdapter(cardsDataAdapter);
		wordsCardStack.setListener(new CardStack.CardEventListener() {
			@Override
			public boolean swipeEnd(int section, float distance) {

				//if "return true" the dismiss animation will be triggered
				//if false, the card will move back to stack
				//distance is finger swipe distance in dp

				//the direction indicate swipe direction
				//there are four directions
				//  0  |  1
				// ----------
				//  2  |  3
				return (distance>300)? true : false;
			}

			@Override
			public boolean swipeStart(int section, float distance) {
				return true;
			}

			@Override
			public boolean swipeContinue(int section, float distanceX, float distanceY) {
				return true;
			}

			@Override
			public void discarded(int mIndex, int direction) {
				//this callback invoked when dismiss animation is finished.
			}

			@Override
			public void topCardTapped() {
				//this callback invoked when a top card is tapped by user.
			}
		});
	}


	@Override
	protected void onFragmentStartLazy() {

		TabMainActivity tabMainActivity = (TabMainActivity ) getActivity();
		tabMainActivity.setProjectTitleText("金句");
		tabMainActivity.changeNavToolBarVisibility(1);
		super.onFragmentStartLazy(); //fragment显示
	}

	//卡片适配器
	public class WordCardsDataAdapter extends ArrayAdapter<String> {

		public WordCardsDataAdapter(Context context, int resource) {
			super(context, resource);
		}

		@Override
		public View getView(int position, final View contentView, ViewGroup parent){

			TextView v = (TextView)(contentView.findViewById(R.id.words_content));
			v.setText(getItem(position));
			return contentView;
		}

	}
}
