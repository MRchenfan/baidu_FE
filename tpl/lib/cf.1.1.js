
/*
*  date:2016.8.16
*  author:cf
*  note:
*    1. 重写了基本方法，面向对象。
*    2. 使用方法：cf.dom.getNextElements(currentNode);
*    3. 反映了当前我的认知。设计模式还很懵懂。
*    4.
*/
;(function(root,factory){

  if (typeof define === 'function' && define.amd) {
      define([], function () {
          return factory(root);
      });
  }
  else if (typeof exports === 'object' && module.exports) {
      module.exports = factory(root);
  }
  else {
      root.cf = factory(root.cf);
  }

}(this,function(cf){

  //浏览器hack
  if(typeof String.trim=="undefined"){
    String.prototype.trim=function(){
      return this.replace(/(^\s*)|(\S*$)/g,"");
    } 
  };
  if (!Array.prototype.forEach && typeof Array.prototype.forEach !== "function") {
    Array.prototype.forEach = function(callback, context) {
      // 遍历数组,在每一项上调用回调函数，这里使用原生方法验证数组。
      if (Object.prototype.toString.call(this) === "[object Array]") {
        for (var i = 0, len = this.length; i < len; i++) {
            if (typeof callback === "function"  && Object.prototype.hasOwnProperty.call(this, i)) {
                if (callback.call(context, this[i], i, this) === false) {
                    break; // or return;
                }
            }
        }
      }
   };
  };
  //自定义对象
  function cf(name){
    return this.$(name);
  }
  //常用函数
  cf.prototype={
    constructor:cf,
    addLoadEvent:function(new_load){
      var old_load=window.onload;
        if(typeof old_load!="function"){
          window.onload=new_load;
        }else{
          window.onload=function(){
            old_load();
            new_load();
          }
        }
    },
    $:function(el){
      return document.querySelector(el);
    },
    animate:function(element,json,callback,speed){  
      if(!speed){
        var speed=50;
      }
      clearInterval(element.timer);     
      element.timer=setInterval(function(){
        var flag=true;
        for(var attr in json){
          //1.获取当前值
          var icur=0;
          if(attr=="opacity"){
            icur=parseFloat(domEnhance.getStyle(element,attr))*100;
            icur=Math.round(icur);
          }else{
            icur=parseInt(domEnhance.getStyle(element,attr));
          } 
          //2.计算速度
          var step=(json[attr]-icur)/8;
          step=step>0?Math.ceil(step):Math.floor(step);
          //3.判断停止和刷新
          if(icur!=json[attr]){
            flag=false;
            if(attr=="opacity"){
              element.style.filter="alpha(opacity:'+(icur+step)+')";
              element.style.opacity=(icur+step)/100;
            }else{
              element.style[attr]=icur+step+"px";
            }           
          }
          if(flag){
            clearInterval(element.timer);
            if(callback){
              callback();
            }
          }
        }
      },speed); 
    },
    getTrail:function(){
      var items=location.pathname.substr(1).split("/");
      var mainPath="<a href='"+location.protocol+"//"+location.hostname;
      var bct="<p>";
      for(var i=0;i<items.length;i++){
        // 当结尾有反斜杠时
        if(items[i].length==0){
          break;
        }
        
        mainPath+=items[i];
        // 最后一个不用加反斜杠和箭头 
        if(i<items.length-1){
          mainPath+="/";
          items[i]+="->";
        }
        bct+=mainPath+"'>"+items[i]+"</a>";
      }
      return bct+="</p>";
    },
    extend:function(obj1,obj2){
      var obj3 = new Object();
      for(var pro1 in obj1){
        obj3[pro1] = obj1[pro1];
      }
      for(var pro2 in obj2){
        obj3[pro2] = obj2[pro2];
      }
      return obj3;
    }
  };
  //事件相关
  cf.prototype.event={
    add:function(element,type,handler){
      if(element.addEventListener){
       element.addEventListener(type,handler,false);
      }else if(element.attachEvent){
       element.attachEvent('on'+type,handler);
      }else{
       element['on'+type]=handler;
      }
    },
    remove:function(element,type,handler){
      if(element.removeEventListener){
        element.removeEventListener(type,handler,false);
      }else if(element.detachEvent){
        element.detachEvent('on'+type,handler);
      }else{
        element['on'+type]=null;
      }
    },
    getEvent:function(event){
      return event?event:window.event;
    },
    getType:function(event){
      return event.type;
    },
    getTarget:function(event){
      return event.target || event.srcElement;
    },
    preventDefault:function(event){
      if(event.preventDefault){
        event.preventDefault();
      }else{
        event.returnValue=false;
      }
    },
    stopPropagation:function(event){
      if(event.stopPropagation){
         event.stopPropagation();
      }else{
        event.cancelBubble=true;
      }
    },
    //获得按键的字符编码
    getCharCode:function(event){
      if(typeof event.which=="number"){
        return event.which;//w3c
      }else{
        return event.keyCode;//ie
      }
    },
    getChar:function(event){
      var code=this.getCharCode(event);
      return String.fromCharCode(code);
    },
    getRelatedTarget:function(event){
      if(event.relatedTarget){
        return event.relatedTarget;
      }else if(event.toElement){
        return event.toElement;
      }else if(event.fromElement){
        return event.fromElement;
      }else{
        return null;
      }
    },
    //鼠标
    getButton:function(event){
      if(document.implementation.hasFeature("MouseEvents","2.0")){
        return event.button;
      }else{
        switch(event.button){
          case 0:
          case 1:
          case 3:
          case 5:
          case 7:
            return 0;
          case 2:
          case 6:
            return 2;
          case 4:
            return 1;
        }
      }
    },
    //mousewheel事件
    getWheelDelta:function(event){
      if(event.wheelDelta){
        return event.wheelDelta;
      }else{
        return -event.detail*40;
      }
    },
    //相对于页面的位置，不是视口的位置
    getMousePosition:function(event){
      var x=0,y=0;
      if(event.pageX){
        x=event.pageX;
        y=event.pageY;
      }else if(event.clientX){
        x=event.clientX+document.body.scrollLeft||document.documentElement.scrollLeft;
        y=event.clientY+document.body.scrollTop||document.documentElement.scrollTop;
      }

      return {
        x: x,
        y: y
      }
    },
    getMousePositionOffset:function(event){
      var x = 0, y = 0;
      if(event.offsetX){
        x = event.offsetX;
        y = event.offsetY;
      }else if(event.layerX){
        x = event.layerX;
        y = event.layerY;
      }
      return {
        x: x,
        y: y
      }
    }
  };
  //dom增强
  cf.prototype.dom={
    //the parent_id is needed if not,will be document
    getElementsByClass:function(clsName,parentNode){

      var parentNode = parentNode?parentNode:document;
      
      var eles=[];
      var elements=parentNode.childNodes;

      for(var i=0;i<elements.length;i++){
        if(elements[i].className==clsName){
          eles.push(elements[i]);
        }
      }
      return eles;
    },
    //
    addClass:function(element,clsName){
      if(element.className){
        element.className+=" "+clsName;
      }else{
        element.className=clsName;
      }
    },
    removeClass:function(element,clsName){
      var result=[];
      var classArr = element.className.split(" ");
      console.log(classArr);
      for(var i=0;i<classArr.length;i++){
        if(classArr[i] != clsName){
          result.push(classArr[i]);
        }
      }
      element.className = result.join(" ");
    },
    //获取下一个元素节点
    getNextElement:function(cur_node){
      var node=cur_node.nextSibling;

      return (function helper(node){

        if(node.nodeType == 1){

          return node;
        }

        if(node.nextSibling){
          return helper(node.nextSibling);
        }

        return null;
      })(node);

    },
    //获取css属性
    getStyle:function(element,attr){
        if(element.currentStyle){
            return element.currentStyle(attr);//ie
        }else{
            return getComputedStyle(element,false)[attr];//firefox
        }
    }
  };
  //ajax直接调用
  cf.prototype.ajax={
    get:function(request,url,json,fn){
      //生成请求url
      var reUrl=this.createUrl(url,json);
      //ajax get
      request.open("GET",reUrl);
      request.send();
      request.onreadystatechange=function(){
        if(request.readyState===4){
          if(request.status>=200&&request.status<300){
            fn();
          }else{
            alert("发生错误"+request.status);
          }
        }
      }
    },
    post:function(request,url,json,fn){
      request.open("POST",url);
      // var data=createData(json);
      data="name=" + document.getElementById("staffName").value 
                        + "&number=" + document.getElementById("staffNumber").value 
                        + "&sex=" + document.getElementById("staffSex").value 
                        + "&job=" + document.getElementById("staffJob").value;
      request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
      request.send(data);
      request.onreadystatechange=function(){
        if (request.readyState===4) {
          if (request.status>=200 && request.status<300) {
            fn();
          } else {
            alert("发生错误：" + request.status);
          }
        } 
      }
    },
    createUrl:function(url,json){
      for(var name in json){
        url+=(url.indexOf("?")==(-1)?"?":"&");
        url+=encodeURIComponent(name)+"="+encodeURIComponent(json[name]);
      }
      return url;
    }
  };
  return new cf();
}));