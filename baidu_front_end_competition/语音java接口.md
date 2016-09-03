##js接口：
1.start
2.stop
3.cancel

##webhost中提供的接口:
js.makeToast(String text);

js.clearCacheFolder(File dir, long numDays);

##java回调
1.onBeginningOfSpeech:开始说话
  speech-begin 
  ""
2.onReadyForSpeech:准备就绪
  ready 
  ""
3.onRmsChanged 音量变化处理
  rms-change
  (float)rmsdB
4.onBurrerReceived 录音数据传出处理

5.onEndOfSpeech 说话结束
  speech-end 
  ""
6.onError 错误信息
  error
  (int)
7.onResult 返回结果
  result
  (String)item
  
  results
  (String json)json_res

  error
  ??error.getMessage();
8.onPartiaResults 返回部分结果
  partial-result
  (String)(array toString)
9.onEvent 处理事件回调

##数据格式
识别出来的：
{
    "content": {
        "json_res": "{\"parsed_text\":\"给 张 三 打 电话\",\"raw_text\":\"给张三打电话\",\"results\":[]}\n",
        "item": [
            "给张三打电话"
        ]
    },

    "result": {
}
官方给的语法：
##语音识别得到的结果为raw_text，需要进一步语义解析。
  {
    "raw_text": "打电话给张三",
    "parsed_text": "打电话给张三",
    "results": [
        {
            "domain": "telephone",
            "intent": "call",
            "object": {
                "name": "张三"
            }
        }
    ]
}


