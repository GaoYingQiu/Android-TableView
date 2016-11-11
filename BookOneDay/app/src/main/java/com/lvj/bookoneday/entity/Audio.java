package com.lvj.bookoneday.entity;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2016/1/23,21:37
 */
public class Audio {

    private int audioId;
    private String title;
    private String audioUrl;
    private String time;
    private String audioType;
    private int currentPlayProgress;

    //test
    public Audio(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public void setCurrentPlayProgress(int currentPlayProgress) {
        this.currentPlayProgress = currentPlayProgress;
    }

    public int getCurrentPlayProgress() {
        return currentPlayProgress;
    }

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAudioType() {
        return audioType;
    }

    public void setAudioType(String audioType) {
        this.audioType = audioType;
    }
}
