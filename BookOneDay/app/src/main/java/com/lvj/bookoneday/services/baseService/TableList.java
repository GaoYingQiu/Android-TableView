package com.lvj.bookoneday.services.baseService;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2015/11/6.
 */
public class TableList {

    private int count;
    private int page;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List list;

    public TableList(){

    }

    public TableList(JSONObject responseObejct){

        this.count = responseObejct.getIntValue("count");
        this.page = responseObejct.getIntValue("page");
        this.pageSize = responseObejct.getIntValue("pageSize");
        this.totalPage = responseObejct.getIntValue("totalPage");
        this.totalCount = responseObejct.getIntValue("totalCount");
    }

    //get,set
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
