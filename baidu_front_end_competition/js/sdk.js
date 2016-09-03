/**
 * @file voice playground sdk
 * @author leon(ludafa@outlook.com)
 */

/* globals define */

// 如果不需要引用require，就直接：root.voice=factory(root); 全局中定义voice

(function (root, factory) {

    if (typeof define === 'function' && define.amd) {
        define([], function () {
            return factory(root);
        });
    }
    else if (typeof exports === 'object' && module.exports) {
        module.exports = factory(root);
    }
    else {
        root.voice = factory(root);
    }

}(this, function (root) {

    var NATIVE_SDK_INTERFACE_KEY = '__efe_voice_playground_native_sdk__';
    var NATIVE_SDK_CALLBACK_NAME = 'efeVoicePlaygroundCallback';

    var native = root[NATIVE_SDK_INTERFACE_KEY];//native就是java入口，可以调用java中的方法

    var listeners = {};//存放自定义事件
    var working = false;//是否工作中
    var supported = native && typeof native.start === 'function';//如果不存在，则为undefined

    var ERROR_CONSTANTS = {

        1: {
            code: 'ERROR_NETWORK_TIMEOUT',
            message: '网络超时'
        },
        2: {
            code: 'ERROR_NETWORK',
            message: '网络错误'
        },
        3: {
            code: 'ERROR_AUDIO',
            message: '录音错误'
        },
        4: {
            code: 'ERROR_SERVER',
            message: '服务端错误'
        },
        5: {
            code: 'ERROR_CLIENT',
            message: '客户端调用错误'
        },
        6: {
            code: 'ERROR_SPEECH_TIMEOUT',
            message: '超时'
        },
        7: {
            code: 'ERROR_NO_MATCH',
            message: '没有识别结果'
        },
        8: {
            code: 'ERROR_RECOGNIZER_BUSY',
            message: '引擎忙'
        },
        9: {
            code: 'ERROR_INSUFFICIENT_PERMISSIONS',
            message: '缺少权限'
        }
    };

    //定义回调方法，java中可以调用这个方法 type 错误类型 data 错误码 参看java接口文件
    root[NATIVE_SDK_CALLBACK_NAME] = function (type, data) {

        if (!working) {
            return;
        }

        if (type === 'error' || type === 'result') {
            working = false;
        }

        var handlers = listeners[type];

        if (!handlers || !handlers.length) {
            return;
        }

        handlers = handlers.slice();

        if (type === 'error') {
            data = ERROR_CONSTANTS[data];
        }
        else {
            data = data ? JSON.parse(data) : null;
        }

        for (var i = 0, len = handlers.length; i < len; i++) {
            handlers[i](data);//执行所有事件
        }

    };

    //返回voice的接口
    return {

        isSupported: function () {
            return supported;
        },

        start: function (options) {

            if (!supported || working) {
                return this;
            }

            working = true;
            native.start();
            return this;

        },

        stop: function () {

            if (!supported || !working) {
                return this;
            }

            native.stop();
            return this;
        },

        cancel: function () {

            if (!supported || !working) {
                return this;
            }

            native.cancel();
            working = false;
            return this;
        },

        //自定义事件
        on: function (type, handler) {

            var handlers = listeners[type];

            if (!handlers) {
                handlers = listeners[type] = [];
            }

            handlers.push(handler);

            return this;

        },

        off: function (type, handler) {

            if (!type) {
                listeners = {};
                return this;
            }

            if (!handler) {
                listeners[type] = [];
                return this;
            }

            var handlers = listeners[type];

            if (!handlers || !handlers.length) {
                return this;
            }

            listeners[type] = handlers.filter(function (h) {
                return h !== handler;
            });

            return this;

        }

    };

}));
