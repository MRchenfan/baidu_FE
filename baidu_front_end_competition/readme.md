#百度前端技术竞赛
##1.题目：
  使用百度语音api完成一个demo。
  安卓hybrid app开发。

###任务一：
  打通安卓平台,hybrid app开发入门。
  》2016.8.13 上午 完成任务

###任务二：
  学习使用js引导sdk进行语音识别
  /*
  *    1.引入sdk.js后有一个全局变量voice，就是我们操控的对象，包含语音api
  *    2.voice 有四个关于语音识别的方法和两个自定义事件的方法。
  *    3.自定义事件中，添加相关反馈信息的回调函数。
  *    4.现在基本搞定了逻辑关系。
  */

###任务三：
  总结可以做什么：
  1.现在可以进行语音识别了,通过添加自定义事件可以对语音识别结果进行处理。
  2.但是速度有点慢啊？是因为什么原因呢？？
  3.离线包没有打开

###任务四：
  理解自然语言解析过程和结果。至少要会使用。
  语音识别得到了结果，然而没有意图啊？
  
  语音识别之后呢？？
  命令怎么识别出来？
  自定义语法设置？？？
  还需要做解析开发？？？
###任务
  
###问题
1.
  Q：首次开启语音识别功能的延迟时间较长，需要如何调控？
  A： 首次延迟时间较长一般是由于权限验证造成，可以通过预先调用接口：
  (int)verifyApiKey:(NSString )apiKey withSecretKey:(NSString )secretKey;
  来进行验证。首次开启语音时就不需要再发送验证请求，从而降低语音识别启动的延迟。
2.
  Q：【Android离在线】如何从识别SDK中获取音频？
  A：
  方式1：设置outfile参数可以指定语音数据的保存路径，设置方式如：

  intent.putExtra("outfile", "/sdcard/your_audio.pcm");
  语音数据的保存格式为PCM，播放和压缩请自行查找相关类库。

  方式2：通过监听onBufferReceived(byte[] buf)回调，拼接音频实现

##创意目标：
  通过语音识别获取命令，处理后传达给机器人，让机器人执行命令。
  
  
