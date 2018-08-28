var Tools = (function () {
    return {
        'isTimeIn': function (now, begin, end) {
            return moment.duration(now.format('HHmmss') - begin.format('HHmmss')) > 0 && moment.duration(now.format('HHmmss') - end.format('HHmmss')) < 0;
        },
        'isTimeBefore': function (time1, time2) {
            return moment.duration(time1.format('HHmmss') - time2.format('HHmmss')) < 0;
        },
        'isTimeAfter': function (time1, time2) {
            return moment.duration(time1.format('HHmmss') - time2.format('HHmmss')) > 0;
        },
        'padLeft': function (s, len, padChar) {
            return (Array(len + 1).join(padChar) + (s)).split('').reverse().join('').substring(0, len).split('').reverse().join('');
        },
        Mp3: (function () {
            soundManager.setup({
                url: 'swf/soundmanager2.swf',
                debugMode: false,
                onready: function () {
                    soundManager.createSound({
                        id: 'msg',
                        url: '/mp3/msg.mp3'
                    });
                    soundManager.createSound({
                        id: 'alert',
                        url: '/mp3/alert.mp3'
                    });
                    soundManager.createSound({
                        id: 'last',
                        url: '/mp3/endnsound.mp3'
                    });
                    soundManager.createSound({
                        id: 'open',
                        url: '/mp3/open.mp3'
                    });
                    soundManager.createSound({
                        id: 'guoan',
                        url: '/mp3/guoan.mp3'
                    });
                },
                ontimeout: function () {
                    // Hrmm, SM2 could not start. Missing SWF? Flash blocked? Show an error, etc.?
                    //alert('您使用的浏览器不支持声音播放');
                }
            });
            return {
                alert: function () {
                    soundManager.play('alert');
                },
                last: function () {
                    soundManager.play('last');
                },
                msg: function () {
                    soundManager.play('msg');
                },
                open: function () {
                    soundManager.play('open');
                },
                guoan: function () {
                    soundManager.play('guoan');
                }
            }
        })(),
        playNumber: (function () {
            soundManager.onready(function () {
                soundManager.createSound({
                    id: '-1',
                    url: 'audio/open.wav'
                });
                soundManager.createSound({
                    id: 'm_0',
                    url: 'audio/m_0.wav'
                });
                soundManager.createSound({
                    id: 'm_1',
                    url: 'audio/m_1.wav'
                });
                soundManager.createSound({
                    id: 'm_2',
                    url: 'audio/m_2.wav'
                });
                soundManager.createSound({
                    id: 'm_3',
                    url: 'audio/m_3.wav'
                });
                soundManager.createSound({
                    id: 'm_4',
                    url: 'audio/m_4.wav'
                });
                soundManager.createSound({
                    id: 'm_5',
                    url: 'audio/m_5.wav'
                });
                soundManager.createSound({
                    id: 'm_6',
                    url: 'audio/m_6.wav'
                });
                soundManager.createSound({
                    id: 'm_7',
                    url: 'audio/m_7.wav'
                });
                soundManager.createSound({
                    id: 'm_8',
                    url: 'audio/m_8.wav'
                });
                soundManager.createSound({
                    id: 'm_9',
                    url: 'audio/m_9.wav'
                });
                soundManager.createSound({
                    id: 'm_10',
                    url: 'audio/m_10.wav'
                });
                soundManager.createSound({
                    id: 'm_11',
                    url: 'audio/m_11.wav'
                });
            });
            return {
                _check: function (number) {
                    var status = true;
                    for (var i = 0; i < number.length; i++) {
                        var _s = false;
                        for (var j = 0; j < 100; j++) {
                            if (number[i] == j) {
                                _s = true;
                                break;
                            }
                        }
                        if (!_s) {
                            status = false;
                            break;
                        }
                    }
                    return status;
                },
                play: function (number) {
                    if ($.type(number) === 'string') {
                        number = number.split(',');
                    }
                    if (!$.isArray(number) || !number.length) {
                        return;
                    }
                    number = $.map(number, function (_record) {
                        return Number(_record);
                    });
                    if (!this._check(number)) {
                        return;
                    }
                    //soundManager.play('-1');
                    clearTimeout(this._pauseTimer);
                    clearTimeout(this._jiangeTimer);
                    this._pauseTimer = setTimeout(function () {;
                        (function (list, index) {
                            if (typeof list[index] === 'undefined') {
                                return;
                            }
                            var callee = arguments.callee;
                            this._jiangeTimer = setTimeout(function () {
                                soundManager.play('m_' + list[index] + '');
                                callee(list, index + 1);
                            }, 600);
                        })(number, 0);
                    }, 1800);
                }
            }
        })()
    }

})();