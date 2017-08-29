package com.thinkive.bank.qrcodedemo2.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.Result;
import com.thinkive.bank.qrcodedemo2.R;
import com.thinkive.bank.qrcodedemo2.qrcode.BarCodeCreat;
import com.thinkive.bank.qrcodedemo2.qrcode.QRCodeCreat;
import com.thinkive.bank.qrcodedemo2.qrcode.QRCodeParse;
import com.thinkive.bank.qrcodedemo2.util.BitmapUtils;
import com.thinkive.bank.qrcodedemo2.util.PermissionUtils;
import com.thinkive.bank.qrcodedemo2.util.ResourcesUtils;
import com.thinkive.bank.qrcodedemo2.util.ToastUtils;
import com.zbar.lib.CaptureActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SWEEP_QR_CODE = 1;

    private Button mBtnScanner;
    private Button mBtnQRCreat;
    private ImageView mIvQRCode;

    private String filePath;
    private Context mContext;
    private Button mBtnBarCreat;
    private ImageView mIvBarCode;

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    //跳转至二维码扫描界面
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, SWEEP_QR_CODE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initEvent();
    }

    private void initView() {
        mBtnScanner = ((Button) findViewById(R.id.btn_scanner));
        mBtnQRCreat = ((Button) findViewById(R.id.btn_qr_creat));
        mIvQRCode = ((ImageView) findViewById(R.id.iv_qr_code));
        mBtnBarCreat = ((Button) findViewById(R.id.btn_bar_creat));
        mIvBarCode = ((ImageView) findViewById(R.id.iv_bar_code));
    }

    private void initEvent() {
        mBtnScanner.setOnClickListener(this);
        mBtnQRCreat.setOnClickListener(this);
        mBtnBarCreat.setOnClickListener(this);
        mIvQRCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Result result = QRCodeParse.parseQRcodeBitmap(filePath);
                QRCodeParse.handResult(result, mContext);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scanner:   //扫描二维码
                PermissionUtils.requestMultiPermissions(this, mPermissionGrant, false);
                break;
            case R.id.btn_qr_creat:  //生成二维码
                creatQRCode();
                break;
            case R.id.btn_bar_creat:  //生成条形码
                creatBarCode();
                break;
        }
    }

    /**
     * 生成条形码
     */
    private void creatBarCode() {
        Bitmap bitmap = BarCodeCreat.creatBarcode(this, "78480000b4a5", 800, 300, true);
        mIvBarCode.setImageBitmap(bitmap);
        mIvBarCode.setVisibility(View.VISIBLE);
    }

    /**
     * 生成二维码
     */
    private void creatQRCode() {
        String content = "http://www.baidu.com";
        Bitmap logoBitmap = ResourcesUtils.resourceToBitmap(this, R.mipmap.ic_launcher);
        filePath = getExternalCacheDir().getPath() + File.separator + "code.jpeg";
        QRCodeCreat.createLogoQRImage(content, 600, 600, logoBitmap, filePath);
        Bitmap bitmap = BitmapUtils.loadBitmap(filePath);
        mIvQRCode.setImageBitmap(bitmap);
        mIvQRCode.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SWEEP_QR_CODE) {
            String resUrl = data.getStringExtra("userName");
            if (TextUtils.isEmpty(resUrl)) {
                ToastUtils.showToast(mContext, "二维码不能识别");
            } else {
                if (resUrl.startsWith("http")) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("linkUrl", resUrl);
                    intent.putExtra("title_name", "扫描结果");
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(mContext, "识别结果：" + resUrl);

                }
            }
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant, false);

    }
}
