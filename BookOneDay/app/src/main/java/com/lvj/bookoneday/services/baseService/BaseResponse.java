package com.lvj.bookoneday.services.baseService;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2015/11/2.
 * 基础响应类
 */
public class BaseResponse {

    private JSONObject responseObject;   //返回数据
    private int error;           //请求状态 0,请求成功 1请求错误
    private String errorMsg;     //错误消息
    private Object returnObject; //返回单个对象

    private TableList tableList;

    public BaseResponse(){

    }

    public BaseResponse(String jsonResultString){

        JSONObject result = JSON.parseObject(jsonResultString, JSONObject.class);
        this.error = result.getIntValue("error");
        this.errorMsg = result.getString("errormessage");
        responseObject = (JSONObject)result.get("response");

        Log.d("responseObject",responseObject.toString());

        int totalPage = responseObject.getIntValue("totalPage");
        if(totalPage > 0){
            Log.d("total print","进来了呀呀呀吗，表格！");
            this.tableList = new TableList(responseObject);
        }
    }


    //get,set

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getError() {
        return error;
    }

    public JSONObject getResponseObject() {
        return responseObject;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "responseObject=" + responseObject +
                ", error=" + error +
                ", errorMsg='" + errorMsg + '\'' +
                ", tableList=" + tableList +
                '}';
    }

    //get,set

    public TableList getTableList() {
        return tableList;
    }

    public void setTableList(TableList tableList) {
        this.tableList = tableList;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }
}
