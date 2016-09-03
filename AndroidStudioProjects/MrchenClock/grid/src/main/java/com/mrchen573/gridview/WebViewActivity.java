package com.mrchen573.gridview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by CHEN on 2016/8/12.
 */
public class WebViewActivity extends Activity implements View.OnClickListener{
  private WebView web_view;
  private ProgressDialog pro_dialog;
  private Button back_btn;
  private Button reload_btn;
  private TextView title_text;
  final private String HOMEURL="http://www.mrchen573.com/voicePlatform";
  private VoiceJavaScript voice;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.webview);
    /*
     * 1.初始化webView --通用设置
     * 2.初始化title --设置头部显示和按钮
     * 3.java js 混合调用问题
     *  3.1 js调用java
     *    3.1.1.创建java被调用的类，记住要加 @JavascriptInterface
     *    3.1.2.创建JavascriptInterface
     *    3.1.3.移除调用 web_view.removeJavascriptInterface("js");
     *  3.2 java调用js
     *      java调用可以使用loadUrl方式，用url去执行js代码
     * 4.实例化一个百度语音类，里面实现了js和java两个相关方法。
     *   提供了三个js接口：start stop cancel 用于调用java，实现录音和取得结果
     *   提供了多个java方法：用于执行语音识别和反馈结果。 那么，Android端还是需要配置语音识别的程序，
     *   那么怎么实现语音识别的呢？
     *   过程：现在安卓端应该是打好了吧？
     *   合并文件》权限等设置》引入文件要一并打包
     * 5.两种调用方式：
     *    一种必须要放到主进程中才能调用。
     *   ok! Android端终于打通了！！！现在开始回到主线--前端，js
     * ps: 为方便调试，将缓存关闭了 8.13
     */
    initWebView();
    initTitle();

    voice= new VoiceJavaScript(this,web_view);

    web_view.addJavascriptInterface(new WebHost(this),"js");

    /*web_view.setWebViewClient(new WebViewClient(){
      @Override
      public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:hello('chenfan');");
      }
    });*/

  }

  private void initTitle() {
    /*
     * 1..title
     *      刷新按钮：reload（）方法
     *      返回：goBack（）方法
     *      标题：setWebViewClient中有个onReceiveTitle方法
     * 2.添加后退功能
     * 3.添加刷新功能
     */
    back_btn= (Button) findViewById(R.id.back_btn);
    reload_btn= (Button) findViewById(R.id.reload_btn);
    title_text= (TextView) findViewById(R.id.title_text);

    web_view.setWebChromeClient(new WebChromeClient(){
      //获取标题
      @Override
      public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        title_text.setText(title);
      }
    });
    back_btn.setOnClickListener(this);
    reload_btn.setOnClickListener(this);
  }

  private void initWebView(){
    /*
     * 1.配置好权限等
     * 2.实例化webView
     * 3.使用loadUrl（）方法打开页面
     * 4.用户体验之---setWebViewClient
     *   4.1 不使用其他浏览器打开 setWebViewClient中有个shouldOverrideUrlLoading方法
     *   4.2 没有网络时显示本地页面 setWebViewClient中有个onReceiveError方法
     * 5.用户体验之--启用js和缓存模式 web_settings
     * 6.用户体验之-：setWebChromeClient
     *    6.1进度提醒 onProgressChanged
     *    6.2
     */
    web_view= (WebView)findViewById(R.id.web_view);
    web_view.loadUrl(HOMEURL);
    //不用其他浏览器打开
    web_view.setWebViewClient(new WebViewClient(){
      //当打开错误时打开准备好的错误页面
      @Override
      public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        view.loadUrl(HOMEURL);
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
    //启用js并且启用缓存 web_settings
    WebSettings web_settings=web_view.getSettings();
    web_settings.setJavaScriptEnabled(true);
    //web_settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    web_settings.setCacheMode(web_settings.LOAD_NO_CACHE);
    //进度显示 setWebChromeClient
    web_view.setWebChromeClient(new WebChromeClient(){
      //进度显示
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(newProgress==100){
          //加载完毕
          closeDiolag();
        }else{
          //加载中
          openDiolag(newProgress);
        }
      }
      private void closeDiolag() {
        if(pro_dialog!=null&&pro_dialog.isShowing()){
          pro_dialog.dismiss();
          pro_dialog=null;
        }
      }
      private void openDiolag(int newProgress){
        if(pro_dialog==null){
          pro_dialog=new ProgressDialog(WebViewActivity.this);
          pro_dialog.setTitle("Please Waiting...");
          pro_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          pro_dialog.setProgress(newProgress);
          pro_dialog.show();
        }else{
          pro_dialog.setProgress(newProgress);
        }
      }
    });
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode==KeyEvent.KEYCODE_BACK){
      if(web_view.canGoBack()){
        web_view.goBack();
        return true;
      }else{
        Toast.makeText(this,"您已经回到首页了！^_^",Toast.LENGTH_LONG).show();
        return true;
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.back_btn:{
        if(web_view.canGoBack()){
          web_view.goBack();
        }else{
          Toast.makeText(WebViewActivity.this,"当前没有历史记录了哦~~",Toast.LENGTH_LONG).show();
        }
        break;
      }
      case R.id.reload_btn:{
        web_view.reload();
        break;
      }
    }
  }
}
