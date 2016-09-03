define(['view'],function(View){

  var Ajax = {
    listeners:{},
    position:'北京',
    version:'v1.1 cf 2016.9',
    on:function(type,handler){
      var handlers = this.listeners[type];

      if (!handlers) {
        handlers = this.listeners[type] = [];
      }

      handlers.push(handler);

      return this;
    },
    fire:function(type,data){
      var handlers = this.listeners[type];

      if(!handlers || !handlers.length){
        return ;
      }

      for(var i=0;i<handlers.length;i++){
        handlers[i](data);
      }

      return this;
    }
  };

  //监听语音结果，并很据结果调用不同领域的解析
  function weather(data){
    var start_end = data.results[0].object.date.split(','); //[]
    var days_len = parseInt(start_end[1].substring(8)) - parseInt(start_end[0].substring(8))
    var days = [];
    var arr = start_end[0].split('-');

    for(var i=0; i<days_len + 1; i++){

      var newd = new Date(new Date(arr[0].toString(),arr[1].toString(),arr[2].toString()).getTime() + 
        1000*3600*24*i);

      var year = '' + newd.getFullYear();
      var month = (parseInt(newd.getMonth()/10)==0)?'0' + newd.getMonth():'' + newd.getMonth();
      var date = (parseInt(newd.getDate()/10)==0)?'0' + newd.getDate():'' + newd.getDate();

      days.push(year + '-' + month + '-' + date);
    }

    var focus = data.results[0].object.focus;
    var position = Ajax.position;

    // 百度api
    var url = 'http://apis.baidu.com/apistore/weatherservice/recentweathers';
    $.ajax({
      type:'GET',
      url:url,
      headers:{
        apikey:'b030cd3802cbcbf2453268a380639cdf'
      },
      data:{
        cityname:position
      },
      success:function(resource){
        //结果提取
        resource = JSON.parse(resource);

        var find_days = [];
        days.forEach(function(item,index){

          if(item == resource.retData.today.date){
            find_days.push(resource.retData.today);
          }

          resource.retData.forecast.forEach(function(obj,index){
            if(item == obj.date){
              find_days.push(obj);
            }
          });
          
          resource.retData.history.forEach(function(obj,index){
            if(item == obj.date){
              find_days.push(obj);
            }
          });
        }); 

        var result = '';
        find_days.forEach(function(day_data,index){
          result += '<p>' +
          day_data.date + ' ' + 
          day_data.week + ' ' +  
          day_data.type + '<br>' +
                  //'现在气温' + day_data.curTemp + '<br>' +
                  '今日最高气温' + day_data.hightemp + '<br>' +
                  '今日最低气温' + day_data.lowtemp + '<br>' +
                  '风向：' + day_data.fengxiang + '<br>' +
                  '风力：' + day_data.fengli + '<br>' +
                  '</p>';
                });

        View.renderPoping(data.raw_text,result);
      },
      error: function(xhr, type){
        alert('Ajax error!')
      }
    });
  }
  function calendar(data){
    var result = '<p>' + data.results[0].object.ANSWER + '</p>';
    View.renderPoping(data.raw_text,result);
  }
  function translation(data){
    var transbody = data.results[0].object.transbody;
    var url = 'http://fanyi.youdao.com/openapi.do?keyfrom=voicePlatform&key=1019782203&type=data&doctype=jsonp&callback=translationShow&version=1.1';

    $.ajax({
      type:'GET',
      url:url,
      data:{
        q:transbody
      },
      dataType:'jsonp',
      jsonp:'translationShow'
    });
  }
  /*Ajax.translationShow = function(res){

    var str = '<p>' + res.translation[0] + '</p>';

    if(res.basic){
      str += '<p>发音：英（' + res.basic['uk-phonetic'] + 
      '） 美（' + res.basic['us-phonetic'] + '）<br></p>' +
      '<p>基本释义：<br>' + res.basic.explains.join('<br>') +
      '</p> ';
    }

    if(res.web){

      str += '<p>网络释义:<br>';

      res.web.forEach(function(item,index){

        str += item.key + '<br>' + item.value.join('  ') + '<br>';

      });

      str += '</p>';
    }

    APP.modelAjax.renderUI(res.query, str);
  }*/
  Ajax.init = function(config){
    this.position = config.position;
    // 绑定天气事件，触发时传入语音参数
    Ajax.on('weather',weather);
    Ajax.on('calendar',calendar);
    Ajax.on('translation',translation);
  }
  
  return Ajax;
});

/*
* 翻译那里ajax指定context就报错，只能出此下策了
*/
function translationShow(res){

  var str = '<p>' + res.translation[0] + '</p>';

  if(res.basic){
    str += '<p>发音：英（' + res.basic['uk-phonetic'] + 
    '） 美（' + res.basic['us-phonetic'] + '）<br></p>' +
    '<p>基本释义：<br>' + res.basic.explains.join('<br>') +
    '</p> ';
  }

  if(res.web){

    str += '<p>网络释义:<br>';

    res.web.forEach(function(item,index){

      str += item.key + '<br>' + item.value.join('  ') + '<br>';

    });

    str += '</p>';
  }
  
  APP.view.renderPoping(res.query, str);
}