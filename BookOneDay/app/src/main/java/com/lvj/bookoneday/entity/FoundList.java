package com.lvj.bookoneday.entity;

import java.util.ArrayList;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2016/1/23,14:20
 */
public class FoundList {
    private String title;
    private ArrayList<Book> bookList;


    public FoundList(String title, ArrayList<Book> bookList) {
        this.title = title;
        this.bookList = bookList;
    }

    //get ,set
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }
}
