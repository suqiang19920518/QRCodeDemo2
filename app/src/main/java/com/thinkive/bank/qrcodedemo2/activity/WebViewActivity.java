package com.thinkive.bank.qrcodedemo2.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thinkive.bank.qrcodedemo2.R;

/**
 * @author: sq
 * @date: 2017/8/21
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: 网页加载
 */
public class WebViewActivity extends BaseActivity {

    private TextView mBackTv;//返回
    private TextView mCloseTv;
    private WebView mWebView;
    private ProgressBar pb;
    private TextView tv_title;

    private String mUrl;
    private String sessionKey;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void findViews() {
        mBackTv = (TextView) findViewById(R.id.tv_back);
        mCloseTv = (TextView) findViewById(R.id.tv_close);
        tv_title = (TextView) findViewById(R.id.tv_title);
        mWebView = (WebView) findViewById(R.id.webview);
        pb = (ProgressBar) findViewById(R.id.pb);
    }

    @Override
    protected void initObjects() {
//        sessionKey = MemoryCachUtils.getObjectData(Constant.SESSION_KEY) == null ? "" : (String) MemoryCachUtils.getObjectData(Constant.SESSION_KEY);
    }

    @Override
    protected void initViews() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置是否可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏缩放工具
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (mUrl.equals(url)) {
                    return false;
                } else {
                    view.loadUrl(url);
                    return true;
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mUrl = getIntent().getStringExtra("linkUrl");
        String title = getIntent().getStringExtra("title_name");
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
//        if(mUrl.contains("?")){
//            mUrl = mUrl + "&code="+sessionKey;
//        } else {
//            mUrl = mUrl + "?code="+sessionKey;
//        }
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void setListeners() {
        //返回
        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        mCloseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View view) {

    }
}

