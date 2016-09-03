require.config({
  paths:{
    sdk: 'sdk',
    ajax:'ajax',
    view:'view'
  }
});

require(['sdk','ajax','view'],function(V,Ajax,View){
  //APP是一个全局对象*/
  APP = {
    version:'1.0.0',
    author:'Chen',
    date:'2016-9',
    domains:['weather','calendar','translation'],// 目前已开通的服务
    view:View,
    ajax:Ajax
  };

  APP.controller = (function(){
    function getPosition(){
      return '合肥';
    }
    function initPage(){
      Mobilebone.pushStateEnabled = false; //禁用浏览器历史记录
      //Mobilebone.submit(document.querySelector("form")); //使用Mobilebone的ajax提交
      View.init({});
    }
    function initVoice(){
      if(!V.isSupported()){
      console.log('voice sdk is not supported !');
        //return false;
      }
    }
    function initAjax(){
      // on 事件等
      Ajax.init({
        position: getPosition()
      });

      V.on('result',function(data){
        /*var data = {
          "raw_text":"成王败寇的泰语翻译",
          "parsed_text":"成王败寇 的 泰语翻译",
          "results":[
          {
            "domain":"translation",
            "intent":"translate",
            "score":0.555556,
            "object":{
              "transbody":"不到长城非好汉",
              "source":"1",
              "target":"6"
            }
          }
          ]
        };*/
        // 触发ajax的事件，并取得api数据
        // 如果是天气，则触发天气相关的事件，并将语音参数传入
        var domain = data.results[0].domain;
        js.makeToast(domain);

        if(APP.domains.indexOf(domain) != -1){
          Ajax.fire(domain,data);
        }else{
          View.renderPoping('未开通',JSON.stringify(data));
        }
      });

      V.on('error',function(error){});
    }

    function start(){
      initPage();
      initVoice();
      initAjax();
    }

    return {
      start:start
    }
  })();

  APP.controller.start();
});
