package com.lvj.bookoneday.activity.view;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

import com.lvj.bookoneday.config.AppConstant;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2016/1/31,10:46
 */
public class QiuMediaPlayerManage {

    private static AudioManager audioMrg = null;
    private static QiuMediaPlayerManage instance = null;
    private static Context context = null;
    private static AudioManager.OnAudioFocusChangeListener audioListener = null;
    public static Handler handler = null;

    /* 定义在播放列表中的当前选择项 */
    public static int currentListItme = 0;
    /* 定于一个数据播放列表，用来存放从指定文件中搜索到的文件 */
    public static List<String> mMusicList = new ArrayList<String>();

    public void initMusicList(ArrayList<String> paths){
        mMusicList.clear();
        mMusicList.addAll(paths);
    }

    public static QiuMediaPlayerManage getInstance(AudioManager audioManager,Context targetContext,Handler targetHandler) {
        if (instance == null) {
            synchronized (QiuMediaPlayerManage.class) {
                if (instance == null) {
                    instance = new QiuMediaPlayerManage(audioManager,targetContext,targetHandler);
                }
            }
        }
        return instance;
    }

    private QiuMediaPlayerManage(AudioManager audioManager,Context targetContext,Handler targetHandler) {

        audioMrg = audioManager;  //获取AudioManager Foucus
        context = targetContext;  //启动service
        handler = targetHandler;  //handler 子线程通知主线程

        //来电等进行干预
        audioListener = new AudioManager.OnAudioFocusChangeListener() {

            @Override
            public void onAudioFocusChange(int focusChange) {
                // TODO Auto-generated method stub
//                if (QiuMediaService.mMediaPlayer == null) {
//                    return;
//                }

                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: //临时事情audio的focuse，例如有来电，进行音乐播放暂停操作
                        playMusic(AppConstant.PlayerMag.PAUSE);
                        break;

                    case AudioManager.AUDIOFOCUS_GAIN:  //获得audio的focuse，对暂停的音乐继续播
                        playMusic(AppConstant.PlayerMag.PAUSE);
                        break;

                    case AudioManager.AUDIOFOCUS_LOSS: //失去焦点，被别的音乐抢占。
                        stopMusic();
                        break;

                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK://例如有短信的通知音，可以调低声音，无需silent
                        //可以通过AudioManager.get调低声音，或简单地不做处理。
                        audioMrg.getStreamVolume(AudioManager.AUDIOFOCUS_GAIN);
                        break;
                    default:
                        break;
                }
            }
        };

        audioMrg.requestAudioFocus(audioListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    //播放和暂停
    public static void playMusic(int action) {
        Intent intent = new Intent();
        intent.putExtra("MSG", action);
        intent.setClass(context, QiuMediaService.class);
        context.startService(intent);
    }

    //是否正在播放
    public static boolean isMediaAudioPlaying(){
//        if(QiuMediaService.mMediaPlayer != null)
//            return  QiuMediaService.mMediaPlayer.isPlaying();
        return false;
    }

    /* 播放上一首歌曲 */
    private void FrontMusic() {
        if (--currentListItme >= 0) {
            playMusic(AppConstant.PlayerMag.PLAY_MAG);

        } else {
            Toast.makeText(context, "没有前一首了", Toast.LENGTH_SHORT)
                    .show();
            currentListItme = 0;
        }
    }

    /* 播放下一首 */
    public static void nextMusic() {
        if (++currentListItme >= mMusicList.size()) {
            Toast.makeText(context, "没有下一首了", Toast.LENGTH_SHORT)
                    .show();
            currentListItme = mMusicList.size() - 1;
        } else {
            playMusic(AppConstant.PlayerMag.PLAY_MAG);
        }
    }

    //停止播放音乐
    public static void stopMusic() {
        audioMrg.abandonAudioFocus(audioListener);
        Intent intent = new Intent(context,QiuMediaService.class);
        context.stopService(intent);//停止Service
    }
}
