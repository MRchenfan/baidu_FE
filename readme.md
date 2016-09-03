#百度前端技术竞赛

######工作记录

##题目：
  使用百度语音api完成一个demo。
  安卓hybrid app开发。

### 2016.8.7
  打通安卓平台
  hybrid app开发入门。

### 2016.8.10
  学习使用js引导sdk进行语音识别
  /*
  *    1.引入sdk.js后有一个全局变量voice，就是我们操控的对象，包含语音api
  *    2.voice 有四个关于语音识别的方法和两个自定义事件的方法。
  *    3.自定义事件中，添加相关反馈信息的回调函数。
  *    4.现在基本搞定了逻辑关系。
  */

### 2016.8.11
  总结可以做什么：
  1.现在可以进行语音识别了,通过添加自定义事件可以对语音识别结果进行处理。
  2.但是速度有点慢啊？是因为什么原因呢？？
  3.离线包没有打开

### 2016.8.11
  理解自然语言解析过程和结果。至少要会使用。
  语音识别得到了结果，然而没有意图啊？
  
  语音识别之后呢？？
  命令怎么识别出来？
  自定义语法设置？？？
  还需要做解析开发？？？

## 2016.8.13
  获取的格式
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

----
中间去补充js和响应式、webApp知识


## 2016.8.20
  用学过的MVC框架搭建了一个微型框架，便于以后扩展。
  接下来的任务是构思app的功能，使用对象，实现逻辑等等，最后搭建好webApp页面。
  
## 2016.8.23
  使用mobilebone和zepto搭建app框架，实现了tab页面切换。
  但是发现自己搭建的webView不支持手势滑动切换。
  
  zepto 没有define，只能设置全局变量$

## 2016.8.25
  完成天气模块，可以查询前7天和后4天，总共12天的天气情况。
  
## 2016.8.26
  UI优化了一下，顿时好看不少。
  主页有三个应用插件：
  第一页：天气和推荐
    天气使用天气网的插件就行
    每日推荐要做到Ajax动态更新，内容是新闻或者美文，可以用户定制
  第二页：备忘录和日历
    日历组件要加上备忘录功能
  第三页：时钟
    包括时间显示，定时功能，倒计时功能。
## 2016.8.27
  发现touchend事件总是无法触发，才知道是浏览器bug，解决的办法：
  touchstart事件中阻止默认就可以了

## 2016.8.30
  两天没有更新了，前天开学，到学校很多事情。昨天下午弄了份简历，要找工作了。
  一个很重要的问题：如何使用离线应用。目前为之都是在线的，没有加入离线功能。

  任务：备忘录如何实现，需要日历插件，加上备忘录功能。
  
## 2016.9.1
  头疼了，不该乱用require的，现在各种设计模式都搞乱了。
  
## 2016.9.3
  重构完毕，实现了mvc的分离
  Controller: app.js -- APP.controller
  Model: sdk.js ajax.js -- APP.ajax
  View: view.js -- APP.view

## 问题
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

## 创意目标：
  2016.8.15 通过语音识别获取命令，处理后传达给机器人，让机器人执行命令。
  2016.8.18 做一个将语音识别融入生活的App，分为三大板块：
    1. 工作/学习
    2. 生活
    3. 娱乐
  核心：
    通过语音，将平时手机上不怎么用的小应用集成起来。
    但是又不能单纯的语音助手，还要社区化，尽量提供便捷的服务，融入生活。

  目标客户：
    不熟练使用智能手机的人群，语音可以帮助他们从对他们而言比较繁杂的手机操作中解放出来。
    如果体验足够好，会带动手机交互的革命。真正的让语音识别融入生活中。
    
  业务逻辑：
    所有的信息分到三个模块中展示。
    学习工作
    生活
    娱乐

    语音识别后可以去调用第三方Api，实现信息的获取。
  提供的功能：
    在线：
      天气服务
      股票
      。。。
      》可以从百度语音提供的垂类和第三方Api延伸
    离线:
      闹钟
      倒计时
      备忘录

## 技术选型
  后端：
    自建微型MVC框架，使用mysql作为数据库，Smarty作为视图引擎

  前端：
    zepto作为js框架
    mobilebone作为app首页框架
    ajax更新数据
    自己写的组件widget类
    json数据传输格式
    iconfont
    
  安卓端：
    搭建webView，完成语音sdk布置

  业务：
    第三方api：

## 业务逻辑
### 主屏小工具
  1. 时钟
  2. 备忘录
  3. 
  4. 设置

### 语音插件
  语音得到的结果以弹出层的形式展现。
  比如查询天气：弹出一个界面，显示结果。

  
