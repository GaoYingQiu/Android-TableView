package com.lvj.bookoneday.entity;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/10,13:21
 */

public class MenuNav {

    private int icon; //icon
    private String title; //标题
    private String desc; //描述内容
    private int viewTypeCellPosition; //cell 的position

    public MenuNav(){

    }
    public MenuNav(int icon,String title,String desc, int viewTypeCellPosition){
        this.title = title;
        this.icon = icon;
        this.desc = desc;
        this.viewTypeCellPosition = viewTypeCellPosition;
    }


    //get,set方法
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getViewTypeCellPosition() {
        return viewTypeCellPosition;
    }
}
