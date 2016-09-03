package com.example.chen.voiceplatform;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*方法一：主布局中添加webView的组件*/

        //查找webView
        WebView webView=(WebView) findViewById(R.id.webView);
        //启用js
        webView.getSettings().setJavaScriptEnabled(true);
        //首页
        webView.loadUrl("http://www.mrchen573.com/voicePlatform");

        //启用js入口，web中可以调用java api
        webView.addJavascriptInterface(new WebJavaScriptInterface(this),"efe");

        //调用web的js，java中可以调用web中的api
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view,String url){
                view.loadUrl("javascript:hello('chenfan');");
            }
        });

        //设置自身打开，而不是默认浏览器打开
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url); // 在当前的webview中跳转到新的url
                return true;
            }
        });
    }

    //js入口
    private class WebJavaScriptInterface{
        Context context;
        WebJavaScriptInterface(Context context){
            this.context=context;
        }
        @JavascriptInterface
        public void showToast(String toast){
            Toast.makeText(context,toast, Toast.LENGTH_LONG).show();
        }
    }
}
