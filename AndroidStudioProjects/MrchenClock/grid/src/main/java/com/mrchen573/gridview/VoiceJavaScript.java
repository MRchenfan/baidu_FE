package com.mrchen573.gridview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.baidu.speech.VoiceRecognitionService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leon on 16/5/11.
 */
public class VoiceJavaScript implements RecognitionListener {

    private WebView webview;
    private SpeechRecognizer recognizer;
    private Context context;

    final private static String JS_INSTANCE_NAME = "__efe_voice_playground_native_sdk__";

    final private static String JS_CALLBACK_PREFIX = "javascript:efeVoicePlaygroundCallback";

    public VoiceJavaScript(Context context, WebView webview) {

        this.context = context;
        this.webview = webview;

        // 语音识别
        recognizer = SpeechRecognizer.createSpeechRecognizer(
                context,
                new ComponentName(context, VoiceRecognitionService.class)
        );

        recognizer.setRecognitionListener(this);

        webview.addJavascriptInterface(this, JS_INSTANCE_NAME);

    }

    @JavascriptInterface
    public void start() {

        Handler handler = new Handler(context.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {

                recognizer.cancel();

                Intent intent = new Intent();
                intent.putExtra("sample", 16000);
                intent.putExtra("language", "cmn-Hans-CN");
//                intent.putExtra("grammar", 20000); // 输入
//                intent.putExtra("grammar", 10060); // 地图
//                intent.putExtra("grammar", 10001); // 音乐
//                intent.putExtra("grammar", 10003); // 应用
//                intent.putExtra("grammar", 10008); // 电话
//                intent.putExtra("grammar", 100014); // 联系人
//                intent.putExtra("grammar", 100016); // 手机设置
//                intent.putExtra("grammar", 100018); // 电视指令
                intent.putExtra("grammar", 100019); // 播放器指令
//                intent.putExtra("grammar", 100020); // 收音机指令
//                intent.putExtra("grammar", 100021); // 命令词
//                intent.putExtra("grammar","assets:///baidu_speech_grammar.bsg");//导入自定义的语法包
                intent.putExtra("nlu","enable");//开启语义解析

                recognizer.startListening(intent);

            }

        });


    }

    @JavascriptInterface
    public void stop() {

        Handler handler = new Handler(context.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                recognizer.stopListening();
            }

        });

    }

    @JavascriptInterface
    public void cancel() {

        Handler handler = new Handler(context.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                recognizer.cancel();
            }

        });

    }

    @Override
    public void onBeginningOfSpeech() {
        javaScriptCallback("speech-begin", "");
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        javaScriptCallback("ready", "");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        javaScriptCallback("rms-change", Float.toString(rmsdB));
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
        javaScriptCallback("speech-end", "");
    }

    @Override
    public void onError(int error) {
        javaScriptCallback("error", Integer.toString(error));
    }

    @Override
    public void onResults(Bundle results) {

        try {
            String text = results.getString("origin_result");
            JSONObject json = new JSONObject(text);
            JSONObject content = json.getJSONObject("content");

            JSONArray item = content.getJSONArray("item");
            JSONObject json_res= new JSONObject(content.getString("json_res"));

            javaScriptCallback("result",json_res.toString());
            Log.i("tag", json_res.toString());
        }
        catch (Exception error) {
            javaScriptCallback("error", error.getMessage());
        }

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> results = partialResults
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        javaScriptCallback(
                "partial-result",
                Arrays.toString(
                        results.toArray(new String[results.size()])
                )
        );

    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }

    protected void javaScriptCallback(String type, String data) {

        webview.loadUrl(
                JS_CALLBACK_PREFIX + "('" + type + "', '" + data + "')"
        );

    }

}
