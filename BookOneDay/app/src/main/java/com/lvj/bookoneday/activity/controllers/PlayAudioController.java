package com.lvj.bookoneday.activity.controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lvj.bookoneday.R;
import com.lvj.bookoneday.activity.baseController.Controller;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.SweetSheet;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.BlurEffect;

import java.util.ArrayList;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2016/1/27,16:22
 */
public class PlayAudioController extends Controller {

    private RelativeLayout rl;
    private SweetSheet mSweetSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_activity_play);

        rl = (RelativeLayout) findViewById(R.id.audio_activity_play_relative_layout);
        setupRecyclerView();

        rl.findViewById(R.id.audio_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mSweetSheet.toggle();
            }
        });

        rl.findViewById(R.id.music_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupRecyclerView() {

        ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.test_ic_account_child;
        menuEntity1.title = "code";
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.iconId = R.drawable.test_ic_account_child;
        menuEntity.title = "我是列表，点点看";
        list.add(menuEntity1);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(rl);

        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {

                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                Toast.makeText(PlayAudioController.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

}
