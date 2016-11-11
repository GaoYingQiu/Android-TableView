package com.lvj.bookoneday.services.baseService;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lvj.bookoneday.config.AndroidCommon;
import com.lvj.bookoneday.helper.MD5;
import com.lvj.bookoneday.helper.NetworkDetector;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Administrator on 2015/11/2.
 * 网络请求层。父类
 */
public abstract class BaseDataService {

    private final static String LvJinKuAgency = "root";
    private final static String LvJinKuSecurity = "permission";

    private RequestCallBackDelegate delegate;
    private RequestType requestType;
    private HashMap params = new HashMap();
    private RequestParams requestParamsDic = new RequestParams();

    //执行请求
    public void executeRequest() {

        if (!NetworkDetector.isAvailable(AndroidCommon.sharedInstance().getContext())) {
            onError(-2, null, null);
            return ;
        }
        if (requestType == null) {
            requestType = RequestType.GET;
        }

        //设置请求url的参数
        requestParamsDic.put("xml",acquireBaseRequestParamDictionary());

        switch (requestType) {
            case GET:
                AsyncHttpClientHelper.get(getRequestUrl(), requestParamsDic, responseHandler);
                break;
            case POST:
                AsyncHttpClientHelper.post(getRequestUrl(), requestParamsDic, responseHandler);
                break;

            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    private AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              byte[] errorResponse, Throwable throwable) {
            Log.e("androidAsync","androidAsyncFail:" + statusCode + "  " + throwable.toString());
            onError(statusCode, errorResponse, throwable);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            Log.e("androidAsync","androidAsyncSuccess:"+ statusCode + "  " +response);
            onRequestSuccess(response);
        }
    };

    //请求成功的回调
    private void onRequestSuccess(byte[] response) {

        String data = new String(response);
        if (data.equals("")) {
            onError(-1, null, null);
            return;
        }

        Log.d("请求成功回调response:", data);


        BaseResponse baseResponse = new BaseResponse(data);
        if (baseResponse == null) {
            // 数据解析错误
            Log.e("ANDROID_COMMON", "data_parse_error");
            onError(-1, null, null);
            return;
        }


        //请求状态返回为0表示请求成功
        if (baseResponse.getError() == 0) {
            parseResponse(baseResponse);
            if (delegate!=null) {
                delegate.onStatusOk(baseResponse, this.getClass());
            }
        } else {
            if (delegate!=null) {
                delegate.onStatusError(baseResponse);
            }
        }
    }

    private void onError(int statusCode, byte[] errorResponse,
                         Throwable throwable) {
        Log.e("ANDROID_COMMON", delegate.toString());
        if (delegate == null) {
            return;
        }

        delegate.onRequestError(statusCode, errorResponse, throwable);
    }

    private String acquireBaseRequestParamDictionary(){
        setRequestParams(params); //调用子类获取参数

        Map<String,Object> baseRequestDictionary = new HashMap<String,Object>();
        baseRequestDictionary.put("agency",LvJinKuAgency);
        baseRequestDictionary.put("security", LvJinKuSecurity);
        baseRequestDictionary.put("serviceCode", getAPIRequestMethodName());
        baseRequestDictionary.put("params",params);

        //tokenId
        String paramsJsonStr = JSON.toJSONString(params);
        String tokenIdStr = LvJinKuAgency + LvJinKuSecurity + getAPIRequestMethodName() + paramsJsonStr;
        String tokenId = MD5.getMD5(tokenIdStr.getBytes());

        String baseRequestParamJsonStr = JSON.toJSONString(baseRequestDictionary);
        return baseRequestParamJsonStr;
    }

    protected String getRequestDomain() {
        return "http://www.lvjinku.com/Home/Router/doRoute/";
    }

    protected String getRequestUrl() {
        return getRequestDomain(); //如果接口API请求的方法以链接方式： + requestPath
    }

    //子类覆盖该方法设置参数
    protected void setRequestParams(HashMap params) {

    }
    //子类解析
    protected void parseResponse(BaseResponse response) {

    }
    //子类请求的API接口ServiceCode
    protected String getAPIRequestMethodName() {
        return  "";
    }


    //get,set
    public RequestCallBackDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(RequestCallBackDelegate delegate) {
        this.delegate = delegate;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
