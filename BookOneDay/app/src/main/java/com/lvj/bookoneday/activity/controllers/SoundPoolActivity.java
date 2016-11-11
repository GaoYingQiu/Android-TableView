package com.lvj.bookoneday.activity.controllers;

import java.util.TooManyListenersException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lvj.bookoneday.R;

public class SoundPoolActivity extends Activity implements SoundPool.OnLoadCompleteListener{
	private static final int SOUNDPOOL_LOAD_PRIORITY = 1;
	private SoundPool soundpool = null;
	private AudioManager audioManager = null;
	
	private int sid_background,sid_roar,sid_chimp,sid_rooster,sid_bark;
	
	
	private TextView tv = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soundpool_activity);
		tv =(TextView)findViewById(R.id.soundpool_text);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		     /* 【1】创建我们的SoundPool对象：第一个参数是SoundPool同时共同播放多上个音乐，
		     注意是最大的同时播放数，而不是由多少音频资源。第二参数是音频类型，一般为STREAM_MUSIC；
		     第三个参数目前没有使用，reference建议为0 。*/
		soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		  /* 【3.pre】将资源load()到SoundPool后，才可以进行播放，因此，需要完成load的回调函数。*/
		soundpool.setOnLoadCompleteListener(this);
		
		audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		    /* 【2】SoundPool load入音频资源，音频流会从文件中解码，放入内存，
		    这提高了SoundPool的性能。我们给出了用资源和fd方式。对于SD卡，
		    可以用soundpool.load("/mnt/sdcard/abc.mp3",1）; load()参数的最后一位是PRIORITY，
		    目前没有真正使用，reference建议设置为1，以便兼容未来版本。*/
//		sid_background = soundpool.load(this, R.raw.crickets, 1);
		sid_roar = soundpool.load(this, R.raw.roar, SOUNDPOOL_LOAD_PRIORITY);
//		sid_rooster = soundpool.load(this, R.raw.rooster, SOUNDPOOL_LOAD_PRIORITY);
//		sid_chimp = soundpool.load(this, R.raw.chimp, SOUNDPOOL_LOAD_PRIORITY);
//		sid_bark = soundpool.load(this, R.raw.test, SOUNDPOOL_LOAD_PRIORITY);
//		try{
//			AssetFileDescriptor afd = getAssets().openFd("dogbark.mp3");
//			sid_bark = soundpool.load(afd.getFileDescriptor(), 0, afd.getLength(), SOUNDPOOL_LOAD_PRIORITY);
//			afd.close();
//		}catch(Exception e){
//			debug("ERROR:" + e.toString());
//			e.printStackTrace();
//		}
		
		debug("sid_background = " + sid_background);
		debug("sid_roar = " + sid_roar);
		debug("sid_rooster = " + sid_rooster);
		debug("sid_chimp = " + sid_chimp);
		debug("sid_bark = " + sid_bark);	
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		soundpool.release();
		soundpool = null;
		super.onPause();
	}
	
	public void doClick(View v){
		debug("Toggle button is checked ? " + ((ToggleButton)v).isChecked());
		if(((ToggleButton)v).isChecked()){
			soundpool.autoResume();
		}else{
			soundpool.autoPause();
		}
	}

	@Override //load完后，进行播放，
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		// TODO Auto-generated method stub
		debug("sid " + sampleId + " status=" + status);
		
		if(status != 0)
			return;

		// 获取当前音量，音量范围：0-1.0
		final float currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
				/ (float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		// 如果是背景声音，则不断循环播放，如果是其他，则已某个间隔时间播放
        /* play() 第一个参数是音频的id；第二和第三个分别是左右音量；第四个参数是音频流的优先级别，0是最低的基本；第5参数标识是否循环播放，0标识不循环，-1表示循环；第6个参数是播放速率，1.0标识按正常速率播放，有效范围是0.5-2.0。如果成功返回音频流的Id，失败返回0  */
		if(sampleId == sid_background){

			if(soundPool.play(sid_background, currentVolume, currentVolume, 1, -1, 1.0f) == 0){
				debug("Failed to start sound " + sampleId);
			}			
		}else if(sampleId == sid_chimp){
			queueSound(sampleId,5000,currentVolume);
		}else if(sampleId == sid_rooster){
			queueSound(sampleId, 17000, currentVolume);
		}else if(sampleId == sid_roar){
			queueSound(sampleId, 12000, currentVolume);
		}else if(sampleId == sid_bark){
			queueSound(sampleId, 7000, currentVolume);
		}
		
	}

	//利用handler实现固定时间间隔的播放，不同的音频流的间隔时间均不同，我们可以听到1~5个音频的混音效果。
	private void queueSound(final int sid , final long delay , final float volume){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				debug("sid " + sid + " queueSound() run in "  + Thread.currentThread().getName());
				if(soundpool == null)
					return;
				if(((ToggleButton)findViewById(R.id.soundpool_button)).isChecked()){
					if(soundpool.play(sid, volume, volume, 1, 0, 1.0f) == 0){
						debug("Fail to start sound " + sid);
					}
				}

				queueSound(sid, delay, volume);
			}
		}, delay);
	}

	private void debug(String info){
		Log.d("WEI",info);
		tv.setText(info + "\n" + tv.getText());
	}
	
}
