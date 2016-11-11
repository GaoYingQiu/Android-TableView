package com.lvj.bookoneday.activity.baseController;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lvj.bookoneday.config.AndroidCommon;
import com.lvj.bookoneday.services.baseService.BaseResponse;
import com.lvj.bookoneday.services.baseService.RequestCallBackDelegate;
import com.lvj.bookoneday.widget.view.SystemBarTintManager;

//customer import

public abstract class Controller extends AppCompatActivity implements RequestCallBackDelegate {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStatusBar();

        //网络状态
        AndroidCommon.sharedInstance().onCreateActivity(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        tintManager.setTintColor(getStatusBarColor());
    }


    protected int getStatusBarColor() {

        int color = AndroidCommon.getAppearence().getStatusBarTintColor();
        return  getResources().getColor(color);
    }

    @Override
    public void onStatusOk(BaseResponse response, Class<?> type) {

    }

    @Override
    public void onStatusError(BaseResponse response) {
        Log.e("ANDROID_COMMON", response.toString());
        Toast toast = Toast.makeText(this, response.getErrorMsg(),
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                                  Throwable throwable) {
        if (errorCode == -2) {
            Log.e("ANDROID_COMMON", "network exception");
            Toast toast = Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (errorCode == -1) {
            Log.e("ANDROID_COMMON", "data_parse_exception");
            Toast toast = Toast.makeText(this, "解析失败", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
