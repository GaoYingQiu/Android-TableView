package com.lvj.bookoneday.activity.controllers;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.lvj.bookoneday.R;

//MediaRecorder 录音 然后写到文件中
public class MediaRecordAudioActivity extends Activity{
	private String OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/myrecord001.mp3";
			//"/mnt/sdcard1/Music/myrecord001.3gpp";
	private MediaRecorder recorder = null;
	private MediaPlayer player = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_record_activity);
	}
	
	public void doClick(View v){
		try{
			switch(v.getId()){
			case R.id.begin_button:
				v.setEnabled(false);
				findViewById(R.id.stop_button).setEnabled(true);
				beginRecording();
				break;
			case R.id.stop_button:
				v.setEnabled(false);
				findViewById(R.id.begin_button).setEnabled(true);
				stopRecording();
				break;
			case R.id.play_button:
				playRecording();
				break;
			case R.id.stopPlay_button:
				stopPlayingRecording();
				break;
			default:
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void beginRecording() throws Exception{
		killMediaRecorder();

		//删除旧文件，如果存在
		File outFile = new File(OUTPUT_FILE);
		if(outFile.exists()){
			outFile.delete();
		}

		//步骤[【1】获取MediaRecorder对象，【2】设置录音参数，【3】prepare()，【4】开始录音start()
		recorder = new MediaRecorder();
		   /* 音频来源，最常见的就是MIC。
     * 可以对phone call进行录音，使用VOICE_CALL，VOICE_UPLINK（本端）或VOICE_DOWNLINK远端。
     * 在Android 2.1增加两个音频源：
     * - CAMCORDER 将使用camera相关的麦克风，如果没有则使用设备的主麦克风；
     * - VOICE_RECOGNITION 将用于语音识别，即音频流尽可能raw，保持原样，即不对音频进行修改，例如某些HTC手机对麦克风有AGC（自动增益控制）， 但这会对语音识别是有影响的，采用VOICE_RECOGNITION则音频进行额外的处理。 */
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //�ļ���ʽ
	    /* 设置音频编码，在Android2.3.3之前，只能使用AMR_NB，而之后，可以使用AMR_WB和AAC。
* 注意：setAudioEncoder()应在setOutputFormat()之后，否者会出现setAudioEncoder called in an invalid state(2)的错误 */
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(OUTPUT_FILE);  //输出文件
		//recorder.setMaxDuration(30*1000);//设定30秒录音时间，如果到底上限，自动停止
		//recorder.setMaxFileSize(1000000); //设定1M的文件大小，如果到底上限，自动停止
		recorder.prepare();
		recorder.start();
		
	}
	
	private void stopRecording() throws Exception{
		if(recorder != null)
			recorder.stop();
	}

	private void killMediaRecorder(){
		if(recorder != null){
			try{
				recorder.release();
				recorder = null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void playRecording() throws Exception{
		killMediaPlayer();
		player = new MediaPlayer();
		player.setDataSource(OUTPUT_FILE);
		findViewById(R.id.play_button).setEnabled(false);
		player.setOnCompletionListener(new OnCompletionListener() {			
			@Override
			public void onCompletion(MediaPlayer mp) {
				findViewById(R.id.play_button).setEnabled(true);
				
			}
		});
		player.prepare();
		player.start();
	}
	
	private void stopPlayingRecording() throws Exception{
		if(player != null){
			player.stop();//停止录音
		}
		findViewById(R.id.play_button).setEnabled(true);
	}

	private void killMediaPlayer(){
		try{
			if(player != null){
				player.release();//释放录音资源
				player = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		killMediaPlayer();
		killMediaRecorder();
		
		super.onDestroy();
	}
	
	
}
