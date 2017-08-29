package com.thinkive.bank.qrcodedemo2.util;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

/**
 * @author: sq
 * @date: 2017/8/4
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: Toast封装类
 */
public class ToastUtils {
    private static Toast mToast;
    private static boolean isSetGravity = false;

    /**
     * 判断是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void showToast(Context context, String msg, int showTime) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, showTime);
        } else if (isSetGravity) {  //还原重心至默认位置
            dismissToast();
            mToast = Toast.makeText(context, msg, showTime);
        } else {
            mToast.setText(msg);
            mToast.setDuration(showTime);
        }
        if (!isApplicationBroughtToBackground(context)) {
            mToast.show();
        }

    }

    public static void showToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else if (isSetGravity) {  //还原重心至默认位置
            dismissToast();
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        if (!isApplicationBroughtToBackground(context)) {
            mToast.show();
        }

    }

    /**
     * 自定义显示位置，进行Toast
     *
     * @param gravity  重心位置 ，如 Gravity.CENTER
     * @param context
     * @param msg
     * @param showTime
     */
    public static void showToast(Context context, int gravity, String msg, int showTime) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, showTime);
        } else {
            mToast.setText(msg);
            mToast.setDuration(showTime);
        }
        mToast.setGravity(gravity, 0, 0);
        isSetGravity = true;
        if (!isApplicationBroughtToBackground(context)) {
            mToast.show();
        }

    }

    /**
     * 自定义显示位置，进行Toast
     *
     * @param gravity 重心位置 ，如 Gravity.CENTER
     * @param context
     * @param msg
     */
    public static void showToast(Context context, int gravity, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setGravity(gravity, 0, 0);
        isSetGravity = true;
        if (!isApplicationBroughtToBackground(context)) {
            mToast.show();
        }
    }

    public static void dismissToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
