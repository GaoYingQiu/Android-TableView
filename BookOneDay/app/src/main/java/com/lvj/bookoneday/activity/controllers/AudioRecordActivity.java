package com.lvj.bookoneday.activity.controllers;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lvj.bookoneday.R;

//MediaRecorder将录音写到文件中，但有时我们需要对录音进行处理后在写，
// 或者我们并不需要些文件，只是需要对这些数据进行处理，例如在VoIP中，
// 数据转换为RTP/RTCP流，传输对远端。这些情况可以采用AudioRecord，
// 录音数据写在AudioRecord的一个内部存储中，我们从这个内置存贮中读取数据。

//AudioRecord 录音
public class AudioRecordActivity extends Activity{
	private TextView tv = null;
	private int mAudioBufferSize;
	private int mAudioBufferSampleSize ;
	private AudioRecord record = null;
	private boolean inRecordMode = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple);
		tv = (TextView)findViewById(R.id.simple_text);
		initAudioRecord();
	}
	//http://open.sina.com.cn/course/id_1096
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		debug("onResume() is called");
		inRecordMode = true;
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() { //【2】在后台开始进行录音的采样
				// TODO Auto-generated method stub
				getSamples();
			}
		});
		thread.start();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		inRecordMode = false;  //【3】停止采样
		super.onPause();		
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(record != null){
			record.release();//【4】释放录音所需资源
			debug("Released AudioRecord");
		}
		super.onDestroy();
	}

	private AudioRecord.OnRecordPositionUpdateListener listener =
			new AudioRecord.OnRecordPositionUpdateListener() {
				
				@Override
				public void onPeriodicNotification(AudioRecord recorder) {
					debug("onPeriodicNotification count=" + count);
				}
				
				@Override
				public void onMarkerReached(AudioRecord recorder) {
					debug("onMarkerReached,stop record... count=" + count);
					inRecordMode = false;
				}
			};


	//【步骤1】初始化AudioRecord对象
	private void initAudioRecord(){
		try{			
			int sampleRate = 8000;
			int channelConfig = AudioFormat.CHANNEL_IN_MONO;
			int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
			
			mAudioBufferSampleSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
			mAudioBufferSize = 2 * mAudioBufferSampleSize;
			
				/* 获取AudioRecord需要选择音频来源、采用频率，channel（mono、stereo，left，right），audio编码格式，和内置buffer大小
			 * 我们可以计算最小所需的buffer大小，然后设置更大容量，作为buffer缓存，以确保所有录音均被处理
			 * 如果参数不支持，例如采样频率不支持，会得到IllegalArgumentException异常。*/
			record = new AudioRecord(MediaRecorder.AudioSource.MIC,
					sampleRate, channelConfig, audioFormat, mAudioBufferSize);			
			debug("Setup AudioRecord OK. buffsize = " + mAudioBufferSize);			


			/* 【1.1】这里是frames，8000Hz的采用频率，10000个frames，所需的时间为1.25秒，
			 * 我们还可以计算一下，samplesize长度为640，即可以有640个frame，读16次buffer，为640*16/8000=1.28秒
			 * 故大约都16次buffer
			 * 【注意】这时基于帧的，和我们设置的采样buffer没有任何关系
			 */
			record.setNotificationMarkerPosition(10000);//markerInFrames
			record.setPositionNotificationPeriod(1000);//in frames
			record.setRecordPositionUpdateListener(listener);
			
		    /* 【1.2】查看AudioRecord对象的状态是否正常  */
			int audioRecordState = record.getState();
			if(audioRecordState != AudioRecord.STATE_INITIALIZED){
				debug("ERROR: not initialized state=" + audioRecordState);
				finish();
			}else{ 
				debug("AudioRecord is initialized!");
			}			
			
		}catch(IllegalArgumentException  e){
			debug("[ERROR]" +e.toString());
			e.printStackTrace();
		}

	}
	
	private int count = 0;
	private void getSamples(){
		if(record == null)
			return;
		//【步骤2.2】通过read()，不断地从AudioRecord的内置录音数据buffer中读出数据
		//我们采用16bits，因此在因为read()可以选择short[]而非byte[]，这样一个元素就是一个frame。
		//由于我们采用short方式，而我们注意到在record创建的时候，参数是int bufferSizeInBytes，所以长度需要减半
		short[] audioBuffer = new short[mAudioBufferSize/2];

		//【步骤2.1】开始进行采用，并坚持采样的状态是否正确。
		record.startRecording();

		int state = record.getRecordingState();
		if(state != AudioRecord.RECORDSTATE_RECORDING){
			debug("AudioRecord is not recording... state=" + state);
			finish();
			return;
		}
		
		debug("AudioRecord is recording...");
		while(inRecordMode){
				/* 我们不能直接使用AudioRecord的内置buffer，需要将内容读出来。读的方式是block，因为在后台线程，所有不会有影响 。
			 * 这个read从某种程度有些类似流的读取，当读满mAudioBufferSampleSize的时候，就完成一次read()操作
			 */

			int samplesRead = record.read(audioBuffer,0,audioBuffer.length);
			count ++;
			debug("" + count + " Got samples: " + samplesRead);
			debug("Frist few sample values: " +
					audioBuffer[0] + "," +
					audioBuffer[1] + "," +
					audioBuffer[2] + "," +
					audioBuffer[3] + "," +
					audioBuffer[4] + "," +
					audioBuffer[5] + "," +
					audioBuffer[6] + "," +
					audioBuffer[7] 	); 
		}
		record.stop();
		debug("AudioRecord has stoped recording....");
		
	}

	private void debug(final String info){
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Log.i("WEI",info);
				tv.setText(info + "\n" + tv.getText());				
			}
		});

	}
}
