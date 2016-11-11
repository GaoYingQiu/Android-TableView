package com.lvj.bookoneday.widget.view;

/**
 * create description
 *
 * @author lingyuanhong
 * @creattime 2015/11/16      16:36
 */
import android.content.Context;
import android.view.WindowManager;

public class AppUtil {

    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = { width, height };
        return result;
    }

}

