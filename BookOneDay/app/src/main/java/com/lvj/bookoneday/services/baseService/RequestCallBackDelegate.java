package com.lvj.bookoneday.services.baseService;

/**
 * Created by Administrator on 2015/11/2.
 * 请求回调类
 */
public interface RequestCallBackDelegate {

    void onStatusOk(BaseResponse response, Class<?> type);
    void onStatusError(BaseResponse response);
    void onRequestError(int errorCode, byte[] errorResponse, Throwable throwable);
}
