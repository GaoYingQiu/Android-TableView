package com.lvj.bookoneday.widget.view.swipeview.cardstack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.lvj.bookoneday.widget.view.swipeview.animation.RelativeLayoutParamsEvaluator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;


import static com.lvj.bookoneday.widget.view.swipeview.cardstack.CardUtils.*;

public class CardAnimator{
    private static final String DEBUG_TAG = "CardAnimator";
    private static final int REMOTE_DISTANCE = 1000;
    private int mBackgroundColor;
    public ArrayList<View> mCardCollection;
    private float mRotation;
    private HashMap<View,LayoutParams> mLayoutsMap;
    private LayoutParams[] mRemoteLayouts = new LayoutParams[4];
    private LayoutParams baseLayout;
    private int mStackMargin=20;

    public CardAnimator(ArrayList<View> viewCollection, int backgroundColor){
        mCardCollection = viewCollection;
        mBackgroundColor = backgroundColor;
        setup();

    }
    private void setup(){
        mLayoutsMap = new HashMap<View,LayoutParams>();

        for(View v : mCardCollection){
            //setup basic layout
            LayoutParams params = (LayoutParams) v.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.width = LayoutParams.MATCH_PARENT;
            params.height = LayoutParams.MATCH_PARENT;

            if(mBackgroundColor != -1) {
                v.setBackgroundColor(mBackgroundColor);
            }

            v.setLayoutParams(params);
        }

        baseLayout = (LayoutParams)mCardCollection.get(0).getLayoutParams();
        baseLayout = cloneParams(baseLayout);

        initLayout();

        for (View v : mCardCollection){
            LayoutParams params = (LayoutParams) v.getLayoutParams();
            LayoutParams paramsCopy =  cloneParams(params);
            mLayoutsMap.put(v, paramsCopy);
        }

        setupRemotes();

    }

    public void initLayout(){
        int size = mCardCollection.size();
        for(View v : mCardCollection){
            int index =  mCardCollection.indexOf(v);
            if(index!=0){
                index-=1;
            }
            LayoutParams params = cloneParams(baseLayout);
            v.setLayoutParams(params);

            scale(v, -(size - index - 1) * 5);
            move(v, index * mStackMargin, 0);
            v.setRotation(0);
        }
    }

    private void setupRemotes(){
        View topView = getTopView();
        mRemoteLayouts[0] = getMoveParams(topView, REMOTE_DISTANCE, -REMOTE_DISTANCE);
        mRemoteLayouts[1] = getMoveParams(topView, REMOTE_DISTANCE, REMOTE_DISTANCE);
        mRemoteLayouts[2] = getMoveParams(topView, -REMOTE_DISTANCE, -REMOTE_DISTANCE);
        mRemoteLayouts[3] = getMoveParams(topView, -REMOTE_DISTANCE, REMOTE_DISTANCE);

    }

    private View getTopView(){
        return mCardCollection.get(mCardCollection.size()-1);
    }

