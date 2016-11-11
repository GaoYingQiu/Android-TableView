package com.lvj.bookoneday.activity.view;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.lvj.bookoneday.config.AppConstant;

import java.io.IOException;

/**
 * 音乐播放service
 *
 * @Author: qiugaoying
 * @createTime 2016/1/31,10:19
 */
public class QiuMediaService extends Service implements Runnable ,MediaPlayer.OnCompletionListener{

    /* 定于一个多媒体对象 */
    public  MediaPlayer mMediaPlayer = null;
    // 是否单曲循环
    private static boolean isLoop = false;
    // 用户操作
    private int MSG;
    private static int playbackPosition = 0;
    private Thread thread;

    /* 定义要播放的文件夹路径 */
    private static final String MUSIC_PATH = new String("/sdcard/");

    @Override
    public IBinder onBind(Intent intent) {
        return null;// 这里的绑定没的用，需要将activity与service绑定
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        /* 监听播放是否完成 */
        mMediaPlayer.setOnCompletionListener(this);
        /* 播放错误 */
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(QiuMediaService.this, "播放错误", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) {
            playbackPosition = 0;
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        System.out.println("service onDestroy");
        super.onDestroy();
    }

    /*启动service时执行的方法*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*得到从startService传来的动作，后是默认参数，这里是我自定义的常量*/
        MSG = intent.getIntExtra("MSG", AppConstant.PlayerMag.PLAY_MAG);
        if (MSG == AppConstant.PlayerMag.PLAY_MAG) {
            if (playbackPosition > 0) {
                System.out.print("++++++++++++++++++++++++++++++++++++++++++++++++++++继续播放："+playbackPosition);
                mMediaPlayer.seekTo(playbackPosition); //从指定位置开始播放
                playbackPosition = 0;  //重置播放暂停时候的进度
                mMediaPlayer.start();
            }else {

                System.out.print("++++++++++++++++++++++++++++++++++++++++++++++++++++新歌播放："+playbackPosition);
                playMusic();
            }
        }
        if (MSG == AppConstant.PlayerMag.PAUSE) {
            if (mMediaPlayer.isPlaying()) {
                playbackPosition = mMediaPlayer.getCurrentPosition();
                mMediaPlayer.pause();// 暂停
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @SuppressWarnings("static-access")
    public void playMusic() {
        try {
            /* 重置多媒体 */
            mMediaPlayer.reset();
            /* 读取mp3文件 */
            //mMediaPlayer.setDataSource(MUSIC_PATH+QiuMediaPlayerManage.mMusicList.get(QiuMediaPlayerManage.currentListItme));
            mMediaPlayer.setDataSource(QiuMediaPlayerManage.mMusicList.get(QiuMediaPlayerManage.currentListItme));

//   Uri uri = Uri.parse(MUSIC_PATH+TestMediaPlayer.mMusicList.get(TestMediaPlayer.currentListItme));
//          mMediaPlayer.create(PlayerService.this,uri);

            /* 准备播放 */
            mMediaPlayer.prepare();
            /* 开始播放 */
            mMediaPlayer.start();
            /* 是否单曲循环 */
            mMediaPlayer.setLooping(isLoop);

            new Thread(this).start();

        } catch (IOException e) {

        }

    }

    // 刷新进度条
    @Override
    public void run() {
        int currentPosition = 0;// 设置默认进度条当前位置
        int total = mMediaPlayer.getDuration();
        Boolean audioPlaying = false;
        while (mMediaPlayer != null && currentPosition < total) {
            try {
                Thread.sleep(2000);
                if(mMediaPlayer != null) {
                    currentPosition = mMediaPlayer.getCurrentPosition();
                    audioPlaying = mMediaPlayer.isPlaying();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message msg = new Message();
            msg.what = 0;
            Bundle data = new Bundle();
            data.putInt("currentPosition", currentPosition);
            data.putInt("duration", total);
            data.putBoolean("audioPlaying",audioPlaying);
            msg.setData(data);
            QiuMediaPlayerManage.handler.sendMessage(msg);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        QiuMediaPlayerManage.stopMusic();
        /* 播放完当前歌曲，自动播放下一首 */
        QiuMediaPlayerManage.nextMusic();
    }
}
