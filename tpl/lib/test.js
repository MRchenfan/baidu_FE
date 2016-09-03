require.config({
  paths: {
    sdk: "common/sdk",
    cf: "common/cf.1.1",
    zepto: "common/zepto"
  }
});

require(['sdk', 'cf', 'zepto'], function(voice, cf, $) {

  var main = document.getElementById('main');
  var speaker = document.getElementById('speaker');

  if (!voice.isSupported()) {
    appendLog('sdk not support');
    return;
  }

  var logId = 0;
  var logIdPrefix = 'log-';

  function appendLog(message) {
      var div = document.createElement('p');
      div.id = logIdPrefix + (logId++);
      div.innerHTML = message;
      main.appendChild(div);
      div.scrollIntoView();
  }

  window.appendLog = appendLog;


  voice.on('ready', function() {
      appendLog('ready');
  });

  voice.on('speech-begin', function() {
      appendLog('speech-begin');
  });

  voice.on('speech-end', function() {
      appendLog('speech-end');
  });

  voice.on('error', function(error) {
      appendLog('error:' + JSON.stringify(error));
  });

  voice.on('result', function(data) {
    var domain = data.results[0].domain;
    var intent = data.results[0].intent;
    var date = data.results[0].object.date.substring(0,10);
    var focus = data.results[0].object.focus;
    var raw_text = data.raw_text;

    var str = domain + "</br>" + intent + "</br>" + date +"</br>"+ focus +"</br>"+ raw_text;
    // appendLog(str);
    appendLog(JSON.stringify(data));
  });


  speaker.addEventListener('touchstart', function() {
      voice.start();
  });

  speaker.addEventListener('touchend', function() {
      voice.stop();
  });

  appendLog('语音 SDK 已加载~！开始使用吧~！');

  if (voice.isSupported()) {
      document.getElementById("NaN").innerHTML = "voice is supported!!";
  }

});

