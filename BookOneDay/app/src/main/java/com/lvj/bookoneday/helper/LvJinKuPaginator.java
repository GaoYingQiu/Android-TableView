package com.lvj.bookoneday.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvjinku on 15/11/9.
 */
public class LvJinKuPaginator {

    private static  int ljkDefaultPageSize = 10;

    private int currentPage;
    private int pageSize;
    private int totalPages;
    private List results = new ArrayList();
    private PaginatorDelegate delegate ;



    public  LvJinKuPaginator(){

    }
    public  LvJinKuPaginator(int pageSize ,PaginatorDelegate delegate){
        this.totalPages = 1;
        this.currentPage = 0;
        this.delegate = delegate;
        this.pageSize = pageSize;
    }


    //是否到达末尾页
    public boolean reachedLastPage()
    {
        return this.currentPage >= totalPages;
    }

    //重置
    private void reset(){
        this.totalPages = 1;
        this.currentPage = 0;
        if(results == null) {
            results = new ArrayList();
        }else{
            results.clear();
        }
    }

    //加载第一页
    public void fetchFirstPage(){
        reset();
        fetchResutlsWithPage(1);
    }

    private void fetchResutlsWithPage(int page){
        delegate.fetchResutlsWithPage(page);
    }

    public void receivedResults(List results,int totalPage){
        this.results.addAll(results);
        this.currentPage ++;
        this.totalPages = totalPage;

        if(!reachedLastPage())
        {
             delegate.showLoadMoreView(true);  //加载更多
        }else{
             delegate.showLoadMoreView(false); //没有更多了
        }
    }

    //get,set
    public int getCurrentPage() {
        return currentPage;
    }

    public List getResults() {
        return results;
    }
}