    private void moveToBack(View child)
    {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    private void reorder(){
        View temp = getTopView();
        //RelativeLayout.LayoutParams tempLp = mLayoutsMap.get(mCardCollection.get(0));
        //mLayoutsMap.put(temp,tempLp);
        moveToBack(temp);

        for(int i=(mCardCollection.size()-1); i>0; i--){
            //View next = mCardCollection.get(i);
            //RelativeLayout.LayoutParams lp = mLayoutsMap.get(next);
            //mLayoutsMap.remove(next);
            View current = mCardCollection.get(i-1);
            //current replace next
            mCardCollection.set(i,current);
            //mLayoutsMap.put(current,lp);

        }
        mCardCollection.set(0,temp);

        temp = getTopView();

    }

    public void discard(int direction,final AnimatorListener al){
        AnimatorSet as = new AnimatorSet();
        ArrayList<Animator> aCollection = new ArrayList<Animator>();


        final View topView = getTopView();
        LayoutParams topParams = (LayoutParams) topView.getLayoutParams();
        LayoutParams layout = cloneParams(topParams);
        ValueAnimator discardAnim = ValueAnimator.ofObject(new RelativeLayoutParamsEvaluator(),layout, mRemoteLayouts[direction]);

        discardAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator value) {
                topView.setLayoutParams((LayoutParams)value.getAnimatedValue());
            }
        });

        discardAnim.setDuration(250);
        aCollection.add(discardAnim);

        for(int i = 0; i< mCardCollection.size();i++){
            final View v = mCardCollection.get(i);

            if(v==topView) continue;
            final View nv = mCardCollection.get(i+1);
            LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
            LayoutParams endLayout = cloneParams(layoutParams);
            ValueAnimator layoutAnim = ValueAnimator.ofObject(new RelativeLayoutParamsEvaluator(),endLayout,mLayoutsMap.get(nv));
            layoutAnim.setDuration(250);
            layoutAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator value) {
                    v.setLayoutParams((LayoutParams)value.getAnimatedValue());
                }
            });
            aCollection.add(layoutAnim);
        }

        as.addListener(new AnimatorListenerAdapter(){


            @Override
            public void onAnimationEnd(Animator animation) {
                reorder();
                if(al != null){
                    al.onAnimationEnd(animation);
                }
                mLayoutsMap = new HashMap<View,LayoutParams>();
                for (View v : mCardCollection){
                    LayoutParams params = (LayoutParams) v.getLayoutParams();
                    LayoutParams paramsCopy =  cloneParams(params);
                    mLayoutsMap.put(v, paramsCopy);
                }

            }

        });


        as.playTogether(aCollection);
        as.start();


    }

    public void reverse(MotionEvent e1, MotionEvent e2){
        final View topView =  getTopView();
        ValueAnimator rotationAnim = ValueAnimator.ofFloat(mRotation, 0f);
        rotationAnim.setDuration(250);
        rotationAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator v) {
                topView.setRotation(((Float) (v.getAnimatedValue())).floatValue());
            }
        });

        rotationAnim.start();

        for(final View v : mCardCollection){
            LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
            LayoutParams endLayout = cloneParams(layoutParams);
            ValueAnimator layoutAnim = ValueAnimator.ofObject(new RelativeLayoutParamsEvaluator(),endLayout,mLayoutsMap.get(v));
            layoutAnim.setDuration(250);
            layoutAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator value) {
                    v.setLayoutParams((LayoutParams)value.getAnimatedValue());
                }
            });
            layoutAnim.start();
        }

    }

    public void drag(MotionEvent e1, MotionEvent e2, float distanceX,
                     float distanceY){

        View topView =  getTopView();

        float rotation_coefficient = 20f;

        LayoutParams layoutParams = (LayoutParams) topView.getLayoutParams();
        LayoutParams topViewLayouts = mLayoutsMap.get(topView);
        int x_diff = (int)((e2.getRawX()-e1.getRawX()));
        int y_diff = (int)((e2.getRawY()-e1.getRawY()));

        layoutParams.leftMargin  = topViewLayouts.leftMargin+ x_diff;
        layoutParams.rightMargin = topViewLayouts.rightMargin - x_diff;
        layoutParams.topMargin  = topViewLayouts.topMargin + y_diff;
        layoutParams.bottomMargin  = topViewLayouts.bottomMargin - y_diff;

        mRotation = (x_diff/rotation_coefficient);
        topView.setRotation(mRotation);
        topView.setLayoutParams(layoutParams);

        //animate secondary views.
        for(View v : mCardCollection){
            int index  = mCardCollection.indexOf(v);
            if(v!=getTopView() && index != 0){
                LayoutParams l = scaleFrom(v, mLayoutsMap.get(v), (int) (Math.abs(x_diff) * 0.05));
                moveFrom(v, l, 0, (int) (Math.abs(x_diff) * 0.1));
            }
        }
    }

    public void setStackMargin(int margin) {
        mStackMargin = margin;
        initLayout();
    }



}
