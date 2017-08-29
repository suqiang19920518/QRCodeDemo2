package com.thinkive.bank.qrcodedemo2.qrcode;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.thinkive.bank.qrcodedemo2.util.ToastUtils;
import com.thinkive.bank.qrcodedemo2.activity.WebViewActivity;

import java.util.Hashtable;

/**
 * @author: sq
 * @date: 2017/8/22
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: 二维码识别
 */
public class QRCodeParse {

    /**
     * 解析二维码图片,返回结果封装在Result对象中
     *
     * @param bitmapPath
     * @return
     */
    public static Result parseQRcodeBitmap(String bitmapPath) {
        //解析转换类型UTF-8
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
        options.inJustDecodeBounds = true;
        //此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素
        //以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性
        options.inSampleSize = options.outHeight / 400;
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1; //防止其值小于或等于0
        }
        /**
         * 辅助节约内存设置
         *
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
         * options.inPurgeable = true;
         * options.inInputShareable = true;
         */
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        //开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 处理识别结果
     *
     * @param result
     * @param context
     */
    public static void handResult(Result result, Context context) {
        try {
            String resUrl = result.getText();
            if (TextUtils.isEmpty(resUrl)) {
                ToastUtils.showToast(context, "二维码不能识别");
            } else {
                if (resUrl.startsWith("http")) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("linkUrl", resUrl);
                    intent.putExtra("title_name", "扫描结果");
                    context.startActivity(intent);
                } else {
                    ToastUtils.showToast(context, "识别结果：" + resUrl);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
