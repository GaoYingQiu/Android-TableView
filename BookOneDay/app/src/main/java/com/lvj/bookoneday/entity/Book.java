package com.lvj.bookoneday.entity;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2016/1/23,14:22
 */
public class Book {

    private int bookId; //书id
    private String url; //banner图片
    private String thumb; //缩略图
    private String title; //标题
    private float readCoin;//阅读币
    private int shelfStatu; //书架状态
    private String desc; //描述
    private String briefIntroduce; //图书简介
    private float audioSize; //音频大小
    private String quotes;  //金句
    private String audioTime; //音频时长
    private int likeCount; //点赞数
    private int recommendArticleContext; //推荐内容.首页今天值得看


    public Book(){
    }

    //Constontar
    public Book(int bookId, String url) {
        this.bookId = bookId;
        this.url = url;
    }

    public Book(int bookId, String url,String title,float readCoin) {
        this.bookId = bookId;
        this.url = url;
        this.title = title;
        this.readCoin = readCoin;
    }

    public Book(int bookId,String title,String desc,String audioTime,float readCoin) {
        this.bookId = bookId;
        this.title = title;
        this.desc = desc;
        this.audioTime = audioTime;
        this.readCoin = readCoin;
    }

//get,set

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getRecommendArticleContext() {
        return recommendArticleContext;
    }

    public void setRecommendArticleContext(int recommendArticleContext) {
        this.recommendArticleContext = recommendArticleContext;
    }

    public String getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(String audioTime) {
        this.audioTime = audioTime;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getReadCoin() {
        return readCoin;
    }

    public void setReadCoin(float readCoin) {
        this.readCoin = readCoin;
    }

    public int getShelfStatu() {
        return shelfStatu;
    }

    public void setShelfStatu(int shelfStatu) {
        this.shelfStatu = shelfStatu;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBriefIntroduce() {
        return briefIntroduce;
    }

    public void setBriefIntroduce(String briefIntroduce) {
        this.briefIntroduce = briefIntroduce;
    }

    public float getAudioSize() {
        return audioSize;
    }

    public void setAudioSize(float audioSize) {
        this.audioSize = audioSize;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }
}
