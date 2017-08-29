package com.thinkive.bank.qrcodedemo2.util;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;

/**
 * @author: sq
 * @date: 2017/8/1
 * @corporation: 深圳市思迪信息科技有限公司
 * @description: 资源文件工具
 */
public class ResourcesUtils {
    public static final String RES_TYPE_LAYOUT = "layout";
    public static final String RES_TYPE_DRAWABLE = "drawable";
    public static final String RES_TYPE_STRING = "string";
    public static final String RES_TYPE_COLOR = "color";
    public static final String RES_TYPE_ID = "id";
    public static final String RES_TYPE_STYLE = "style";

    private ResourcesUtils() {
    }

    /**
     * 获取Resource对象
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取布局文件的资源ID
     */
    public static int getLayoutResId(Context context, String name) {
        return getResourceID(context, RES_TYPE_LAYOUT, name);
    }

    /**
     * 获取图片文件的资源ID
     */
    public static int getDrawableResId(Context context, String name) {
        return getResourceID(context, RES_TYPE_DRAWABLE, name);
    }

    /**
     * 获取字符串的资源ID
     */
    public static int getStringResId(Context context, String name) {
        return getResourceID(context, RES_TYPE_STRING, name);
    }

    /**
     * 获取颜色的资源ID
     */
    public static int getColorResId(Context context, String name) {
        return getResourceID(context, RES_TYPE_COLOR, name);
    }

    /**
     * 获取id的资源ID
     */
    public static int getIdResId(Context context, String name) {
        return getResourceID(context, RES_TYPE_ID, name);
    }

    /**
     * 获取样式的资源ID
     */
    public static int getStyleResId(Context context, String name) {
        return getResourceID(context, RES_TYPE_STYLE, name);
    }

    /**
     * 获取资源ID
     */
    public static int getResourceID(Context context, String type, String name) {
        Resources resource = context.getResources();
        String pkgName = context.getPackageName();
        /**
         * getIdentifier(String name, String defType, String defPackage)
         * 第一个参数:资源的名称,如(ic_launcher,hello等)
         * 第二个参数:资源的类型(drawable,string等)
         * 第三个参数:包名
         */
        return resource.getIdentifier(name, type, pkgName);
    }

    /**
     * 获取string.xml中的字符串
     */
    public static String getString(Context context, int resId) {
        return getResources(context).getString(resId);
    }

    /**
     * 得到string.xml中的字符,带占位符
     */
    public static String getString(Context context, int resId, Object... formatArgs) {
        return getResources(context).getString(resId, formatArgs);
    }

    /**
     * 得到string.xml中的字符数组
     */
    public static String[] getStringArr(Context context, int resId) {
        return getResources(context).getStringArray(resId);
    }

    /**
     * 得到color.xml中的颜色值
     */
    public static int getColor(Context context, int resId) {
        return getResources(context).getColor(resId);
    }

    /**
     * 得到应用程序的包名
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }


    public static Drawable getDrawable(int a, int r, int g, int b) {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0, Color.argb(a, r, g, b)});
        return drawable;
    }

    public static View findViewById(Context context, int resId) {
        return LayoutInflater.from(context).inflate(resId, (ViewGroup) null);
    }

    public static View findViewById(Context context, int resId, ViewGroup root) {
        return LayoutInflater.from(context).inflate(resId, root);
    }

    public static View findViewById(Context context, int resId, ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(context).inflate(resId, root, attachToRoot);
    }

    public static Drawable resourceToDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    public static Bitmap resourceToBitmap(Context context, int resId) {
        Resources r = context.getResources();
        InputStream is = r.openRawResource(resId);
        BitmapDrawable bmpDraw = new BitmapDrawable(is);
        return bmpDraw.getBitmap();
    }
}
