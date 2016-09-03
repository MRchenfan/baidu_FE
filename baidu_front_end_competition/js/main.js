require.config({
    paths:{
        sdk:"sdk",
        cf:"cf.1.1"
    }
});

require(['sdk','cf'], function(voice,cf) {
    
    alert(cf.name);

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
        appendLog(JSON.stringify(data));
    });

    voice.on('json_result', function(data) {
        appendLog(data);
    });

    speaker.addEventListener('touchstart', function() {
        voice.start();
    });

    speaker.addEventListener('touchend', function() {
        voice.stop();
    });

    appendLog('语音 SDK 已加载~！开始使用吧~！');

    if(voice.isSupported()){
        document.getElementById("NaN").innerHTML="voice is supported!!";
      }
      
});