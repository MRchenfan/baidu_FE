define(['sdk'],function(V){
  var dom = {
    poping:$('.poping'),
    pages:$('.page'),
    navs:$('.nav'),
    speaker:$('#speaker'),
    panel:$('.speaker-panel')
  }

  function renderPoping(title,content){
    //js.makeToast('找到结果');
    var poping = dom.poping;
    var p_header = poping.find('.poping-header');
    var p_content = poping.find('.poping-content');
    var p_footer = poping.find('.poping-footer');

    p_header.html('<h2>' + title + '</h2>');
    p_content.html(content);

    poping.removeClass('bounceOut').show();
  }
  function bindPoping(){
    var poping = dom.poping;
    $('#poping-cancel').on('tap',function(){
      poping.addClass('bounceOut');
    });

    $('#speaker').on('touchstart',function(){
      poping.addClass('bounceOut');
    });
  }
  function pageSwitch(){
    var pages = dom.pages;
    var navs = dom.navs;
    var active_id = 0; // 保存活动页面的id

    pages.on("swipeLeft",function(){

      $(navs[active_id]).removeClass('active');    

      active_id = parseInt(this.id.substring(4))+1;
      active_id = (active_id == pages.length)?active_id-1:active_id;

      $(navs[active_id]).trigger('tap')
      .addClass('active');
    });
    pages.on("swipeRight",function(){

      $(navs[active_id]).removeClass('active');  
      
      active_id = parseInt(this.id.substring(4))-1;
      active_id = (active_id == -1)?active_id+1:active_id;

      $(navs[active_id]).trigger('tap')
      .addClass('active');
    });
    navs.on("tap",function(){

      $(navs[active_id]).removeClass('active');

      active_id = parseInt(this.id.substring(4));

      $(this).addClass('active');
    });
  }
  function showArticle(){
    //主屏显示文本
    $.ajax({
      url: 'tpl/images/article.text',
      type: 'GET',
      dataType: 'text',
      success:function(str){

        $('#page0').find('.page0-article').html(str);
      }
    })
  }
  function changeStyle(){
    var pages = dom.pages;
    //var style_id = Math.round(Math.random()*6);// 0 1 2 3 4 5 6
    var style_id = 0;
    
    $('#speaker').addClass('style-' + style_id);
    pages.addClass('style-' + style_id);

    $('#config-style').on('tap',function(){
      changeStyle();
    });

    function changeStyle(){
      $('#speaker').removeClass('style-' + style_id);
      pages.removeClass('style-' + style_id)

      style_id++;
      style_id = (style_id == 7)?0:style_id;

      $('#speaker').addClass('style-' + style_id);
      pages.addClass('style-' + style_id);
    }
  }
  function voiceButton(){
    var speaker = dom.speaker,
    panel = dom.panel;
    speaker.on('touchstart', function(ev){
      ev.preventDefault();//解决touchend无法触发的bug
      panel.removeClass('bounceOutDown');
      V.start();
      panel.show();
      $('#speaker-mask').show();

    }).on('touchend', function() {
      V.stop();
      panel.addClass('bounceOutDown');
      $('#speaker-mask').hide();
    });
  }
  function voicePanel(){
    var panel = dom.panel;
    var speaker = dom.speaker;
    var rms = panel.find('.icon-feed');

    V.on('ready', function() {
    });

    V.on('speech-begin', function() {
      rms.css({color:'#FA1313'});
    });

    V.on('rms-change',function(rmsdB){
    });

    V.on('speech-end', function() {
      rms.css({color:'#14E31C'});
    });

  }
  return {
    init:function(config){
      $.extend(true, dom, config);
      bindPoping();
      pageSwitch();

      changeStyle();
      voiceButton();
    },
    renderPoping:renderPoping,
  }
});