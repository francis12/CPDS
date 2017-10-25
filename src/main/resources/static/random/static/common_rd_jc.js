var caipiao = 'rd60s';
var caipiaoMenu = $(".caipiao");
var dropCaipiao = $("#dropCaipiao");
var caipiaoText = $("#caipiaoText");
var betting = $('#betting');
var curPrizes = [];
var prizesData = [];
var way = ['ge'];
var recentid = 0;
var nextId = 0;
var before = $(window).width() > 500 ? 300 : 100;
var act = 'circle';
var stat = '冷热';
var cycle = 1;
var plan = 1;
var methodway = 'normal';
var ding = false;
var kline = true;
var content = new Array();
var bigAuto = [];
var rotate = "allji";
var storeNums = new Array();
var sh;
var lock = false;
var curPrizeNum = [];
var bollingMatched = false;

var dataMatched = false;

var TimerData = function() {
		var d = $("#startPrize");
		return {
			init: function() {
				caipiao = this.getCaipiaoCookie();
				caipiaoName = this.getcaipiaoname(caipiao);
				caipiaoText.val(caipiaoName);
				dropCaipiao.bind('click', function() {
					$('#dropdownCaipiao').show()
				});
				this.getdata(caipiao)
			},
			getCountdown: function(a) {
				var b = Math.floor(a / 60);
				var c = Math.floor(a - b * 60);
				$("#minute").html(b);
				$("#second").html(c);
				window.clearInterval(sh);
				sh = window.setInterval(function() {
					c--;
					if (c < 0) {
						c = 59;
						if (b > 0) {
							b--;
							$("#minute").html(b)
						}
					}
					$("#second").html(c)
				}, 1000)
			},
			layout: function(a) {
				$("#rencentId").html(a.peroid);
				if (d.val() == '') {
					d.val(a.peroid)
				}
				$("#nextId").html(a.nextid);
				nextId = a.nextid;
				for (var i in a.prizes) {
					$("#caipiaoNums button:eq(" + i + ")").html(a.prizes[i])
				}
				if ($("#autoupdate").is(":checked") && a.peroid != d.val()) {
					$("#before").val(before);
					d.val(a.peroid);
					switch (daShu) {
					case 'disk':
						KlineData.getdata(caipiao);
						break;
					case 'index':
						DansDatas.getdata(caipiao);
						break;
					case 'dudan':
						DudanDatas.getdata(caipiao);
						break;
					case 'random':
						RandomDatas.getdata(caipiao);
						break;
					case 'rotate':
						RotateDatas.getdata(caipiao);
						break;
					case 'formula':
						FormulaDatas.getdata(caipiao);
						break;
					case 'godnum':
						GodnumDatas.getdata(caipiao);
						break
					}
				}
			},
			getdata: function() {
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/init",
					data: "caipiao=" + caipiao,
					dataType: "json",
					success: function(a) {
						if (a) {
							curPrizes = a.prizes;
							TimerData.layout(a);
							TimerData.getCountdown(a.countdown);
							window.setTimeout(TimerData.getdata, 5000)
						}
					},
					error: function(a) {
                        window.setTimeout(TimerData.getdata, 5000)
					}
				})
			},
			getcaipiaoname: function(a) {
				var b = "重庆时时彩";
				switch (a) {
				case "chongqing":
					b = "重庆时时彩";
					break;
				case "HeleShi":
					b = "HI彩时时彩";
					break;
				case "HeleFeng":
					b = "HI彩分分彩";
					break;
				case "JyFive":
					b = "久游五分彩";
					break;
				case "JyTwo":
					b = "久游三分彩";
					break;
				case "JyFen":
					b = "久游分分彩";
					break;
				case "HeNei":
					b = "河内五分彩";
					break;
				case "HeNeiFen":
					b = "河内分分彩";
					break;
				case "YinLi":
					b = "印尼五分彩";
					break;
				case "YinLiFen":
					b = "印尼分分彩";
					break;
				case "HrTwo":
					b = "华人两分彩";
					break;
				case "HrFen":
					b = "华人分分彩";
					break;
				case "UcaiFive":
					b = "U彩五分彩";
					break;
				case "UcaiFen":
					b = "U彩分分彩";
					break;
				case "JwThree":
					b = "欢乐三分彩";
					break;
				case "JwFen":
					b = "幸运分分彩";
					break;
				case "JiubaFeng":
					b = "急速60秒";
					break;
				case "JiubaShi":
					b = "幸运彩";
					break;
				case "TianJin":
					b = "天津时时彩";
					break;
				case "TaiWan":
					b = "台湾5分彩";
					break;
				case "JyHg":
					b = "韩国1.5分彩";
					break;
				case "CanadaFen":
					b = "加拿大3.5分彩";
					break;
				case "XjpTwo":
					b = "新加坡2分彩";
					break;
				case "FlbFen":
					b = "菲律宾1.5彩彩";
					break;
				case "BeiJing":
					b = "北京5分彩";
					break;
				case "JiubaNinty":
					b = "198 1.5分彩";
					break
				}
				return b
			},
			getcaipiaoflag: function(a) {
				var b = 0;
				switch (a) {
				case "chongqing":
					b = -3;
					break;
				case "HeleShi":
					b = 0;
					break;
				case "HeleFeng":
					b = 0;
					break;
				case "JyFive":
					b = -3;
					break;
				case "JyTwo":
					b = -3;
					break;
				case "JyFen":
					b = -4;
					break;
				case "HeNei":
					b = -3;
					break;
				case "HeNeiFen":
					b = -4;
					break;
				case "YinLi":
					b = -3;
					break;
				case "YinLiFen":
					b = -4;
					break;
				case "HrTwo":
					b = -3;
					break;
				case "HrFen":
					b = -4;
					break;
				case "UcaiFive":
					b = -3;
					break;
				case "UcaiFen":
					b = -4;
					break;
				case "JwThree":
					b = -3;
					break;
				case "JwFen":
					b = -4;
					break;
				case "JiubaFeng":
					b = 0;
					break;
				case "JiubaShi":
					b = 0;
					break;
				case "TianJin":
					b = -3;
					break;
				case "TaiWan":
					b = 0;
					break;
				case "JyHg":
					b = 0;
					break;
				case "CanadaFen":
					b = 0;
					break;
				case "XjpTwo":
					b = 0;
					break;
				case "FlbFen":
					b = -3;
					break;
				case "BeiJing":
					b = 0;
					break;
				case "JiubaNinty":
					b = 0;
					break
				}
				return b
			},
			getcaipiaolimit: function(a) {
				var b = 120;
				switch (a) {
				case "chongqing":
					b = 120;
					break;
				case "HeleShi":
					b = 0;
					break;
				case "HeleFeng":
					b = 0;
					break;
				case "JyFive":
					b = 288;
					break;
				case "JyTwo":
					b = 480;
					break;
				case "JyFen":
					b = 1440;
					break;
				case "HeNei":
					b = 288;
					break;
				case "HeNeiFen":
					b = 1440;
					break;
				case "YinLi":
					b = 288;
					break;
				case "YinLiFen":
					b = 1440;
					break;
				case "HrTwo":
					b = 690;
					break;
				case "HrFen":
					b = 1380;
					break;
				case "UcaiFive":
					b = 312;
					break;
				case "UcaiFen":
					b = 1440;
					break;
				case "JwThree":
					b = 440;
					break;
				case "JwFen":
					b = 1440;
					break;
				case "JiubaFeng":
					b = 0;
					break;
				case "JiubaShi":
					b = 0;
					break;
				case "TianJin":
					b = 120;
					break;
				case "TaiWan":
					b = 0;
					break;
				case "JyHg":
					b = 0;
					break;
				case "CanadaFen":
					b = 0;
					break;
				case "XjpTwo":
					b = 0;
					break;
				case "FlbFen":
					b = 920;
					break;
				case "BeiJing":
					b = 0;
					break;
				case "JiubaNinty":
					b = 0;
					break
				}
				return b
			},
			getCaipiaoCookie: function() {
				var a = $.cookie('caipiao');
				if (typeof(a) == "undefined") {
					a = 'chongqing';
					this.setCaipiaoCookie('chongqing')
				}
				return a
			},
			setCaipiaoCookie: function(a) {
				$.cookie('caipiao', a, {
					expires: 7,
					path: '/',
					domain: 'localhost:8080'
				})
			},
		}
	}();
	
	//新增策略
	//1.布林盘整，在下轨买入
var RandomBollingTrategy = function() {
    return {
        start: function() {

            randonButtonIntervalClick = window.setInterval(function() {
                $('#randomNum').click();
            }, 5000);

        },

        isMatch: function(j) {
            var bollUp = j.bollup;
            var bollMiddle = j.bollmiddle;
            var bollDown = j.bolldown;
            //defatul : 0.056,20
            var rate = 0.056;
            var lastItems = 20;
            try {
                var isBollUpCommon = this.isCommonRate(bollUp, rate, lastItems) && this.isCommonRate(bollMiddle, rate, lastItems) && this.isCommonRate(bollDown, rate, lastItems);
                //
                //当图表类型为K线图时，其数值设置比较特殊，他的数值内容为长度为4的数组，分别代表[开盘价，收盘价，最低值，最高值]
                var isKlineMatch = this.isKlineInbottom(j, 100) && this.isKLineUnderBollMiddle(j);
                //var isAve = this.isAverage(j, 2);
                /*var kUp = !this.isKlineInbottom(j, 6) || !this.isKlineInbottom(j, 10) ||!this.isKlineInbottom(j, 16)||!this.isKlineInbottom(j, 30) || !this.isKlineInbottom(j, 3);

                if ( isBollUpCommon && isKlineMatch && kUp ){
                    console.log(this.isKlineInbottom(j, 3));
                    console.log(this.isKlineInbottom(j, 6));
                    console.log(this.isKlineInbottom(j, 10));
                    console.log(this.isKlineInbottom(j, 16));
                    console.log(this.isKlineInbottom(j, 30));
                    return true;
                } else {
                    return false;
                }*/
                //this.isAverage(j,2 , 2)&&
                return  this.isMachJC(j, 3, 2) && this.isBollUp(j, 3);
                //return this.isAverage(j,3) && this.isMacdBarRedPlus(j, 2, 4);

            } catch (err) {
                return false;
            }
        },
        //判断盘整src的偏离平均值情况
        isCommonRate: function(src, rate, last) {
            var total = 0;
            for (var i = src.length -last;i<src.length;i++) {
                total += src[i-1];
                if (i != src.length -last) {
                    var avg =  total/(i - (src.length -last) + 1);

                    if ( Math.abs( (Math.abs(src[i])-Math.abs(avg) ))
                        > Math.abs(avg) * rate ) {
                        return false;
                    }
                }
            }
            return true;
        },
        //判断k线位于中轨下且最后一期未开出
        isKLineUnderBollMiddle: function(j) {
            var kline =  j.values[j.values.length -1 ] ;
            var bollMiddle = j.bollmiddle[ j.bollmiddle.length -1];
            if (kline[0] > kline[1] &&kline[0]<bollMiddle) {
                return  true;
            }
            return false;
        },
        //判断k线当前处于位置
        isKlineLessThanLineCommon: function(j) {
            var kline =  j.values[j.values.length -1 ] ;

            return kline[0] < j.line30[j.line30.length -1];
        },
        //判断在近期低点
        isKlineInbottom2: function(j, last) {
            var kline =  j.values[j.values.length -1 ] [0];
            var total = 0;
            for (var i = j.values.length -last;i<j.values.length;i++) {
                total +=j.values[i][0];
            }
            return total/last > kline ;
        }
        ,
        //判断在近期低点
        isKlineInbottom: function(j, last) {
            var kline =  j.values[j.values.length -1 ] [0];
            var totalPre = 0;
            for (var i = j.values.length -last;i<j.values.length - last/2;i++) {
                totalPre +=j.values[i][0];
            }
            var totalPost = 0;
            for (var i = j.values.length - last/2;i<j.values.length;i++) {
                totalPost +=j.values[i][0];
            }
            return totalPre > totalPost;
        },
        //判断在近期低点
        isBollUp: function(j, last) {
            for (var i = j.bolldown.length -last;i<j.macdbar.length - 1;i++) {
                if (j.bolldown[i + 1] < j.bolldown[i]) {
                    return false;
                }
            }
            return true;
        },



        //均线last期金叉
        isAverage: function(j, last, last2) {

            for (var i = j.line60.length -last;i<j.line60.length ;i++) {
                if (j.line15[i] < j.line60[i]) {
                    return false;
                }
            }

            for (var i = j.line60.length -last-last2;i<j.line60.length  -last;i++) {
                if (j.line15[i] > j.line60[i]) {
                    return false;
                }
            }
            return true;
        },
        //均线last期金叉
        isMachJC: function(j, last, last2) {

            if(j.macdbar[j.macdbar.length - 1] < j.macdbar[j.macdbar.length - 2]) {
                return false;
            }
            if(j.dif[j.dif.length-1] < 0 || j.dea[j.dea.length -1] < 0) {
                return false;
            }
            for (var i = j.macdbar.length -last;i<j.macdbar.length - 1;i++) {
                if (j.dif[i + 1] < j.dif[i] || j.dea[i + 1] < j.dea[i]) {
                    return false;
                }
            }

            for (var i = j.dif.length -last;i<j.dif.length ;i++) {
                if (j.dif[i] < j.dea[i]) {
                    return false;
                }
            }

            for (var i = j.dif.length -last-last2;i<j.dif.length  -last;i++) {
                if (j.dif[i] > j.dea[i]) {
                    return false;
                }
            }
            return true;
        },
        //Macdbar 最近last期为红，之前为绿,last2:last2期为直线增长方向
        isMacdBarRedPlus: function(j, last, last2) {

            var maxValue = Math.max(j.values[j.values.length -1 ][0], j.values[j.values.length -1 ][1]);
            //k线不能太高
            if ((j.bollup[ j.bollup.length -1]  + j.bollmiddle[ j.bollmiddle.length -1] )/2 <
                j.values[j.values.length -1 ][1]  ) {
                return false;
            }

            //K线不能触碰上轨
            /*if (j.values[j.values.length -1 ][0] >= (j.bollup[ j.bollup.length -1] )) {
                return false;
            }
            if (j.values[j.values.length -1 ][1] >= (j.bollup[ j.bollup.length -1] )) {
                return false;
            }*/
            /*var maxValue = Math.max(j.values[j.values.length -1 ][0], j.values[j.values.length -1 ][1]);
            if ((j.bollup[ j.bollup.length -1] ) - j.values[j.values.length -1 ][1]  < 1) {
                return false;
            }*/
            /*//刚好触碰中轨
            if(!(j.bollmiddle[ j.bollup.length -1] > j.values[j.values.length -1 ][0] &&  j.bollmiddle[ j.bollup.length -1] < j.values[j.values.length -1 ][1])
                && !(j.bollmiddle[ j.bollup.length -1] < j.values[j.values.length -1 ][0] &&  j.bollmiddle[ j.bollup.length -1] > j.values[j.values.length -1 ][1])
                ) {
                return false;
            }*/
            // if(maxValue > (j.bollup[ j.bollup.length -1] ) || ((j.bollup[ j.bollup.length -1] ) - maxValue)/maxValue < 0.16 ) {
            // return false;
            // }
            // //last 前一期在水下
            // if(j.dif[j.dif.length - last - 1] > 0) {
            //     return false;
            // }
            //
            // if(j.dif[j.dif.length - last ] < 0) {
            //     return false;
            //}

            //最近两次macdbar大于0，再之前两次小于0
            /*if (j.macdbar[j.macdbar.length -1] < 0 || j.macdbar[j.macdbar.length -2] < 0
                    || (j.macdbar[j.macdbar.length -3] > 0 && j.macdbar[j.macdbar.length -4] > 0 ) ) {
                    return false;
                }*/

            //第前last期大于0
            /*if (j.macdbar[j.macdbar.length -last] < 0) {
                return false;
            }*/
            //近last期macd上升曲线
            for (var i = j.macdbar.length -last;i<j.macdbar.length - 1;i++) {
                if (j.macdbar[i + 1] < j.macdbar[i]) {
                    return false;
                }
            }

            //近last2期上升曲线
            for (var i = j.macdbar.length -last2;i<j.macdbar.length - 1;i++) {
                if (j.dif[i + 1] < j.dif[i] || j.dea[i + 1] < j.dea[i]) {
                    return false;
                }
            }

            // for (var i = j.macdbar.length -last;i<j.macdbar.length;i++) {
            //     if(j.macdbar[i] <0 || j.dif[i] <j.dea[i] || j.dif[i] < 0 || j.dea[i] < 0) {
            //         return false;
            //     }
            // }



            return true;
        }
    }

}();

var GodkeyDatas = function() {
		var z = $("#startPrize");
		var A = $("#before");
		var B = $("#btnStatistic");
		var C = $(".way");
		var D = $('#morebox');
		var E = $('#main');
		var F = $('#betting');
		var G = $('#godkey');
		var H = $("#klinebox");
		var I = $("#yiloubox");
		var D = $("#morebox");
		var J = $("#staticbox");
		var K = $("#zuNums");
		var L = $('#storeNums');
		return {
			init: function() {
				before = A.val();
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					F.html('');
					TimerData.getdata(caipiao);
					$('#dropdownCaipiao').hide()
				});
				B.on('click', function() {
					GodkeyDatas.searchdata(caipiao)
				});
				C.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						ding = false;
						if (way == 'ding') {
							ding = true
						}
						storeNums = new Array();
						F.html('');
						GodkeyDatas.checkvalues();
						GodkeyDatas.layout(prizesData)
					}
				});
				H.on('click', function() {
					kline = true;
					GodkeyDatas.layout(prizesData)
				});
				I.on('click', function() {
					kline = false;
					GodkeyDatas.layout(prizesData)
				});
				D.on('click', function() {
					$('#klinemap').html('fdsgf').css('color', '#fff')
				});
				J.on('click', function() {
					$('#klinemap').html('dsafdsa').css('color', '#fff')
				})
			},
			checkvalues: function() {
				if (recentid != z.val() || before != A.val()) {
					recentid = parseInt(z.val());
					before = parseInt(A.val());
					GodkeyDatas.getdata(caipiao)
				}
				return
			},
			resetGodNum: function() {
				var a = ['挂中挂挂挂挂中中', '中中挂挂挂挂中中', '挂挂挂挂挂挂挂中', '挂挂挂中中挂挂中中', '挂中挂中挂中', '中挂挂挂挂中', '中挂挂中挂挂中', '中中中中中挂挂中中挂中', '挂挂挂挂挂', '挂中中挂中', '中中挂挂挂挂中中挂挂中中挂中挂'];
				var b = parseInt(Math.random() * 10);
				G.val(a[b])
			},
			getRandomNums: function(a) {
				var b = Math.pow(10, a.length);
				var c = new Array();
				while (1) {
					if (c.length < b) {
						c = CommonObj.generateRandom(b, c)
					} else {
						break
					}
				}
				return c
			},
			searchGodKeys: function(a, b, c) {
				var d = new Array();
				var e = 0;
				while (1) {
					var f = this.getRandomNums(b);
					d = f.slice(0, c);
					var g = this.prizesData(b, a, d, 1);
					var h = g[1];
					var i = KlineMaps.getMacdBar(h);
					var j = i[0].slice(-10);
					var k = i[1].slice(-10);
					var l = 0;
					var m = 0;
					for (m in j) {
						var n = Math.abs(j[m] - k[m]);
						l = l > n ? l : n
					}
					if (e == 100) {
						break
					} else {
						if ((j[m - 1] == k[m - 1] && j[m] < k[m]) || (j[m - 1] > k[m - 1] && j[m] == k[m])) {
							alert(d);
							e++
						}
						e++
					}
				}
			},
			insect: function(a, b) {
				var c = 0,
					bi = 0;
				var d = new Array();
				while (c < a.length && bi < b.length) {
					if (a[c] < b[bi]) {
						c++
					} else if (a[c] > b[bi]) {
						bi++
					} else {
						d.push(a[c]);
						c++;
						bi++
					}
				}
				return d
			},
			union: function(x, y) {
				for (var j = 0; j < y.length; j++) {
					if (!this.getInArray(y[j], x)) {
						x.push(y[j])
					}
				}
				return x
			},
			reduce: function(x, y) {
				var a = new Array();
				for (var i = 0; i < x.length; i++) {
					if (!this.getInArray(x[i], y)) {
						a.push(x[i])
					}
				}
				return a
			},
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = G.val();
					var c = parseInt(K.val());
					var d = this.getGodNum(prizes, way, b);
					d = this.getLimitsNum(d, way, c);
					var e = this.prizesData(way, prizes, d, 1);
					var f = "klinemap";
					KlineMaps.createmap('', e, f);
					var g = "大数计划 " + d.length + "注</br>第" + nextId + "期的投注号为：</br><textarea class='bettings span12'>" + d.join(' ') + "</textarea>";
					$("#betting").html(g)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			getdata: function(b) {
				recentid = z.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							GodkeyDatas.layout(a)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			godlist: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = parseInt(K.val());
					prizeNum = this.searchGodKeys(prizes, way, b)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			searchdata: function(b) {
				recentid = z.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							GodkeyDatas.godlist(a)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			prizesData: function(a, b, c, d) {
				var e = new Array();
				var f = new Array();
				var g = new Array();
				var h = 0;
				var j = 1;
				var k = 0;
				var l = 0;
				var m = 0;
				var n = 0;
				for (var i in b) {
					var o = b[i]['peroid'];
					o % d == 0 && e.push(o);
					g[o] = b[i]['prize'];
					var p = b[i]['prize'].split('');
					var q = 1;
					var r = '';
					if (ding) {
						var s = 0;
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							if (c[a[t]]) {
								q = 10;
								var v = c[a[t]].length;
								s += this.getInArray(p[u], c[a[t]]) == kline ? (q - v) / v : -1
							}
						}
					} else {
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							r += p[u];
							q *= 10
						}
						if (j == plan) {
							h = o;
							j = 1
						} else {
							h = h == 0 ? o : h;
							j++
						}
						var w = c;
						var v = w.length;
						var s = this.getInArray(r, w) == kline ? (q - v) / v : -1
					}
					l += s;
					m = m > l ? m : l;
					n = n < l ? n : l;
					if (o % d == 0) {
						var x = new Array(k, l, m, n);
						f.push(x);
						m = n = k = l
					}
				}
				return new Array(e, f, g, c)
			},
			getInArray: function(a, b) {
				for (var i in b) {
					if (a == b[i]) {
						return true
					}
				}
				return false
			},
			getPrizeNum: function(a) {
				var b = new Array();
				for (var c in a) {
					var d = a[c];
					if (b.length > 0) {
						var e = new Array();
						for (var i in b) {
							for (var j in d) {
								e.push(b[i].toString() + d[j])
							}
						}
						b = e;
						e = new Array()
					} else {
						b = d
					}
					d = new Array()
				}
				return b
			},
			randomsort: function(a, b) {
				return Math.random() > .5 ? -1 : 1
			},
		}
	}();
var DansDatas = function() {
		var G = $("#startPrize");
		var H = $("#before");
		var I = $("#btnStatistic");
		var J = $(".way");
		var K = $("#tomato li");
		var L = $("#planInfo");
		var M = "个位";
		var N = 'zong';
		var O = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
		var P = [];
		var Q = 1;
		var R = 'intro';
		return {
			init: function() {
				before = H.val();
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					TimerData.getdata(caipiao);
					DansDatas.getdata(caipiao);
					$('#dropdownCaipiao').hide()
				});
				I.on('click', function() {
					DansDatas.checkvalues();
					P = [];
					Q = Math.ceil(Math.random() * 5);
					DansDatas.layout(prizesData)
				});
				J.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						ding = false;
						if (way == 'ding') {
							ding = true;
							way = ['wan', 'qian', 'bai', 'shi', 'ge']
						}
						M = $.trim($(this).html());
						Q = Math.ceil(Math.random() * 5);
						DansDatas.checkvalues();
						P = [];
						DansDatas.layout(prizesData)
					}
				});
				K.on('click', function() {
					R = $(this).attr('init');
					DansDatas.checkvalues();
					DansDatas.klinemap(prizesData)
				});
				Q = Math.ceil(Math.random() * 5);
				this.getdata(caipiao)
			},
			checkvalues: function() {
				if (recentid != G.val() || before != H.val()) {
					recentid = parseInt(G.val());
					before = parseInt(H.val());
					DansDatas.getdata(caipiao)
				}
				return
			},
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = $('.tab-content .red').html().replace(/(^\s*)|(\s*$)/g, "") + " - " + before + "期胆出次统计";
					var c = this.countdata(way, a.prizes);
					this.createmap(b, c);
					this.klinemap(a)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			klinemap: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					P = P.length > 0 ? P : this.smartplan(way);
					switch (R) {
					case "intro":
						$("#tab_2").hide();
						this.planShow(way, prizes, P, 1, Q);
						break;
					case "kline":
						$("#tab_2").show();
						var b = this.prizesData(way, prizes, P, 1, Q);
						var c = "klinemap";
						KlineMaps.createmap('', b, c);
						break;
					case "search":
						$("#tab_2").hide();
						break
					}
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			getdata: function(b) {
				recentid = G.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							DansDatas.layout(a)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			smartplan: function(a) {
				var b = [];
				var c = [];
				var d = [];
				var e = 0;
				for (var i in O) {
					e += O[i]
				}
				var f = a.length > 1 ? a.length * 10 : e / O.length;
				for (var i in O) {
					if (O[i] >= f) {
						b.push(i)
					}
				}
				if (ding) {
					var n = 0;
					var g = 0;
					for (var i in O) {
						g = n > O[i] ? g : i;
						n = n > O[i] ? n : O[i]
					}
					c['ge'] = g;
					c['shi'] = g;
					c['bai'] = g;
					c['qian'] = g;
					c['wan'] = g
				} else {
					c = this.getDanNums(a.length, b)
				}
				return c
			},
			getDanNums: function(a, b) {
				var c = Math.pow(10, a);
				var d = new Array();
				for (var i = 0; i < c; i++) {
					var e = parseInt(i).toString();
					var f = "00000";
					var g = parseInt(i).toString();
					e = f.substring(0, a - g.length) + g;
					curarr = e.split('');
					for (var j in curarr) {
						if (CommonObj.getInArray(curarr[j], b)) {
							d.push(e);
							break
						}
					}
				}
				return d
			},
			getPrizeNum: function(a) {
				var b = new Array();
				for (var c in a) {
					var d = a[c];
					if (b.length > 0) {
						var e = new Array();
						for (var i in b) {
							for (var j in d) {
								e.push(b[i].toString() + d[j])
							}
						}
						b = e;
						e = new Array()
					} else {
						b = d
					}
					d = new Array()
				}
				return b
			},
			countdata: function(a, b) {
				O = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				var c = this.setway(a);
				for (var i in b) {
					var d = b[i]['prize'].split("");
					for (var j in c) {
						O[d[c[j]]]++
					}
				}
				return O
			},
			setway: function(a) {
				var b = [];
				for (var i in a) {
					switch (a[i]) {
					case 'ge':
						b.push(4);
						break;
					case 'shi':
						b.push(3);
						break;
					case 'bai':
						b.push(2);
						break;
					case 'qian':
						b.push(1);
						break;
					case 'wan':
						b.push(0);
						break;
					case 'ding':
						b = [0, 1, 2, 3, 4];
						break
					}
				}
				return b
			},
			createmap: function(a, b) {
				$('#main').html('');
				var c = echarts.init(document.getElementById('main'));
				var d = {
					title: {
						text: a,
						subtext: '大数团队精心计算'
					},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						data: ['胆出次']
					},
					toolbox: {
						show: true,
						feature: {
							dataView: {
								show: true,
								readOnly: false
							},
							magicType: {
								show: true,
								type: ['line', 'bar']
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},
					calculable: true,
					xAxis: [{
						type: 'category',
						data: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
					}],
					yAxis: [{
						type: 'value'
					}],
					series: [{
						name: '胆出次数量',
						type: 'bar',
						data: b,
						label: {
							normal: {
								show: true,
								position: 'top',
								textStyle: {
									fontWeight: 'bolder',
								}
							}
						},
						markLine: {
							data: [{
								type: 'average',
								name: '平均值'
							}]
						}
					}]
				};
				c.setOption(d)
			},
			planShow: function(a, b, c, d, e) {
				var f = new Array();
				var g = new Array();
				var h = new Array();
				var j = 0;
				var k = 1;
				var l = 0;
				var m = 0;
				var n = 0;
				var o = 0;
				var p = TimerData.getcaipiaoname(caipiao) + "第" + recentid + "期 开【" + curPrizes.join(',') + "】";
				var q = [];
				var r = "";
				var s = 0;
				var t = [];
				for (var i in b) {
					var u = b[i]['peroid'];
					if (i == 0) {
						j = u;
						continue
					}
					u % d == 0 && f.push(u);
					h[u] = b[i]['prize'];
					var v = b[i]['prize'].split('');
					var w = 1;
					var x = '';
					var y = c;
					if (ding) {
						var z = 0;
						for (var A in a) {
							var B = 0;
							switch (a[A]) {
							case 'ge':
								B = 4;
								break;
							case 'shi':
								B = 3;
								break;
							case 'bai':
								B = 2;
								break;
							case 'qian':
								B = 1;
								break;
							case 'wan':
								B = 0;
								break
							}
							if (c[a[A]]) {
								w = 10;
								var C = c[a[A]].length;
								z += CommonObj.getInArray(v[B], c[a[A]]) == kline ? (w - C) / C : -1
							}
						}
						y = [c['ge'], c['ge'], c['ge'], c['ge'], c['ge']]
					} else {
						for (var A in a) {
							var B = 0;
							switch (a[A]) {
							case 'ge':
								B = 4;
								break;
							case 'shi':
								B = 3;
								break;
							case 'bai':
								B = 2;
								break;
							case 'qian':
								B = 1;
								break;
							case 'wan':
								B = 0;
								break
							}
							x += v[B];
							w *= 10
						}
						var C = y.length;
						var z = CommonObj.getInArray(x, y) == kline ? (w - C) / C : -1
					}
					if (k == 1) {
						r = this.getsubstr(u, 0) + '-' + this.getsubstr(u, e)
					}
					if (z > 0) {
						var D = y.join(',');
						q.push(r + "期：大数全自动智能计划 " + M + " {" + D.substring(0, 20) + (D.length > 20 ? "..." : "") + " | " + y.length + "注} " + this.getsubstr(u, 0) + "期开[" + b[i]['prize'] + "] 中√ [" + k + "]");
						s++
					}
					if (z <= 0 && k == e) {
						var D = y.join(',');
						q.push(r + "期：大数全自动智能计划 " + M + " {" + D.substring(0, 20) + (D.length > 20 ? "..." : "") + " | " + y.length + "注} " + this.getsubstr(u, 0) + "期开[" + b[i]['prize'] + "] 挂× [" + k + "]")
					}
					if (this.getMethodWay(k, e, N, z)) {
						j = u;
						k = 1
					} else {
						j = j == 0 ? u : j;
						k++
					}
				}
				if (k == 1 && z < 0) {
					P = this.smartplan(way);
					Q = Math.ceil(Math.random() * 5)
				}
				t = ding == true ? [P['ge'], P['ge'], P['ge'], P['ge'], P['ge']] : P;
				var E = k == 1 ? (this.getsubstr(nextId, 0) + '-' + this.getsubstr(nextId, e)) : r;
				p += z > 0 ? "中" : "挂";
				p += " 计划进度：【 " + k + " 】 当前投注：</br></br>";
				var F = t.join(',');
				p += "第" + E + "期： 大数全自动智能计划 " + M + " 『" + (F.length > 100 ? "</br><textarea class='span4'>" + F + "</textarea></br>" : F) + "』 {" + t.length + "注} 等.. [" + k + "] </br></br></br>";
				p += "¤ 当前战绩：共" + q.length + "个计划，中" + s + "个，准确率：" + parseFloat(s / q.length * 100).toFixed(2) + "%</br>";
				p += "</br></br>★ ---------------</br>";
				p += q.reverse().join('</br>');
				p += "</br></br></br>==================================";
				L.html(p)
			},
			prizesData: function(a, b, c, d, e) {
				var f = new Array();
				var g = new Array();
				var h = new Array();
				var j = 0;
				var k = 1;
				var l = 0;
				var m = 0;
				var n = 0;
				var o = 0;
				for (var i in b) {
					var p = b[i]['peroid'];
					if (i == 0) {
						j = p;
						continue
					}
					p % d == 0 && f.push(p);
					h[p] = b[i]['prize'];
					var q = b[i]['prize'].split('');
					var r = 1;
					var s = '';
					var t = c;
					if (ding) {
						var u = 0;
						for (var v in a) {
							var w = 0;
							switch (a[v]) {
							case 'ge':
								w = 4;
								break;
							case 'shi':
								w = 3;
								break;
							case 'bai':
								w = 2;
								break;
							case 'qian':
								w = 1;
								break;
							case 'wan':
								w = 0;
								break
							}
							if (c[a[v]]) {
								r = 10;
								var x = c[a[v]].length;
								u += CommonObj.getInArray(q[w], c[a[v]]) == kline ? (r - x) / x : -1
							}
						}
					} else {
						for (var v in a) {
							var w = 0;
							switch (a[v]) {
							case 'ge':
								w = 4;
								break;
							case 'shi':
								w = 3;
								break;
							case 'bai':
								w = 2;
								break;
							case 'qian':
								w = 1;
								break;
							case 'wan':
								w = 0;
								break
							}
							s += q[w];
							r *= 10
						}
						var x = t.length;
						var u = CommonObj.getInArray(s, t) == kline ? (r - x) / x : -1
					}
					if (this.getMethodWay(k, e, N, u)) {
						j = p;
						k = 1
					} else {
						j = j == 0 ? p : j;
						k++
					}
					m += u;
					n = n > m ? n : m;
					o = o < m ? o : m;
					if (p % d == 0) {
						var y = new Array(l, m, n, o);
						g.push(y);
						n = o = l = m
					}
				}
				return new Array(f, g, h, c)
			},
			getMethodWay: function(a, b, c, d) {
				var e = true;
				switch (c) {
				case "normal":
					e = a == b ? true : false;
					break;
				case "zong":
					e = (a == b || d > 0) ? true : false;
					break;
				case "gua":
					e = (a == b || d <= 0) ? true : false;
					break
				}
				return e
			},
			getsubstr: function(a, b) {
				var c = '';
				var d = TimerData.getcaipiaoflag(caipiao);
				var e = TimerData.getcaipiaolimit(caipiao);
				if (e == 0) {
					return parseInt(a) + b
				}
				if (b > 0) {
					b--;
					a = parseInt(a) + b;
					c = a.toString().slice(d);
					if (parseInt(c) > e) {
						var f = c - e;
						c = ('00000' + f).toString().slice(d)
					}
				} else {
					c = a.toString().slice(d)
				}
				return c
			},
		}
	}();
var DragonDatas = function() {
		var G = $("#startPrize");
		var H = $("#before");
		var I = $("#btnStatistic");
		var J = $(".way");
		var K = $("#tomato li");
		var L = $("#planInfo");
		var M = "个位";
		var N = 'zong';
		var O = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
		var P = [];
		var Q = 1;
		var R = 'intro';
		return {
			init: function() {
				before = H.val();
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					TimerData.getdata(caipiao);
					DansDatas.getdata(caipiao);
					$('#dropdownCaipiao').hide()
				});
				I.on('click', function() {
					DansDatas.checkvalues();
					P = [];
					Q = Math.ceil(Math.random() * 5);
					DansDatas.layout(prizesData)
				});
				J.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						ding = false;
						if (way == 'ding') {
							ding = true;
							way = ['wan', 'qian', 'bai', 'shi', 'ge']
						}
						M = $.trim($(this).html());
						Q = Math.ceil(Math.random() * 5);
						DansDatas.checkvalues();
						P = [];
						DansDatas.layout(prizesData)
					}
				});
				K.on('click', function() {
					R = $(this).attr('init');
					DansDatas.checkvalues();
					DansDatas.klinemap(prizesData)
				});
				Q = Math.ceil(Math.random() * 5);
				this.getdata(caipiao)
			},
			checkvalues: function() {
				if (recentid != G.val() || before != H.val()) {
					recentid = parseInt(G.val());
					before = parseInt(H.val());
					DansDatas.getdata(caipiao)
				}
				return
			},
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = $('.tab-content .red').html().replace(/(^\s*)|(\s*$)/g, "") + " - " + before + "期胆出次统计";
					var c = this.countdata(way, a.prizes);
					this.createmap(b, c);
					this.klinemap(a)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			klinemap: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					P = P.length > 0 ? P : this.smartplan(way);
					switch (R) {
					case "intro":
						$("#tab_2").hide();
						this.planShow(way, prizes, P, 1, Q);
						break;
					case "kline":
						$("#tab_2").show();
						var b = this.prizesData(way, prizes, P, 1, Q);
						var c = "klinemap";
						KlineMaps.createmap('', b, c);
						break;
					case "search":
						$("#tab_2").hide();
						break
					}
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			getdata: function(b) {
				recentid = G.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							DansDatas.layout(a)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			smartplan: function(a) {
				var b = [];
				var c = [];
				var d = [];
				var e = 0;
				for (var i in O) {
					e += O[i]
				}
				var f = a.length > 1 ? a.length * 10 : e / O.length;
				for (var i in O) {
					if (O[i] >= f) {
						b.push(i)
					}
				}
				if (ding) {
					var n = 0;
					var g = 0;
					for (var i in O) {
						g = n > O[i] ? g : i;
						n = n > O[i] ? n : O[i]
					}
					c['ge'] = g;
					c['shi'] = g;
					c['bai'] = g;
					c['qian'] = g;
					c['wan'] = g
				} else {
					c = this.getDanNums(a.length, b)
				}
				return c
			},
			getDanNums: function(a, b) {
				var c = Math.pow(10, a);
				var d = new Array();
				for (var i = 0; i < c; i++) {
					var e = parseInt(i).toString();
					var f = "00000";
					var g = parseInt(i).toString();
					e = f.substring(0, a - g.length) + g;
					curarr = e.split('');
					for (var j in curarr) {
						if (CommonObj.getInArray(curarr[j], b)) {
							d.push(e);
							break
						}
					}
				}
				return d
			},
			getPrizeNum: function(a) {
				var b = new Array();
				for (var c in a) {
					var d = a[c];
					if (b.length > 0) {
						var e = new Array();
						for (var i in b) {
							for (var j in d) {
								e.push(b[i].toString() + d[j])
							}
						}
						b = e;
						e = new Array()
					} else {
						b = d
					}
					d = new Array()
				}
				return b
			},
			countdata: function(a, b) {
				O = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				var c = this.setway(a);
				for (var i in b) {
					var d = b[i]['prize'].split("");
					for (var j in c) {
						O[d[c[j]]]++
					}
				}
				return O
			},
			setway: function(a) {
				var b = [];
				for (var i in a) {
					switch (a[i]) {
					case 'ge':
						b.push(4);
						break;
					case 'shi':
						b.push(3);
						break;
					case 'bai':
						b.push(2);
						break;
					case 'qian':
						b.push(1);
						break;
					case 'wan':
						b.push(0);
						break;
					case 'ding':
						b = [0, 1, 2, 3, 4];
						break
					}
				}
				return b
			},
			createmap: function(a, b) {
				$('#main').html('');
				var c = echarts.init(document.getElementById('main'));
				var d = {
					title: {
						text: a,
						subtext: '大数团队精心计算'
					},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						data: ['胆出次']
					},
					toolbox: {
						show: true,
						feature: {
							dataView: {
								show: true,
								readOnly: false
							},
							magicType: {
								show: true,
								type: ['line', 'bar']
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},
					calculable: true,
					xAxis: [{
						type: 'category',
						data: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
					}],
					yAxis: [{
						type: 'value'
					}],
					series: [{
						name: '胆出次数量',
						type: 'bar',
						data: b,
						label: {
							normal: {
								show: true,
								position: 'top',
								textStyle: {
									fontWeight: 'bolder',
								}
							}
						},
						markLine: {
							data: [{
								type: 'average',
								name: '平均值'
							}]
						}
					}]
				};
				c.setOption(d)
			},
			planShow: function(a, b, c, d, e) {
				var f = new Array();
				var g = new Array();
				var h = new Array();
				var j = 0;
				var k = 1;
				var l = 0;
				var m = 0;
				var n = 0;
				var o = 0;
				var p = TimerData.getcaipiaoname(caipiao) + "第" + recentid + "期 开【" + curPrizes.join(',') + "】";
				var q = [];
				var r = "";
				var s = 0;
				var t = [];
				for (var i in b) {
					var u = b[i]['peroid'];
					if (i == 0) {
						j = u;
						continue
					}
					u % d == 0 && f.push(u);
					h[u] = b[i]['prize'];
					var v = b[i]['prize'].split('');
					var w = 1;
					var x = '';
					var y = c;
					if (ding) {
						var z = 0;
						for (var A in a) {
							var B = 0;
							switch (a[A]) {
							case 'ge':
								B = 4;
								break;
							case 'shi':
								B = 3;
								break;
							case 'bai':
								B = 2;
								break;
							case 'qian':
								B = 1;
								break;
							case 'wan':
								B = 0;
								break
							}
							if (c[a[A]]) {
								w = 10;
								var C = c[a[A]].length;
								z += CommonObj.getInArray(v[B], c[a[A]]) == kline ? (w - C) / C : -1
							}
						}
						y = [c['ge'], c['ge'], c['ge'], c['ge'], c['ge']]
					} else {
						for (var A in a) {
							var B = 0;
							switch (a[A]) {
							case 'ge':
								B = 4;
								break;
							case 'shi':
								B = 3;
								break;
							case 'bai':
								B = 2;
								break;
							case 'qian':
								B = 1;
								break;
							case 'wan':
								B = 0;
								break
							}
							x += v[B];
							w *= 10
						}
						var C = y.length;
						var z = CommonObj.getInArray(x, y) == kline ? (w - C) / C : -1
					}
					if (k == 1) {
						r = this.getsubstr(u, 0) + '-' + this.getsubstr(u, e)
					}
					if (z > 0) {
						var D = y.join(',');
						q.push(r + "期：大数全自动智能计划 " + M + " {" + D.substring(0, 20) + (D.length > 20 ? "..." : "") + " | " + y.length + "注} " + this.getsubstr(u, 0) + "期开[" + b[i]['prize'] + "] 中√ [" + k + "]");
						s++
					}
					if (z <= 0 && k == e) {
						var D = y.join(',');
						q.push(r + "期：大数全自动智能计划 " + M + " {" + D.substring(0, 20) + (D.length > 20 ? "..." : "") + " | " + y.length + "注} " + this.getsubstr(u, 0) + "期开[" + b[i]['prize'] + "] 挂× [" + k + "]")
					}
					if (this.getMethodWay(k, e, N, z)) {
						j = u;
						k = 1
					} else {
						j = j == 0 ? u : j;
						k++
					}
				}
				if (k == 1 && z < 0) {
					P = this.smartplan(way);
					Q = Math.ceil(Math.random() * 5)
				}
				t = ding == true ? [P['ge'], P['ge'], P['ge'], P['ge'], P['ge']] : P;
				var E = k == 1 ? (this.getsubstr(nextId, 0) + '-' + this.getsubstr(nextId, e)) : r;
				p += z > 0 ? "中" : "挂";
				p += " 计划进度：【 " + k + " 】 当前投注：</br></br>";
				var F = t.join(',');
				p += "第" + E + "期： 大数全自动智能计划 " + M + " 『" + (F.length > 100 ? "</br><textarea class='span4'>" + F + "</textarea></br>" : F) + "』 {" + t.length + "注} 等.. [" + k + "] </br></br></br>";
				p += "¤ 当前战绩：共" + q.length + "个计划，中" + s + "个，准确率：" + parseFloat(s / q.length * 100).toFixed(2) + "%</br>";
				p += "</br></br>★ ---------------</br>";
				p += q.reverse().join('</br>');
				p += "</br></br></br>==================================";
				L.html(p)
			},
			prizesData: function(a, b, c, d, e) {
				var f = new Array();
				var g = new Array();
				var h = new Array();
				var j = 0;
				var k = 1;
				var l = 0;
				var m = 0;
				var n = 0;
				var o = 0;
				for (var i in b) {
					var p = b[i]['peroid'];
					if (i == 0) {
						j = p;
						continue
					}
					p % d == 0 && f.push(p);
					h[p] = b[i]['prize'];
					var q = b[i]['prize'].split('');
					var r = 1;
					var s = '';
					var t = c;
					if (ding) {
						var u = 0;
						for (var v in a) {
							var w = 0;
							switch (a[v]) {
							case 'ge':
								w = 4;
								break;
							case 'shi':
								w = 3;
								break;
							case 'bai':
								w = 2;
								break;
							case 'qian':
								w = 1;
								break;
							case 'wan':
								w = 0;
								break
							}
							if (c[a[v]]) {
								r = 10;
								var x = c[a[v]].length;
								u += CommonObj.getInArray(q[w], c[a[v]]) == kline ? (r - x) / x : -1
							}
						}
					} else {
						for (var v in a) {
							var w = 0;
							switch (a[v]) {
							case 'ge':
								w = 4;
								break;
							case 'shi':
								w = 3;
								break;
							case 'bai':
								w = 2;
								break;
							case 'qian':
								w = 1;
								break;
							case 'wan':
								w = 0;
								break
							}
							s += q[w];
							r *= 10
						}
						var x = t.length;
						var u = CommonObj.getInArray(s, t) == kline ? (r - x) / x : -1
					}
					if (this.getMethodWay(k, e, N, u)) {
						j = p;
						k = 1
					} else {
						j = j == 0 ? p : j;
						k++
					}
					m += u;
					n = n > m ? n : m;
					o = o < m ? o : m;
					if (p % d == 0) {
						var y = new Array(l, m, n, o);
						g.push(y);
						n = o = l = m
					}
				}
				return new Array(f, g, h, c)
			},
			getMethodWay: function(a, b, c, d) {
				var e = true;
				switch (c) {
				case "normal":
					e = a == b ? true : false;
					break;
				case "zong":
					e = (a == b || d > 0) ? true : false;
					break;
				case "gua":
					e = (a == b || d <= 0) ? true : false;
					break
				}
				return e
			},
			getsubstr: function(a, b) {
				var c = '';
				var d = TimerData.getcaipiaoflag(caipiao);
				var e = TimerData.getcaipiaolimit(caipiao);
				if (e == 0) {
					return parseInt(a) + b
				}
				if (b > 0) {
					b--;
					a = parseInt(a) + b;
					c = a.toString().slice(d);
					if (parseInt(c) > e) {
						var f = c - e;
						c = ('00000' + f).toString().slice(d)
					}
				} else {
					c = a.toString().slice(d)
				}
				return c
			},
		}
	}();
var KlineData = function() {
		var z = $("#startPrize");
		var A = $("#before");
		var B = $("#btnStatistic");
		var C = $(".way");
		var D = $("#hotYilou > .btn");
		var E = $("#statistic");
		var F = $("#method");
		var G = $(".number");
		var H = $(".option > .btn");
		return {
			init: function() {
				A.val(before);
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					TimerData.getdata(caipiao);
					$('#dropdownCaipiao').hide()
				});
				B.on('click', function() {
					KlineData.checkvalues();
					KlineData.mapshow(prizesData)
				});
				C.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						act = $(this).attr('act');
						F.html($(this).html());
						KlineData.selectAct(act)
					}
				});
				D.on('click', function() {
					if (!$(this).hasClass('red')) {
						$('#hotYilou .red').removeClass('red');
						$(this).addClass('red');
						if ($(this).attr('init') == 'hot') {
							stat = '冷热';
							$('.hot').show();
							$('.yilou').hide()
						} else {
							stat = '遗漏';
							$('.hot').hide();
							$('.yilou').show()
						}
						E.html(stat + '选号')
					}
				});
				G.on('click', function() {
					if ($(this).hasClass('button-default')) {
						$(this).addClass('button-danger').removeClass('button-default')
					} else {
						$(this).removeClass('button-danger').addClass('button-default')
					}
				});
				H.on('click', function() {
					if (!$(this).hasClass('red')) {
						$(this).parent().find('.red').removeClass('red');
						if ($(this).attr('init') != 'clear') {
							$(this).addClass('red')
						}
						KlineData.getAddByButton($(this), $(this).attr('init'))
					} else {
						KlineData.getRemoveByButton($(this), $(this).attr('init'));
						$(this).removeClass('red')
					}
				});
				this.getdata(caipiao)
			},
			getRemoveByButton: function(b, c) {
				switch (c) {
				case 'all':
					b.parent().parent().find('a.number').each(function() {
						if ($(this).hasClass('button-danger')) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'fan':
					b.parent().parent().find('a.number').each(function() {
						if ($(this).hasClass('button-danger')) {
							$(this).removeClass('button-danger').addClass('button-default')
						} else {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'big':
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (a > 4) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'small':
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (a < 5) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'zhi':
					var d = "12357";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'he':
					var d = "04689";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'single':
					var d = "13579";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'double':
					var d = "02468";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'yin':
					var d = "03467";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'yang':
					var d = "12589";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).removeClass('button-danger').addClass('button-default')
						}
					});
					break;
				case 'clear':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					break;
				default:
					break
				}
			},
			getAddByButton: function(b, c) {
				switch (c) {
				case 'all':
					b.parent().parent().find('a.number').each(function() {
						if (!$(this).hasClass('button-danger')) {
							$(this).removeClass('button-default').addClass('button-danger')
						}
					});
					break;
				case 'fan':
					b.parent().parent().find('a.number').each(function() {
						if ($(this).hasClass('button-danger')) {
							$(this).removeClass('button-danger').addClass('button-default')
						} else {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'big':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (a > 4) {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'small':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (a < 5) {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'zhi':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					var d = "12357";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'he':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					var d = "04689";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'single':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					var d = "13579";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'double':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					var d = "02468";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'yin':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					var d = "03467";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'yang':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					var d = "12589";
					b.parent().parent().find('a.number').each(function() {
						var a = parseInt($(this).text());
						if (d.match(a)) {
							$(this).addClass('button-danger').removeClass('button-default')
						}
					});
					break;
				case 'clear':
					b.parent().parent().find('a.number').removeClass('button-danger').addClass('button-default');
					break;
				default:
					break
				}
			},
			checkvalues: function() {
				if (recentid != z.val() || before != A.val()) {
					recentid = parseInt(z.val());
					before = parseInt(A.val());
					KlineData.getdata(caipiao)
				}
				return
			},
			selectAct: function(a) {
				$(".disk").hide();
				if (a == 'circle' || a == 'dan') {
					for (var i in way) {
						$("#" + way[i]).show()
					}
					$("#hotYilou").show();
					E.show()
				} else {
					$("#" + a).show();
					$("#hotYilou").hide();
					E.hide();
					stat = '特殊'
				}
				return
			},
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = $('.tab-content .red').html().replace(/(^\s*)|(\s*$)/g, "") + " - " + stat + "选号玩法K线趋势图";
					$('#title').html(b);
					var c = this.getInput(act);
					var d = this.prizesData(way, prizes, c, 1);
					KlineMaps.createmap('', d, 'klinemap');
					var e = d[2];
					this.getHotStatistic(e, 10);
					this.getYilouStatistic(e, 50);
					var f = "大数计划 " + c.length + "注</br>第" + nextId + "期的投注号为：</br><textarea class='bettings span12'>" + c.join(' ') + "</textarea>";
					$("#betting").html(f)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			mapshow: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = this.getInput(act);
					var c = this.prizesData(way, prizes, b, 1);
					KlineMaps.createmap('', c, 'klinemap');
					var d = "大数计划 " + b.length + "注</br>第" + nextId + "期的投注号为：</br><textarea class='bettings span12'>" + b.join(' ') + "</textarea>";
					$("#betting").html(d)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			getdata: function(b) {
				recentid = parseInt(z.val());
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							KlineData.layout(a)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			getInput: function(a) {
				var b = new Array();
				ding = false;
				switch (a) {
				case 'circle':
					var n = new Array();
					for (var c in way) {
						var d = new Array();
						$("#" + way[c] + "  a.number").each(function() {
							if ($(this).hasClass('button-danger')) {
								var i = parseInt($(this).text());
								d.push(i)
							}
						});
						if (d.length > 0) {
							n[way[c]] = d
						}
					}
					b = this.getPrizeNum(n);
					break;
				case 'dan':
					var n = new Array();
					for (var c in way) {
						var d = new Array();
						$("#" + way[c] + "  a.number").each(function() {
							if ($(this).hasClass('button-danger')) {
								var i = parseInt($(this).text());
								d.push(i)
							}
						});
						if (d.length > 0) {
							n[way[c]] = d
						}
					}
					ding = true;
					b = n;
					break;
				case 'dudan':
					var n = new Array();
					$("#dudan  a.number").each(function() {
						if ($(this).hasClass('button-danger')) {
							var i = parseInt($(this).text());
							n.push(i)
						}
					});
					ding = true;
					b = new Array();
					b['ge'] = n;
					b['shi'] = n;
					b['bai'] = n;
					b['qian'] = n;
					b['wan'] = n;
					break;
				case 'hand':
					var n = $('#prizeNum').val();
					n = n.split(/[\s|\.,]+/);
					b = n ? n : b;
					break;
				case 'kua':
					var n = new Array();
					$("#kua  a.number").each(function() {
						if ($(this).hasClass('button-danger')) {
							var i = parseInt($(this).text());
							n.push(i)
						}
					});
					if (n.length <= 0) {
						return
					}
					b = this.getKuaNum(n);
					break;
				case 'zusan':
					var n = new Array();
					$("#zusan  a.number").each(function() {
						if ($(this).hasClass('button-danger')) {
							var i = parseInt($(this).text());
							n.push(i)
						}
					});
					if (n.length <= 0) {
						return
					}
					b = this.getZusanNum(n);
					break;
				case 'zuliu':
					var n = new Array();
					$("#zuliu  a.number").each(function() {
						if ($(this).hasClass('button-danger')) {
							var i = parseInt($(this).text());
							n.push(i)
						}
					});
					if (n.length <= 0) {
						return
					}
					b = this.getZuliuNum(n);
					break;
				case 'baodian':
					var n = new Array();
					$("#baodian  a.number").each(function() {
						if ($(this).hasClass('button-danger')) {
							var i = parseInt($(this).text());
							n.push(i)
						}
					});
					if (n.length <= 0) {
						return
					}
					b = this.getBaodianNum(n);
					break;
				default:
					break
				}
				return b
			},
			getPrizeNum: function(a) {
				var b = new Array();
				for (var c in a) {
					var d = a[c];
					if (b.length > 0) {
						var e = new Array();
						for (var i in b) {
							for (var j in d) {
								e.push(b[i].toString() + d[j])
							}
						}
						b = e;
						e = new Array()
					} else {
						b = d
					}
					d = new Array()
				}
				return b
			},
			prizesData: function(a, b, c, d) {
				var e = new Array();
				var f = new Array();
				var g = new Array();
				var h = 0;
				var j = 1;
				var k = 0;
				var l = 0;
				var m = 0;
				var n = 0;
				for (var i in b) {
					var o = b[i]['peroid'];
					o % d == 0 && e.push(o);
					g[o] = b[i]['prize'];
					var p = b[i]['prize'].split('');
					var q = 1;
					var r = '';
					if (ding) {
						var s = 0;
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							if (c[a[t]]) {
								q = 10;
								var v = c[a[t]].length;
								s += this.getInArray(p[u], c[a[t]]) == kline ? (q - v) / v : -1
							}
						}
					} else {
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							r += p[u];
							q *= 10
						}
						if (j == plan) {
							h = o;
							j = 1
						} else {
							h = h == 0 ? o : h;
							j++
						}
						var w = c;
						if (act == 'methodin') {
							w = c[h];
							c[x] = c[h]
						}
						var v = w.length;
						var s = this.getInArray(r, w) == kline ? (q - v) / v : -1
					}
					l += s;
					m = m > l ? m : l;
					n = n < l ? n : l;
					if (o % d == 0) {
						var x = new Array(k, l, m, n);
						f.push(x);
						m = n = k = l
					}
				}
				return new Array(e, f, g, c)
			},
			getInArray: function(a, b) {
				for (var i in b) {
					if (a == b[i]) {
						return true
					}
				}
				return false
			},
			getKuaNum: function(a) {
				var b = new Array();
				for (var i = 0; i < 10; i++) {
					for (var j = 0; j < 10; j++) {
						var c = Math.abs(i - j);
						if (this.getInArray(c, a)) {
							b.push(i.toString() + j.toString())
						}
					}
				}
				return b
			},
			getZusanNum: function(a) {
				var b = new Array();
				for (var i = 0; i < 10; i++) {
					for (var j = 0; j < 10; j++) {
						for (var k = 0; k < 10; k++) {
							if (i == j && j == k && i == k) {
								continue
							}
							if ((this.getInArray(i, a) || this.getInArray(j, a) || this.getInArray(k, a)) && (i == j || j == k || i == k)) {
								b.push(i.toString() + j.toString() + k.toString())
							}
						}
					}
				}
				return b
			},
			getZuliuNum: function(a) {
				var b = new Array();
				for (var i = 0; i < 10; i++) {
					for (var j = 0; j < 10; j++) {
						for (var k = 0; k < 10; k++) {
							if (i == j && j == k && i == k) {
								continue
							}
							if ((this.getInArray(i, a) && this.getInArray(j, a) && this.getInArray(k, a)) && (i != j && j != k && i != k)) {
								b.push(i.toString() + j.toString() + k.toString())
							}
						}
					}
				}
				return b
			},
			getBaodianNum: function(a) {
				var b = new Array();
				for (var i = 0; i < 10; i++) {
					for (var j = 0; j < 10; j++) {
						for (var k = 0; k < 10; k++) {
							var c = parseInt(i + j + k);
							if (this.getInArray(c, a)) {
								b.push(i.toString() + j.toString() + k.toString())
							}
						}
					}
				}
				return b
			},
			getHotStatistic: function(a, b) {
				var c = a;
				var d = [];
				var e = parseInt(c.length) - 1;
				d['ge'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				d['shi'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				d['bai'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				d['qian'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				d['wan'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				for (var i = 0; i < b; i++) {
					var f = c[e - i];
					if (f) {
						d['ge'][f[4]] += 1;
						d['shi'][f[3]] += 1;
						d['bai'][f[2]] += 1;
						d['qian'][f[1]] += 1;
						d['wan'][f[0]] += 1
					}
				}
				for (var g in d) {
					for (var j in d[g]) {
						var k = parseInt(j) + 1;
						$("#" + g + " .hot:eq(" + k + ")").html(d[g][j]).removeClass('red').removeClass('blue');
						$("#" + g + " .number:eq(" + j + ")").removeClass('button-hot').removeClass('button-cold');
						if (d[g][j] == 0) {
							$("#" + g + " .hot:eq(" + k + ")").addClass('blue');
							$("#" + g + " .number:eq(" + j + ")").addClass('button-cold')
						} else if (d[g][j] > 2) {
							$("#" + g + " .hot:eq(" + k + ")").addClass('red');
							$("#" + g + " .number:eq(" + j + ")").addClass('button-hot')
						}
					}
				}
				return d
			},
			getYilouStatistic: function(a, b) {
				var c = a;
				var d = [];
				var e = parseInt(c.length) - 1;
				d['ge'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				d['shi'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				d['bai'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				d['qian'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				d['wan'] = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
				for (var i = 0; i < b; i++) {
					for (var x in d) {
						for (var y in d[x]) {
							d[x][y]++
						}
					}
					var f = c[e - b + i];
					if (f) {
						d['ge'][f[4]] = 0;
						d['shi'][f[3]] = 0;
						d['bai'][f[2]] = 0;
						d['qian'][f[1]] = 0;
						d['wan'][f[0]] = 0
					}
				}
				for (var g in d) {
					for (var j in d[g]) {
						var k = parseInt(j) + 1;
						$("#" + g + " .yilou:eq(" + k + ")").html(d[g][j])
					}
				}
				return d
			},
		}
	}();
var DudanDatas = function() {
		var y = $("#startPrize");
		var z = $("#before");
		var A = $("#btnStatistic");
		var B = $(".way");
		var C = $('#morebox');
		var D = $('#main');
		var E = $('#betting');
		return {
			init: function() {
				before = z.val();
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					C.show();
					D.hide();
					E.html('');
					TimerData.getdata(caipiao);
					$('#dropdownCaipiao').hide()
				});
				A.on('click', function() {
					C.show();
					D.hide();
					E.html('');
					DudanDatas.checkvalues();
					DudanDatas.layout(prizesData)
				});
				B.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						ding = false;
						if (way == 'ding') {
							ding = true
						}
						C.show();
						D.hide();
						E.html('');
						DudanDatas.checkvalues();
						DudanDatas.layout(prizesData)
					}
				});
				this.getdata(caipiao)
			},
			checkvalues: function() {
				if (recentid != y.val() || before != z.val()) {
					recentid = parseInt(y.val());
					before = parseInt(z.val());
					DudanDatas.getdata(caipiao)
				}
				return
			},
			gettemplate: function() {
				var a = '';
				for (var i = 0; i < 10; i++) {
					a += '<li init="' + i + '"><div id="main' + i + '"></div></li>'
				}
				return a
			},
			getbox: function() {
				$("ul.morebox").html(this.gettemplate());
				var b = $("ul.morebox").width();
				var c = b > 1000 ? parseFloat(b / 5) - 4 : (b > 600 ? parseFloat(b / 2) - 4 : b);
				$("ul.morebox > li").width(c).hover(function() {
					$(this).css('border-color', '#888')
				}, function() {
					$(this).css('border-color', '#000')
				}).on('click', function() {
					var a = parseInt($(this).attr('init'));
					C.hide();
					D.show();
					bigAuto = content[a];
					DudanDatas.getbigdata(caipiao, content[a])
				})
			},
			bigmap: function(a, b) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var c = new Array();
					if (ding) {
						way = ['ge', 'shi', 'bai', 'qian', 'wan'];
						c['ge'] = b;
						c['shi'] = b;
						c['bai'] = b;
						c['qian'] = b;
						c['wan'] = b
					} else {
						c = b
					}
					var d = this.prizesData(way, prizes, c, 1);
					var e = "main";
					KlineMaps.createmap('', d, e);
					var f = d[2];
					var g = $("#nextId").html();
					var h = "大数计划 " + c.length + "注</br>第" + g + "期的投注号为：</br><textarea class='bettings span12'>" + c.join(' ') + "</textarea>";
					$("#betting").html(h)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					this.getbox();
					for (var i = 0; i < 10; i++) {
						var b = $('.tab-content .red').html().replace(/(^\s*)|(\s*$)/g, "") + " 胆码【" + i + "】图";
						var c = new Array();
						if (ding) {
							way = ['ge', 'shi', 'bai', 'qian', 'wan'];
							c['ge'] = [i];
							c['shi'] = [i];
							c['bai'] = [i];
							c['qian'] = [i];
							c['wan'] = [i];
							content[i] = [i]
						} else {
							c = this.getFromDan(way, i);
							content[i] = c
						}
						var d = this.prizesData(way, prizes, c, 1);
						var e = "main" + i;
						KlineMaps.createmap(b, d, e);
						var f = d[2]
					}
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			getdata: function(b) {
				recentid = y.val();
				var c = bigAuto.length > 0 && before < 300 ? 300 : before;
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + c,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							DudanDatas.layout(a);
							if (bigAuto.length > 0) {
								DudanDatas.bigmap(a, bigAuto)
							}
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			getbigdata: function(b, c) {
				recentid = y.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + 300,
					dataType: "json",
					success: function(a) {
						if (a) {
							DudanDatas.bigmap(a, c)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			getFromDan: function(a, b) {
				var c = new Array();
				c.push(b);
				var d = a.length;
				var e = Math.pow(10, d);
				var f = new Array();
				for (var i = 0; i < e; i++) {
					var g = parseInt(i).toString();
					var h = "00000";
					var k = parseInt(i).toString();
					g = h.substring(0, d - k.length) + k;
					curarr = g.split('');
					for (var j in curarr) {
						if (this.getInArray(curarr[j], c)) {
							f.push(g);
							break
						}
					}
				}
				return f
			},
			prizesData: function(a, b, c, d) {
				var e = new Array();
				var f = new Array();
				var g = new Array();
				var h = 0;
				var j = 1;
				var k = 0;
				var l = 0;
				var m = 0;
				var n = 0;
				for (var i in b) {
					var o = b[i]['peroid'];
					o % d == 0 && e.push(o);
					g[o] = b[i]['prize'];
					var p = b[i]['prize'].split('');
					var q = 1;
					var r = '';
					if (ding) {
						var s = 0;
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							if (c[a[t]]) {
								q = 10;
								var v = c[a[t]].length;
								s += this.getInArray(p[u], c[a[t]]) ? (q - v) / v : -1
							}
						}
					} else {
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							r += p[u];
							q *= 10
						}
						if (j == plan) {
							h = o;
							j = 1
						} else {
							h = h == 0 ? o : h;
							j++
						}
						var w = c;
						var v = w.length;
						var s = this.getInArray(r, w) ? (q - v) / v : -1
					}
					l += s;
					m = m > l ? m : l;
					n = n < l ? n : l;
					if (o % d == 0) {
						var x = new Array(k, l, m, n);
						f.push(x);
						m = n = k = l
					}
				}
				return new Array(e, f, g, c)
			},
			getInArray: function(a, b) {
				for (var i in b) {
					if (a == b[i]) {
						return true
					}
				}
				return false
			},
		}
	}();
var RandomDatas = function() {
		var y = $("#startPrize");
		var z = $("#before");
		var A = $("#btnStatistic");
		var B = $(".way");
		var C = $('#morebox');
		var D = $('#main');
		var E = $('#betting');
		var F = $('#randomNum');
		var G = $('#randomlock');
		var H = $('#randombox');
		var I = $('#storeNums');
		return {
			init: function() {
				before = z.val();
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					C.show();
					D.hide();
					bigAuto = [];
					before = z.val();
					E.html('');
					TimerData.getdata(caipiao);
					$('#dropdownCaipiao').hide();
                    //F.click();
				});
				A.on('click', function() {
					C.show();
					D.hide();
					E.html('');
					bigAuto = [];
					before = parseInt(z.val());
					RandomDatas.getdata(caipiao)
				});
				B.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						ding = false;
						if (way == 'ding') {
							ding = true
						}
						storeNums = new Array()
					}
				});
                var calCnt = 1;

                F.on('click', function() {
					dataMatched = false;
					RandomDatas.getRandomNums();
					C.show();
					D.hide();
					E.html('');
					bigAuto = [];
					before = parseInt(z.val());
					RandomDatas.getdata(caipiao);

                    setTimeout(function () {
                        while(!dataMatched&&calCnt <= 1200){
                            RandomDatas.getRandomNums();
                            var str = ".randombox #rand1";
                            console.log($(str).html());
                            console.log(calCnt);
                            RandomDatas.getlocaldata();
                            calCnt++;
                            /* dataMatched = false;
                             RandomDatas.getRandomNums();
                             C.show();
                             D.hide();
                             E.html('');
                             bigAuto = [];
                             before = parseInt(z.val());*/

                        }
                        if(!dataMatched) {
                            window.location.reload(true);
                        }
                        calCnt = 1;
                        NotifyData.checkis2Recall(caipiao);

                    }, 100);

					if(dataMatched) {
						//匹配成功，通知后台
					}
					/*if (!dataMatched) {
					    console.log(calCnt);
                        calCnt++;
                        if ( calCnt <= 10000) {
                            //F.click();
                            RandomDatas.layout(prizesData);
                            if (bigAuto.length > 0) {
                                RandomDatas.bigmap(prizesData, bigAuto)
                            }
                        } else {
                            calCnt = 1;
                        }
					} else {
					    //匹配成功，通知后台
                        calCnt = 1;
                    }*/
				   
				});
				G.on('click', function() {
					RandomDatas.getRandomLock();
					C.show();
					D.hide();
					E.html('');
					bigAuto = [];
					before = parseInt(z.val());
					RandomDatas.getdata(caipiao)
				});
				I.on('click', function() {
					var a = '';
					for (index in storeNums) {
						var b = parseInt(index) + 1;
						a += '<tr><td>第' + b + '组</td><td>分割成' + storeNums[index].length + '个随机投注号</td><td><button type="button" class="review btn blue" data-dismiss="modal" init="' + index + '">回看趋势</button></td></tr>'
					}
					$("#caipiaopackage > tbody").html(a);
					$("#myModal").modal('show');
					$(".review").on('click', function() {
						var i = $(this).attr("init");
						before = parseInt(z.val());
						bigAuto = [];
						RandomDatas.resetRandomNums(i);
						RandomDatas.reviewmap(prizesData, i)
					})
				});
				this.getRandomNums();
				this.getdata(caipiao)
			},
			checkvalues: function() {
				if (recentid != y.val() || before != z.val()) {
					recentid = parseInt(y.val());
					before = parseInt(z.val());
					RandomDatas.getdata(caipiao)
				}
				return
			},
			gettemplate: function(a) {
				var b = '';
				for (var i = 0; i < a; i++) {
					b += '<li init="' + i + '"><div id="main' + i + '"></div></li>'
				}
				return b
			},
			getbox: function(b) {
				$("ul.morebox").html(this.gettemplate(b));
				var c = $("ul.morebox").width();
				var l = b > 5 ? 5 : b;
				var d = c > 1000 ? parseFloat(c / l) - 4 : (c > 600 ? parseFloat(c / 2) - 4 : c);
				$("ul.morebox > li").width(d).hover(function() {
					$(this).css('border-color', '#888')
				}, function() {
					$(this).css('border-color', '#000')
				}).on('click', function() {
					var a = parseInt($(this).attr('init'));
					C.hide();
					D.show();
					bigAuto = content[a];
					RandomDatas.getbigdata(caipiao, content[a])
				})
			},
			reviewmap: function(a, b) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var c = storeNums[b].length;
					this.getbox(c);
					for (var i = 0; i < c; i++) {
						var d = $('.tab-content .red').html().replace(/(^\s*)|(\s*$)/g, "") + " 随机王第【" + i + "】组图";
						var e = storeNums[b][i];
						var f = this.prizesData(way, prizes, e, 1);
						var g = "main" + i;
						KlineMaps.createmap(d, f, g)
					}
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			bigmap: function(a, b) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var c = new Array();
					if (ding) {
						way = ['ge', 'shi', 'bai', 'qian', 'wan'];
						c['ge'] = b;
						c['shi'] = b;
						c['bai'] = b;
						c['qian'] = b;
						c['wan'] = b
					} else {
						c = b
					}
					var d = this.prizesData(way, prizes, c, 1);
					var e = "main";
					KlineMaps.createmap('', d, e);
					var f = d[2];
					var g = $("#nextId").html();
					var h = "大数计划 " + c.length + "注</br>第" + g + "期的投注号为：</br><textarea class='bettings span12'>" + c.join(' ') + "</textarea>";
					$("#betting").html(h)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			// {"ret":0,"caipiao":"chongqing","prizes":[{"peroid":"20170919086","prize":"55530"},{"peroid":"20170919087","prize":"68006"},{"peroid":"20170919088","prize":"53607"},{"peroid":"20170919089","prize":"22044"},{"peroid":"20170919090","prize":"10328"},{"peroid":"20170919091","prize":"40112"},{"peroid":"20170919092","prize":"77897"},{"peroid":"20170919093","prize":"97088"},{"peroid":"20170919094","prize":"93836"},{"peroid":"20170919095","prize":"99832"},{"peroid":"20170919096","prize":"59481"},{"peroid":"20170919097","prize":"79115"},{"peroid":"20170919098","prize":"73861"},{"peroid":"20170919099","prize":"41643"},{"peroid":"20170919100","prize":"71092"},{"peroid":"20170919101","prize":"13194"},{"peroid":"20170919102","prize":"41899"},{"peroid":"20170919103","prize":"15866"},{"peroid":"20170919104","prize":"80284"},{"peroid":"20170919105","prize":"40796"},{"peroid":"20170919106","prize":"48781"},{"peroid":"20170919107","prize":"92688"},{"peroid":"20170919108","prize":"99864"},{"peroid":"20170919109","prize":"19749"},{"peroid":"20170919110","prize":"94753"},{"peroid":"20170919111","prize":"06872"},{"peroid":"20170919112","prize":"22628"},{"peroid":"20170919113","prize":"34621"},{"peroid":"20170919114","prize":"05788"},{"peroid":"20170919115","prize":"09622"},{"peroid":"20170919116","prize":"11315"},{"peroid":"20170919117","prize":"13328"},{"peroid":"20170919118","prize":"27310"},{"peroid":"20170919119","prize":"22482"},{"peroid":"20170919120","prize":"67492"},{"peroid":"20170920001","prize":"57157"},{"peroid":"20170920002","prize":"05740"},{"peroid":"20170920003","prize":"90868"},{"peroid":"20170920004","prize":"50779"},{"peroid":"20170920005","prize":"58414"},{"peroid":"20170920006","prize":"29747"},{"peroid":"20170920007","prize":"31430"},{"peroid":"20170920008","prize":"61108"},{"peroid":"20170920009","prize":"95126"},{"peroid":"20170920010","prize":"07120"},{"peroid":"20170920011","prize":"45602"},{"peroid":"20170920012","prize":"46938"},{"peroid":"20170920013","prize":"39628"},{"peroid":"20170920014","prize":"60600"},{"peroid":"20170920015","prize":"38760"},{"peroid":"20170920016","prize":"06023"},{"peroid":"20170920017","prize":"54468"},{"peroid":"20170920018","prize":"26060"},{"peroid":"20170920019","prize":"95306"},{"peroid":"20170920020","prize":"55824"},{"peroid":"20170920021","prize":"25732"},{"peroid":"20170920022","prize":"22214"},{"peroid":"20170920023","prize":"54214"},{"peroid":"20170920024","prize":"47975"},{"peroid":"20170920025","prize":"23560"},{"peroid":"20170920026","prize":"68813"},{"peroid":"20170920027","prize":"86668"},{"peroid":"20170920028","prize":"47572"},{"peroid":"20170920029","prize":"65977"},{"peroid":"20170920030","prize":"55728"},{"peroid":"20170920031","prize":"53241"},{"peroid":"20170920032","prize":"03970"},{"peroid":"20170920033","prize":"26787"},{"peroid":"20170920034","prize":"08925"},{"peroid":"20170920035","prize":"71958"},{"peroid":"20170920036","prize":"38770"},{"peroid":"20170920037","prize":"09743"},{"peroid":"20170920038","prize":"93950"},{"peroid":"20170920039","prize":"38209"},{"peroid":"20170920040","prize":"18844"},{"peroid":"20170920041","prize":"25632"},{"peroid":"20170920042","prize":"49025"},{"peroid":"20170920043","prize":"65776"},{"peroid":"20170920044","prize":"81921"},{"peroid":"20170920045","prize":"46986"},{"peroid":"20170920046","prize":"44899"},{"peroid":"20170920047","prize":"68562"},{"peroid":"20170920048","prize":"83267"},{"peroid":"20170920049","prize":"16924"},{"peroid":"20170920050","prize":"03078"},{"peroid":"20170920051","prize":"18056"},{"peroid":"20170920052","prize":"14750"},{"peroid":"20170920053","prize":"36354"},{"peroid":"20170920054","prize":"54773"},{"peroid":"20170920055","prize":"41740"},{"peroid":"20170920056","prize":"00028"},{"peroid":"20170920057","prize":"43748"},{"peroid":"20170920058","prize":"09575"},{"peroid":"20170920059","prize":"43632"},{"peroid":"20170920060","prize":"78254"},{"peroid":"20170920061","prize":"88878"},{"peroid":"20170920062","prize":"83593"},{"peroid":"20170920063","prize":"73491"},{"peroid":"20170920064","prize":"71155"},{"peroid":"20170920065","prize":"62571"}],"recentid":20170920065}
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = H.children('li').length;
					this.getbox(b);
					var c = new Array();
					var result = false;

					for (var i = 0; i < b; i++) {
						var d = $('.tab-content .red').html().replace(/(^\s*)|(\s*$)/g, "") + " 随机王第【" + i + "】组图";
						var e = new Array();
						if (ding) {
							way = ['ge', 'shi', 'bai', 'qian', 'wan'];
							e['ge'] = [i];
							e['shi'] = [i];
							e['bai'] = [i];
							e['qian'] = [i];
							e['wan'] = [i];
							content[i] = [i];
							c[i] = [i]
						} else {
							e = this.getRandomTextArea(i);
							content[i] = e;
							c[i] = e
						}
						var f = this.prizesData(way, prizes, e, 1);
						var g = "main" + i;
						var itemResult = KlineMaps.createmap(d, f, g);
						var randomLength = $("#randombox li").length;
						if (itemResult && randomLength > 1) {
						    console.log(" 随机王第【" + i + "】组图");
							//alert(" 随机王第【" + i + "】组图");
								var str = ".randombox #rand" + i;
								console.log($(str).html());
                            NotifyData.betCur(a.caipiao, a.recentid, $(str).html());
						} 
						result = result || itemResult;
					}
					dataMatched = result;
					
					/*if (result) {
						window.clearInterval(randonButtonIntervalClick);
					}*/
					if (storeNums.length > storelength) {
						storeNums.pop()
					}
					storeNums.push(c)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			}, 
			getreviewdata: function(b, c) {
				recentid = y.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							RandomDatas.reviewmap(a, c)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},

			getdata: function(b) {
				recentid = y.val();
				var c = bigAuto.length > 0 && before < 300 ? 300 : before;
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + c,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;

							RandomDatas.layout(a);
							if (bigAuto.length > 0) {
								RandomDatas.bigmap(a, bigAuto)
							}
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
            getlocaldata: function() {
                recentid = y.val();
                var c = bigAuto.length > 0 && before < 300 ? 300 : before;

                RandomDatas.layout(prizesData);
                if (bigAuto.length > 0) {
                    RandomDatas.bigmap(prizesData, bigAuto)
                }
            },
			getbigdata: function(b, c) {
				recentid = y.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + 300,
					dataType: "json",
					success: function(a) {
						if (a) {
							RandomDatas.bigmap(a, c)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			getRandomTextArea: function(i) {
				var n = $('#rand' + i).val();
				n = n.split(/[\s|\.,]+/);
				return n
			},
			prizesData: function(a, b, c, d) {
				var e = new Array();
				var f = new Array();
				var g = new Array();
				var h = 0;
				var j = 1;
				var k = 0;
				var l = 0;
				var m = 0;
				var n = 0;
				for (var i in b) {
					var o = b[i]['peroid'];
					o % d == 0 && e.push(o);
					g[o] = b[i]['prize'];
					var p = b[i]['prize'].split('');
					var q = 1;
					var r = '';
					if (ding) {
						var s = 0;
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							if (c[a[t]]) {
								q = 10;
								var v = c[a[t]].length;
								s += this.getInArray(p[u], c[a[t]]) ? (q - v) / v : -1
							}
						}
					} else {
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							r += p[u];
							q *= 10
						}
						if (j == plan) {
							h = o;
							j = 1
						} else {
							h = h == 0 ? o : h;
							j++
						}
						var w = c;
						var v = w.length;
						var s = this.getInArray(r, w) ? (q - v) / v : -1
					}
					l += s;
					m = m > l ? m : l;
					n = n < l ? n : l;
					if (o % d == 0) {
						var x = new Array(k, l, m, n);
						f.push(x);
						m = n = k = l
					}
				}
				return new Array(e, f, g, c)
			},
			getInArray: function(a, b) {
				for (var i in b) {
					if (a == b[i]) {
						return true
					}
				}
				return false
			},
			generateRandom: function(a, b) {
				var c = parseInt(Math.random() * a).toString();
				var l = c.length;
				if (l < way.length) {
					var d = "00000";
					var e = c;
					c = d.substring(0, way.length - c.length) + e
				}
				for (var i = 0; i < b.length; i++) {
					if (b[i] == c) {
						return b
					}
				}
				b.push(c);
				return b
			},
			resetRandomNums: function(b) {
				var c = storeNums[b];
				var d = new Array();
				$('#randombox').html('');
				if (c.length > 0) {
					for (var j = 0; j < c.length; j++) {
						d[j] = c[j].length;
						var e = c[j];
						if (e.length == 0) {
							continue
						}
						$('#randombox').append('<li class="randombox"><textarea id="rand' + j + '">' + e.join(' ') + '</textarea><label class="checkbox-inline"><input type="checkbox" class="randomlock" name="randoms" init="' + j + '"> <span class="text-inline">锁定第 【' + j + '】组号</span></label><a class="randomap" href="javascript:void(0)" init="' + j + '"><i class="icon-bar-chart"></i>K线图</a></li>')
					}
					$('#randombox .randomap').bind('click', function() {
						var a = parseInt($(this).attr('init'));
						C.hide();
						D.show();
						RandomDatas.getbigdata(caipiao, content[a])
					});
					$('.randomlock').bind('click', function() {
						$('#randomlock').prop('disabled', false)
					})
				} else if (randoms.length == 1) {}
				$('#randkey').val(d.join(' '))
			},
			getRandomNums: function() {
				var b = Math.pow(10, way.length);
				var c = $.trim($('#randkey').val()).split(' ');
				var d = new Array();
				while (1) {
					if (d.length < b) {
						d = this.generateRandom(b, d)
					} else {
						break
					}
				}
				$('#randombox').html('');
				if (c.length > 0) {
					for (var j = 0; j < c.length; j++) {
						var e = d.splice(0, c[j]);
						if (e.length == 0) {
							continue
						}
						$('#randombox').append('<li class="randombox"><textarea id="rand' + j + '">' + e.join(' ') + '</textarea><label class="checkbox-inline"><input type="checkbox" class="randomlock" name="randoms" init="' + j + '"> <span class="text-inline">锁定第 【' + j + '】组号</span></label><a class="randomap" href="javascript:void(0)" init="' + j + '"><i class="icon-bar-chart"></i>K线图</a></li>')
					}
					$('#randombox .randomap').bind('click', function() {
						var a = parseInt($(this).attr('init'));
						C.hide();
						D.show();
						RandomDatas.getbigdata(caipiao, content[a])
					});
					$('.randomlock').bind('click', function() {
						$('#randomlock').prop('disabled', false)
					})
				} else if (c.length == 1) {}
			},
			getRandomLock: function() {
				var c = 0;
				var d = new Array();
				$('.randomlock').each(function() {
					if (!$(this).is(":checked")) {
						var k = parseInt($(this).attr('init'));
						d = d.concat($('#rand' + k).val().split(' '))
					}
				});
				c = d.length;
				var e = $.trim($('#randkey').val()).split(' ');

				function randomsort(a, b) {
					return Math.random() > .5 ? -1 : 1
				}
				var f = d.sort(randomsort);
				$('.randomlock').each(function() {
					if (!$(this).is(":checked")) {
						var k = parseInt($(this).attr('init'));
						var a = f.splice(0, e[k]).join(' ');
						$('#rand' + k).html(a)
					}
				})
			},
		}
	}();
var GodnumDatas = function() {
		var A = $("#startPrize");
		var B = $("#before");
		var C = $("#btnStatistic");
		var D = $("#btnLock");
		var E = $(".way");
		var F = $('#morebox');
		var G = $('#main');
		var H = $('#betting');
		var I = $('#resetGodNum');
		var J = $('#godkey');
		var K = $("#klinebox");
		var L = $("#yiloubox");
		var F = $("#morebox");
		var M = $("#staticbox");
		var N = $("#zuNums");
		var O = $('#storeNums');
		return {
			init: function() {
				before = B.val();
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					before = B.val();
					H.html('');
					curPrizeNum = [];
					TimerData.getdata(caipiao);
					$('#dropdownCaipiao').hide()
				});
				C.on('click', function() {
					H.html('');
					curPrizeNum = [];
					lock = false;
					D.addClass('default').removeClass('red');
					GodnumDatas.checkvalues();
					GodnumDatas.layout(prizesData)
				});
				D.on('click', function() {
					if (lock) {
						lock = false;
						$(this).addClass('default').removeClass('red')
					} else {
						lock = true;
						$(this).addClass('red').removeClass('default')
					}
				});
				E.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						ding = false;
						if (way == 'ding') {
							ding = true
						}
						curPrizeNum = [];
						storeNums = new Array();
						H.html('');
						GodnumDatas.checkvalues();
						GodnumDatas.layout(prizesData)
					}
				});
				I.on('click', function() {
					GodnumDatas.resetGodNum();
					H.html('');
					GodnumDatas.checkvalues();
					GodnumDatas.layout(prizesData)
				});
				K.on('click', function() {
					kline = true;
					GodnumDatas.layout(prizesData)
				});
				L.on('click', function() {
					kline = false;
					GodnumDatas.layout(prizesData)
				});
				F.on('click', function() {
					$('#klinemap').html('fdsgf').css('color', '#fff')
				});
				M.on('click', function() {
					$('#klinemap').html('dsafdsa').css('color', '#fff')
				});
				O.on('click', function() {
					var a = '';
					for (index in storeNums) {
						var b = parseInt(index) + 1;
						a += '<tr><td>第' + b + '组</td><td>分割成' + storeNums[index].length + '个随机投注号</td><td><button type="button" class="review btn blue" data-dismiss="modal" init="' + index + '">回看趋势</button></td></tr>'
					}
					$("#caipiaopackage > tbody").html(a);
					$("#myModal").modal('show');
					$(".review").on('click', function() {
						var i = $(this).attr("init");
						GodnumDatas.reviewmap(prizesData, i)
					})
				});
				this.getdata(caipiao)
			},
			checkvalues: function() {
				if (recentid != A.val() || before != B.val()) {
					recentid = parseInt(A.val());
					before = parseInt(B.val());
					GodnumDatas.getdata(caipiao)
				}
				return
			},
			resetGodNum: function() {
				var a = ['挂中挂挂挂挂中中', '中中挂挂挂挂中中', '挂挂挂挂挂挂挂中', '挂挂挂中中挂挂中中', '挂中挂中挂中', '中挂挂挂挂中', '中挂挂中挂挂中', '中中中中中挂挂中中挂中', '挂挂挂挂挂', '挂中中挂中', '中中挂挂挂挂中中挂挂中中挂中挂'];
				var b = parseInt(Math.random() * 10);
				J.val(a[b])
			},
			insect: function(a, b) {
				var c = 0,
					bi = 0;
				var d = new Array();
				while (c < a.length && bi < b.length) {
					if (a[c] < b[bi]) {
						c++
					} else if (a[c] > b[bi]) {
						bi++
					} else {
						d.push(a[c]);
						c++;
						bi++
					}
				}
				return d
			},
			union: function(x, y) {
				for (var j = 0; j < y.length; j++) {
					if (!this.getInArray(y[j], x)) {
						x.push(y[j])
					}
				}
				return x
			},
			reduce: function(x, y) {
				var a = new Array();
				for (var i = 0; i < x.length; i++) {
					if (!this.getInArray(x[i], y)) {
						a.push(x[i])
					}
				}
				return a
			},
			getGodNum: function(a, b, c) {
				var d = new Array();
				var e = c.split('');
				var f = e.length;
				var g = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
				var h = new Array();
				var l = new Array();
				for (var m in a) {
					h.push(a[m]['prize'])
				}
				for (var i = 0; i < f; i++) {
					var s = e.pop();
					var z = h.pop();
					var n = z.split('');
					for (var w in b) {
						var k = 0;
						switch (b[w]) {
						case 'wan':
							k = 0;
							break;
						case 'qian':
							k = 1;
							break;
						case 'bai':
							k = 2;
							break;
						case 'shi':
							k = 3;
							break;
						case 'ge':
							k = 4;
							break;
						default:
							break
						}
						if (!d[b[w]]) {
							d[b[w]] = new Array()
						}
						if (!l[b[w]]) {
							l[b[w]] = new Array()
						}
						if (s == '中') {
							d[b[w]] = this.union(d[b[w]], [n[k]])
						} else {
							l[b[w]] = this.union(l[b[w]], [n[k]])
						}
					}
				}
				var o = new Array();
				for (var j in d) {
					l[j] = this.reduce(g, l[j]);
					d[j] = this.union(d[j], l[j]);
					o[j] = this.insect(d[j], l[j])
				}
				return ding ? d : this.getPrizeNum(d)
			},
			reviewmap: function(a, b) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var c = storeNums[b];
					curPrizeNum = c;
					var d = this.prizesData(way, prizes, c, 1);
					var e = "klinemap";
					KlineMaps.createmap('', d, e);
					var f = "大数计划 " + c.length + "注</br>第" + nextId + "期的投注号为：</br><textarea class='bettings span12'>" + c.join(' ') + "</textarea>";
					$("#betting").html(f)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = [];
					if (lock) {
						b = curPrizeNum
					} else {
						var c = J.val();
						var d = parseInt(N.val());
						var b = GodMethod.getGodNum(prizes, way, c);
						b = GodMethod.getLimitsNum(b, way, d);
						if (storeNums.length > storelength) {
							storeNums.pop()
						}
						curPrizeNum = b;
						storeNums.push(b)
					}
					var e = this.prizesData(way, prizes, b, 1);
					var f = "klinemap";
					KlineMaps.createmap('', e, f);
					var g = "大数计划 " + b.length + "注</br>第" + nextId + "期的投注号为：</br><textarea class='bettings span12'>" + b.join(' ') + "</textarea>";
					$("#betting").html(g)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			getreviewdata: function(b, c) {
				recentid = A.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							GodnumDatas.reviewmap(a, c)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			getdata: function(b) {
				recentid = A.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							GodnumDatas.layout(a)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			prizesData: function(a, b, c, d) {
				var e = new Array();
				var f = new Array();
				var g = new Array();
				var h = 0;
				var j = 1;
				var k = 0;
				var l = 0;
				var m = 0;
				var n = 0;
				for (var i in b) {
					var o = b[i]['peroid'];
					o % d == 0 && e.push(o);
					g[o] = b[i]['prize'];
					var p = b[i]['prize'].split('');
					var q = 1;
					var r = '';
					if (ding) {
						var s = 0;
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							if (c[a[t]]) {
								q = 10;
								var v = c[a[t]].length;
								s += this.getInArray(p[u], c[a[t]]) == kline ? (q - v) / v : -1
							}
						}
					} else {
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							r += p[u];
							q *= 10
						}
						if (j == plan) {
							h = o;
							j = 1
						} else {
							h = h == 0 ? o : h;
							j++
						}
						var w = c;
						var v = w.length;
						var s = this.getInArray(r, w) == kline ? (q - v) / v : -1
					}
					l += s;
					m = m > l ? m : l;
					n = n < l ? n : l;
					if (o % d == 0) {
						var x = new Array(k, l, m, n);
						f.push(x);
						m = n = k = l
					}
				}
				return new Array(e, f, g, c)
			},
			getInArray: function(a, b) {
				for (var i in b) {
					if (a == b[i]) {
						return true
					}
				}
				return false
			},
			getPrizeNum: function(a) {
				var b = new Array();
				for (var c in a) {
					var d = a[c];
					if (b.length > 0) {
						var e = new Array();
						for (var i in b) {
							for (var j in d) {
								e.push(b[i].toString() + d[j])
							}
						}
						b = e;
						e = new Array()
					} else {
						b = d
					}
					d = new Array()
				}
				return b
			},
			getLimitsNum: function(a, b, c) {
				var d = new Array();
				var e = a.length;
				if (e < c) {
					var f = Math.pow(10, b.length);
					var g = new Array();
					while (1) {
						if (g.length < f) {
							g = CommonObj.generateRandom(f, g)
						} else {
							break
						}
					}
					var h = CommonObj.reduce(g, a);
					var i = a.concat(h);
					d = i.slice(0, c)
				} else {
					var i = a.sort(this.randomsort);
					d = i.slice(0, c)
				}
				return d
			},
			randomsort: function(a, b) {
				return Math.random() > .5 ? -1 : 1
			},
		}
	}();
var GodMethod = function() {
		return {
			getInArray: function(a, b) {
				for (var i in b) {
					if (a == b[i]) {
						return true
					}
				}
				return false
			},
			insect: function(a, b) {
				var c = 0,
					bi = 0;
				var d = new Array();
				while (c < a.length && bi < b.length) {
					if (a[c] < b[bi]) {
						c++
					} else if (a[c] > b[bi]) {
						bi++
					} else {
						d.push(a[c]);
						c++;
						bi++
					}
				}
				return d
			},
			union: function(x, y) {
				for (var j = 0; j < y.length; j++) {
					if (!this.getInArray(y[j], x)) {
						x.push(y[j])
					}
				}
				return x
			},
			reduce: function(x, y) {
				var a = new Array();
				for (var i = 0; i < x.length; i++) {
					if (!this.getInArray(x[i], y)) {
						a.push(x[i])
					}
				}
				return a
			},
			unique: function(a) {
				var b = new Array();
				var c = new Array();
				for (var i = 0; i < a.length; i++) {
					if (!c[a[i]] && a[i] !== '.' && a[i] !== '-') {
						b.push(a[i]);
						c[a[i]] = 1
					}
				}
				return b
			},
			getGodNum: function(a, b, c) {
				var d = new Array();
				var e = c.split('');
				var f = e.length;
				var g = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
				var h = new Array();
				var j = new Array();
				for (var l in a) {
					h.push(a[l]['prize'])
				}
				for (var i = 0; i < f; i++) {
					var s = e.pop();
					var z = h.pop();
					var m = z.split('');
					for (var w in b) {
						var k = 0;
						switch (b[w]) {
						case 'wan':
							k = 0;
							break;
						case 'qian':
							k = 1;
							break;
						case 'bai':
							k = 2;
							break;
						case 'shi':
							k = 3;
							break;
						case 'ge':
							k = 4;
							break;
						default:
							break
						}
						if (s == '中') {
							if (!d[i]) {
								d[i] = new Array()
							}
							d[i] = d[i].toString() + m[k]
						} else {
							if (!j[i]) {
								j[i] = new Array()
							}
							j[i] = j[i].toString() + m[k]
						}
					}
				}
				return new Array(this.unique(d), this.unique(j))
			},
			getLimitsNum: function(a, b, c) {
				var d = new Array();
				var e = a[0].length;
				if (e < c) {
					var f = Math.pow(10, b.length);
					var g = new Array();
					while (1) {
						if (g.length < f) {
							g = CommonObj.generateRandom(f, g)
						} else {
							break
						}
					}
					var h = CommonObj.reduce(g, a[0]);
					h = CommonObj.reduce(h, a[1]);
					var i = a[0].concat(h);
					d = i.slice(0, c)
				} else {
					var i = a[0].sort(this.randomsort);
					d = i.slice(0, c)
				}
				return d
			},
			randomsort: function(a, b) {
				return Math.random() > .5 ? -1 : 1
			},
		}
	}();
var RotateDatas = function() {
		var y = $("#startPrize");
		var z = $("#before");
		var A = $("#btnStatistic");
		var B = $("#btnSearch");
		var C = $(".way");
		var D = $('#morebox');
		var E = $('#main');
		var F = $('#betting');
		var G = $('#rotate > .btn');
		var H = $('.whirl');
		return {
			init: function() {
				before = z.val();
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					D.html('').show();
					E.hide();
					F.html('');
					bigAuto = [];
					before = parseInt(z.val());
					content = new Array();
					TimerData.getdata(caipiao);
					$('#dropdownCaipiao').hide()
				});
				A.on('click', function() {
					D.html('').show();
					E.hide();
					F.html('');
					bigAuto = [];
					recentid = parseInt(y.val());
					before = parseInt(z.val());
					RotateDatas.getdata(caipiao)
				});
				B.on('click', function() {
					D.html('').show();
					E.hide();
					F.html('');
					bigAuto = [];
					content = new Array();
					recentid = parseInt(y.val());
					before = parseInt(z.val());
					RotateDatas.getdata(caipiao)
				});
				C.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						ding = false;
						if (way == 'ding') {
							ding = true
						}
						D.html('').show();
						E.hide();
						F.html('');
						bigAuto = [];
						before = parseInt(z.val());
						content = new Array();
						RotateDatas.getdata(caipiao)
					}
				});
				H.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.whirls .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						ding = false;
						if (way == 'ding') {
							ding = true
						}
						D.html('').show();
						E.hide();
						F.html('');
						bigAuto = [];
						before = parseInt(z.val());
						content = new Array();
						RotateDatas.getdata(caipiao)
					}
				});
				G.on('click', function() {
					if (!$(this).hasClass('red')) {
						G.removeClass('red');
						$(this).addClass('red');
						var a = $(this).attr('init');
						if (a == 'bigdi') {
							$("#bigdi").show()
						} else {
							$("#bigdi").hide()
						}
						rotate = a
					}
				});
				this.getdata(caipiao)
			},
			checkvalues: function() {
				if (recentid != y.val() || before != z.val()) {
					recentid = parseInt(y.val());
					before = parseInt(z.val());
					RotateDatas.getdata(caipiao)
				}
				return
			},
			gettemplate: function(a) {
				var b = '';
				for (var i = 0; i < a; i++) {
					b += '<li init="' + i + '"><div id="main' + i + '"></div></li>'
				}
				return b
			},
			getbox: function(b) {
				$("ul.morebox").html(this.gettemplate(b));
				var c = $("ul.morebox").width();
				var l = b > 5 ? 5 : b;
				var d = c > 1000 ? parseFloat(c / l) - 4 : (c > 600 ? parseFloat(c / 2) - 4 : c);
				$("ul.morebox > li").width(d).hover(function() {
					$(this).css('border-color', '#888')
				}, function() {
					$(this).css('border-color', '#000')
				}).on('click', function() {
					var a = parseInt($(this).attr('init'));
					D.hide();
					E.html('');
					E.show();
					bigAuto = content[a];
					RotateDatas.getbigdata(caipiao, content[a])
				})
			},
			bigmap: function(a, b) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var c = new Array();
					if (ding) {
						way = ['ge', 'shi', 'bai', 'qian', 'wan'];
						c['ge'] = b;
						c['shi'] = b;
						c['bai'] = b;
						c['qian'] = b;
						c['wan'] = b
					} else {
						c = b
					}
					var d = this.prizesData(way, prizes, c, 1);
					var e = "main";
					KlineMaps.createmap('', d, e);
					var f = d[2];
					var g = $("#nextId").html();
					var h = "大数计划 " + c.length + "注</br>第" + g + "期的投注号为：</br><textarea class='bettings span12'>" + c.join(' ') + "</textarea>";
					$("#betting").html(h)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = parseInt($('.whirls .red').attr('init'));
					this.getbox(b);
					if (content.length <= 0) {
						$('#randombox').html('');
						content = this.getFromSpin(way, b)
					}
					for (var i = 0; i < b; i++) {
						var c = $('.whirls .red').html().replace(/(^\s*)|(\s*$)/g, "") + " 【" + i + "】 图";
						var d = content[i];
						var e = this.prizesData(way, prizes, d, 1);
						var f = "main" + i;
						KlineMaps.createmap(c, e, f);
						var g = e[2]
					}
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			getdata: function(b) {
				recentid = y.val();
				var c = bigAuto.length > 0 && before < 300 ? 300 : before;
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + c,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							RotateDatas.layout(a);
							if (bigAuto.length > 0) {
								RotateDatas.bigmap(a, bigAuto)
							}
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			getbigdata: function(b, c) {
				recentid = y.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + 300,
					dataType: "json",
					success: function(a) {
						if (a) {
							RotateDatas.bigmap(a, c)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			getFromSpin: function(b, c) {
				var d = Math.pow(10, b.length);
				var e = [];
				var f = new Array();
				if (rotate == 'allji') {
					while (1) {
						if (f.length < d) {
							f = CommonObj.generateRandom(d, f)
						} else {
							break
						}
					}
				} else {
					f = $("#inputNum").val().split(/[\s|\.,]+/);
					f = this.shuffle(f)
				}
				var g = f.concat();
				var h = parseInt(g.length / c);
				var i = 0;
				if (c > 0) {
					for (var j = 0; j < c; j++) {
						var k = j == parseInt(c - 1) ? parseInt(d - i) : h;
						e[j] = CommonObj.reduce(g, f.splice(0, k));
						i += k;
						if (e[j].length == 0) {
							continue
						}
						$('#randombox').append('<li class="randombox"><textarea id="rand' + j + '">' + e[j].join(' ') + '</textarea><label class="checkbox-inline"><span class="text-inline">第【' + j + '】组号   ' + e[j].length + '注</span></label><a class="randomap" href="javascript:void(0)" init="' + j + '"><i class="icon-bar-chart"></i>K线图</a></li>')
					}
					$('#randombox .randomap').bind('click', function() {
						var a = parseInt($(this).attr('init'));
						D.hide();
						E.show();
						RotateDatas.getbigdata(caipiao, content[a])
					})
				}
				return e
			},
			prizesData: function(a, b, c, d) {
				var e = new Array();
				var f = new Array();
				var g = new Array();
				var h = 0;
				var j = 1;
				var k = 0;
				var l = 0;
				var m = 0;
				var n = 0;
				for (var i in b) {
					var o = b[i]['peroid'];
					o % d == 0 && e.push(o);
					g[o] = b[i]['prize'];
					var p = b[i]['prize'].split('');
					var q = 1;
					var r = '';
					if (ding) {
						var s = 0;
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							if (c[a[t]]) {
								q = 10;
								var v = c[a[t]].length;
								s += this.getInArray(p[u], c[a[t]]) ? (q - v) / v : -1
							}
						}
					} else {
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							r += p[u];
							q *= 10
						}
						if (j == plan) {
							h = o;
							j = 1
						} else {
							h = h == 0 ? o : h;
							j++
						}
						var w = c;
						var v = w.length;
						var s = this.getInArray(r, w) ? (q - v) / v : -1
					}
					l += s;
					m = m > l ? m : l;
					n = n < l ? n : l;
					if (o % d == 0) {
						var x = new Array(k, l, m, n);
						f.push(x);
						m = n = k = l
					}
				}
				return new Array(e, f, g, c)
			},
			getInArray: function(a, b) {
				for (var i in b) {
					if (a == b[i]) {
						return true
					}
				}
				return false
			},
			shuffle: function(a) {
				var b = a.length;
				for (var i = 0; i < b - 1; i++) {
					var c = Math.floor(Math.random() * (b - i));
					var d = a[c];
					a[c] = a[b - i - 1];
					a[b - i - 1] = d
				}
				return a
			}
		}
	}();
var FormulaDatas = function() {
		var A = $("#startPrize");
		var B = $("#before");
		var C = $("#btnStatistic");
		var D = $(".way");
		var E = $("#klinebox");
		var F = $("#yiloubox");
		var G = $("#morebox");
		var H = $("#staticbox");
		var I = $("#randomMethod");
		var J = $("#method");
		var K = $("#flag");
		var L = $("#plan");
		var M = $(".methodway");
		return {
			init: function() {
				before = B.val();
				caipiaoMenu.on('click', function() {
					caipiao = $(this).attr('init');
					TimerData.setCaipiaoCookie(caipiao);
					caipiaoText.val($(this).find('a').html());
					TimerData.getdata(caipiao);
					$('#dropdownCaipiao').hide()
				});
				C.on('click', function() {
					FormulaDatas.checkvalues();
					FormulaDatas.layout(prizesData)
				});
				D.on('click', function() {
					if ($(this).hasClass('black')) {
						$('.tab-content .red').addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						way = $(this).attr('init').split(',');
						ding = false;
						if (way == 'ding') {
							ding = true;
							way = ['wan', 'qian', 'bai', 'shi', 'ge']
						}
						FormulaDatas.checkvalues();
						FormulaDatas.layout(prizesData)
					}
				});
				M.on('click', function() {
					if ($(this).hasClass('black')) {
						M.addClass('black').removeClass('red');
						$(this).addClass('red').removeClass('black');
						methodway = $(this).attr('init');
						FormulaDatas.checkvalues();
						FormulaDatas.layout(prizesData)
					}
				});
				I.on('click', function() {
					FormulaDatas.getRandomMethod();
					FormulaDatas.checkvalues();
					FormulaDatas.layout(prizesData)
				});
				E.on('click', function() {
					kline = true;
					FormulaDatas.layout(prizesData)
				});
				F.on('click', function() {
					kline = false;
					FormulaDatas.layout(prizesData)
				});
				G.on('click', function() {
					$('#klinemap').html('fdsgf').css('color', '#fff')
				});
				H.on('click', function() {
					$('#klinemap').html('dsafdsa').css('color', '#fff')
				});
				this.getdata(caipiao)
			},
			checkvalues: function() {
				if (recentid != A.val() || before != B.val()) {
					recentid = parseInt(A.val());
					before = parseInt(B.val());
					FormulaDatas.getdata(caipiao)
				}
				return
			},
			getRandomMethod: function() {
				$.ajax({
					type: "post",
					async: false,
					url: "/caipiao/kline/getmethod",
					dataType: "json",
					success: function(a) {
						if (a) {
							J.val(a.method)
						}
					},
					error: function(a) {
						$("#gamepad").html('数据获取失败！')
					}
				})
			},
			layout: function(a) {
				if (a.ret == 0) {
					prizes = a.prizes;
					var b = J.val();
					var c = this.getMetodPrizeNums(prizes, way, b, parseInt(K.val()));
					plan = parseInt(L.val());
					var d = this.prizesData(way, prizes, c, 1, plan);
					var e = "klinemap";
					KlineMaps.createmap('', d, e);
					var f = "大数计划 " + c.length + "注</br>第" + nextId + "期的投注号为：</br><textarea class='bettings span12'>" + d[4].join(' ') + "</textarea>";
					$("#betting").html(f)
				} else if (a.ret == 1) {
					if (confirm(a.message)) {
						 window.location.reload(true);
						return true
					} else {
						return false
					}
				}
			},
			getdata: function(b) {
				recentid = A.val();
				$.ajax({
					type: "post",
					async: false,
					url: "http://localhost:8011/ds/data",
					data: "caipiao=" + b + "&recentid=" + recentid + "&before=" + before,
					dataType: "json",
					success: function(a) {
						if (a) {
							prizesData = a;
							FormulaDatas.layout(a)
						} else {
							alert('数据获取失败！')
						}
					},
					error: function(a) {
						return false
					}
				})
			},
			prizesData: function(a, b, c, d, e) {
				var f = new Array();
				var g = new Array();
				var h = new Array();
				var j = 0;
				var k = 1;
				var l = 0;
				var m = 0;
				var n = 0;
				var o = 0;
				var p = [];
				for (var i in b) {
					var q = b[i]['peroid'];
					if (i == 0) {
						j = q;
						continue
					}
					q % d == 0 && f.push(q);
					h[q] = b[i]['prize'];
					var r = b[i]['prize'].split('');
					var s = 1;
					var t = '';
					var u = c;
					u = c[j];
					if (ding) {
						var v = 0;
						for (var w in a) {
							var x = 0;
							switch (a[w]) {
							case 'ge':
								x = 4;
								break;
							case 'shi':
								x = 3;
								break;
							case 'bai':
								x = 2;
								break;
							case 'qian':
								x = 1;
								break;
							case 'wan':
								x = 0;
								break
							}
							if (u) {
								s = 10;
								var y = u.length;
								v += CommonObj.getInArray(r[x], u) == kline ? (s - y) / y : -1
							}
						}
					} else {
						for (var w in a) {
							var x = 0;
							switch (a[w]) {
							case 'ge':
								x = 4;
								break;
							case 'shi':
								x = 3;
								break;
							case 'bai':
								x = 2;
								break;
							case 'qian':
								x = 1;
								break;
							case 'wan':
								x = 0;
								break
							}
							t += r[x];
							s *= 10
						}
						var y = u.length;
						var v = CommonObj.getInArray(t, u) == kline ? (s - y) / y : -1
					}
					if (this.getMethodWay(k, e, methodway, v)) {
						j = q;
						k = 1
					} else {
						j = j == 0 ? q : j;
						k++
					}
					p = c[j];
					m += v;
					n = n > m ? n : m;
					o = o < m ? o : m;
					if (q % d == 0) {
						var z = new Array(l, m, n, o);
						g.push(z);
						n = o = l = m
					}
				}
				return new Array(f, g, h, c, p)
			},
			getMethodWay: function(a, b, c, d) {
				var e = true;
				switch (c) {
				case "normal":
					e = a == b ? true : false;
					break;
				case "zong":
					e = a == b || d > 0 ? true : false;
					break;
				case "gua":
					e = a == b || d <= 0 ? true : false;
					break
				}
				return e
			},
			getMetodPrizeNums: function(a, b, c, d) {
				var e = new Array();
				for (var f in a) {
					var g = a[f]['prize'].split('');
					var h = '';
					var i = '';
					var j = new Array();
					h = c.replace(/E/g, g[4]);
					h = h.replace(/D/g, g[3]);
					h = h.replace(/C/g, g[2]);
					h = h.replace(/B/g, g[1]);
					h = h.replace(/A/g, g[0]);
					i = eval(h);
					j = i.toString().split('');
					j = CommonObj.unique(j);
					e[a[f]['peroid']] = this.getnums(j, b, d)
				}
				return e
			},
			getnums: function(c, d, e) {
				var f = new Array();
				var g = ding ? 1 : d.length;
				for (var i = 0; i < g; i++) {
					var h = f;
					var j = i % 2 == 0 ? c.slice(0, e) : c.slice(c.length - e);
					j = j.sort(function(a, b) {
						return a > b ? 1 : -1
					});
					if (i == 0) {
						f = j
					} else {
						var z = new Array();
						for (var x = 0; x < h.length; x++) {
							for (var y = 0; y < j.length; y++) {
								z.push(h[x] + j[y])
							}
						}
						f = z
					}
				}
				return f
			},
		}
	}();
var CommonObj = function() {
		var z = $("#startPrize");
		var A = $("#before");
		var B = $("#btnStatistic");
		var C = $(".way");
		var D = $('#morebox');
		var E = $('#main');
		var F = $('#betting');
		return {
			insect: function(a, b) {
				var c = 0,
					bi = 0;
				var d = new Array();
				while (c < a.length && bi < b.length) {
					if (a[c] < b[bi]) {
						c++
					} else if (a[c] > b[bi]) {
						bi++
					} else {
						d.push(a[c]);
						c++;
						bi++
					}
				}
				return d
			},
			union: function(x, y) {
				for (var j = 0; j < y.length; j++) {
					if (!this.getInArray(y[j], x)) {
						x.push(y[j])
					}
				}
				return x
			},
			reduce: function(x, y) {
				var a = new Array();
				for (var i = 0; i < x.length; i++) {
					if (!this.getInArray(x[i], y)) {
						a.push(x[i])
					}
				}
				return a
			},
			prizesData: function(a, b, c, d) {
				var e = new Array();
				var f = new Array();
				var g = new Array();
				var h = 0;
				var j = 1;
				var k = 0;
				var l = 0;
				var m = 0;
				var n = 0;
				for (var i in b) {
					var o = b[i]['peroid'];
					o % d == 0 && e.push(o);
					g[o] = b[i]['prize'];
					var p = b[i]['prize'].split('');
					var q = 1;
					var r = '';
					if (ding) {
						var s = 0;
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							if (c[a[t]]) {
								q = 10;
								var v = c[a[t]].length;
								s += this.getInArray(p[u], c[a[t]]) == kline ? (q - v) / v : -1
							}
						}
					} else {
						for (var t in a) {
							var u = 0;
							switch (a[t]) {
							case 'ge':
								u = 4;
								break;
							case 'shi':
								u = 3;
								break;
							case 'bai':
								u = 2;
								break;
							case 'qian':
								u = 1;
								break;
							case 'wan':
								u = 0;
								break
							}
							r += p[u];
							q *= 10
						}
						if (j == plan) {
							h = o;
							j = 1
						} else {
							h = h == 0 ? o : h;
							j++
						}
						var w = c;
						var v = w.length;
						var s = this.getInArray(r, w) == kline ? (q - v) / v : -1
					}
					l += s;
					m = m > l ? m : l;
					n = n < l ? n : l;
					if (o % d == 0) {
						var x = new Array(k, l, m, n);
						f.push(x);
						m = n = k = l
					}
				}
				return new Array(e, f, g, c)
			},
			getInArray: function(a, b) {
				for (var i in b) {
					if (a == b[i]) {
						return true
					}
				}
				return false
			},
			getPrizeNum: function(a) {
				var b = new Array();
				for (var c in a) {
					var d = a[c];
					if (b.length > 0) {
						var e = new Array();
						for (var i in b) {
							for (var j in d) {
								e.push(b[i].toString() + d[j])
							}
						}
						b = e;
						e = new Array()
					} else {
						b = d
					}
					d = new Array()
				}
				return b
			},
			unique: function(a) {
				var b = new Array();
				var c = new Array();
				for (var i = 0; i < a.length; i++) {
					if (!c[a[i]] && a[i] !== '.' && a[i] !== '-') {
						b.push(a[i]);
						c[a[i]] = 1
					}
				}
				return b
			},
			generateRandom: function(a, b) {
				var c = parseInt(Math.random() * a).toString();
				var l = c.length;
				if (l < way.length) {
					var d = "00000";
					var e = c;
					c = d.substring(0, way.length - c.length) + e
				}
				for (var i = 0; i < b.length; i++) {
					if (b[i] == c) {
						return b
					}
				}
				b.push(c);
				return b
			},
		}
	}();
var KlineMaps = function() {
		var l = $("#klinebox");
		var m = $("#yiloubox");
		var n = $("#morebox");
		var o = $("#staticbox");
		var p = $('.tab-content .row-fluid');
		return {
			init: function() {
				l.on('click', function() {
					kline = true;
					KlineData.mapshow(prizesData)
				});
				m.on('click', function() {
					kline = false;
					KlineData.mapshow(prizesData)
				});
				n.on('click', function() {
					$('#klinemap').html('fdsgf').css('color', '#fff')
				});
				o.on('click', function() {
					$('#klinemap').html('dsafdsa').css('color', '#fff')
				})
			},
			countdata: function(a, b) {
				for (var i in b) {
					var c = b[i]['prize'].split("");
					for (var j in index) {
						count[c[index[j]]]++
					}
				}
				return b
			},
			getAvarageLine: function(a, b) {
				var c = new Array();
				for (var i = 0; i < b.length; i++) {
					if (i >= a) {
						var r = 0;
						for (var j = i; j >= i - a; j--) {
							r += parseInt(b[j][1])
						}
						c[i] = parseFloat(r / a)
					} else {
						c[i] = '-'
					}
				}
				return c
			},
			getBollLine: function(a, b, c) {
				var d = new Array();
				if (c) {
					for (var i = 0; i < b.length; i++) {
						if (i >= a) {
							var r = 0;
							var e = 0;
							var s = 0;
							for (var j = i; j >= i - a; j--) {
								r += parseInt(b[j][1])
							}
							e = parseFloat(r / a);
							for (var j = i; j >= i - a; j--) {
								s += (e - parseInt(b[j][1])) * (e - parseInt(b[j][1]))
							}
							d[i] = e + 2 * Math.sqrt(s / a)
						} else {
							d[i] = '-'
						}
					}
				} else {
					for (var i = 0; i < b.length; i++) {
						if (i >= a) {
							var r = 0;
							var e = 0;
							var s = 0;
							for (var j = i; j >= i - a; j--) {
								r += parseInt(b[j][1])
							}
							e = parseFloat(r / a);
							for (var j = i; j >= i - a; j--) {
								s += (e - parseInt(b[j][1])) * (e - parseInt(b[j][1]))
							}
							d[i] = e - 2 * Math.sqrt(s / a)
						} else {
							d[i] = '-'
						}
					}
				}
				return d
			},
			getMacdBar: function(a) {
				var b = new Array();
				var c = new Array();
				var d = new Array();
				var e = 0;
				var f = 0;
				for (var i = 0; i < a.length; i++) {
					if (i > 0) {
						var g = 12;
						var h = 26;
						var j = 9;
						var e = (e * (g - 1) + 2 * a[i][1]) / (g + 1);
						var f = (f * (h - 1) + 2 * a[i][1]) / (h + 1);
						d[i] = e - f;
						c[i] = (c[parseInt(i - 1)] * (j - 1) + 2 * d[i]) / (j + 1);
						b[i] = 2 * (d[i] - c[i])
					} else {
						e = f = a[i][1];
						d[i] = 0;
						b[i] = 0;
						c[i] = 0
					}
				}
				return new Array(c, d, b)
			},
			splitData: function(a) {
				var b = a[0];
				var c = a[1];
				var d = KlineMaps.getMacdBar(c);
				return {
					categoryData: b,
					values: c,
					line5: [''],
					line15: KlineMaps.getAvarageLine(15, c),
					line30: KlineMaps.getAvarageLine(30, c),
					line60: KlineMaps.getAvarageLine(60, c),
					bollup: KlineMaps.getBollLine(20, c, true),
					bollmiddle: KlineMaps.getAvarageLine(20, c),
					bolldown: KlineMaps.getBollLine(20, c, false),
					dea: d[0],
					dif: d[1],
					macdbar: d[2],
					prizes: a[2],
				}
			},
			createmap: function(c, d, e) {
				$("#" + e).html('');
				var f = parseInt($("#" + e).width() * 0.4);
				var g = f > 300 ? f : 300;
				$("#" + e).height(g);
				var h = echarts.init(document.getElementById(e));
				var i = f > 300 ? ['15均线', '30均线', '60均线'] : [];
				var j = this.splitData(d);
				
				
				
				bollingMatched = RandomBollingTrategy.isMatch(j);
				if (!bollingMatched) {
					return false;
				}
				
				var k = {
					title: {
						text: c,
						left: 'left',
						textStyle: {
							color: '#ccc',
							fontStyle: 'normal',
							fontWeight: 'normal',
							fontSize: 12,
						}
					},
					legend: {
						data: i,
						x: 'center',
						textStyle: {
							color: '#fff',
							fontStyle: 'normal',
							fontWeight: 'normal',
							fontSize: 12,
						}
					},
					backgroundColor: '#333',
					tooltip: {
						trigger: 'axis',
						padding: 5,
						backgroundColor: '#222',
						borderColor: '#777',
						borderWidth: 1,
						formatter: function(a) {
							var b = '期号：' + a[0].name;
							b += '<br/> 开奖号：' + j.prizes[a[0].name];
							return b
						}
					},
					grid: [{
						left: '20px',
						right: '20px',
						height: '75%',
						x: 0,
						y: 40,
						x2: 0,
						y2: 0
					}, {
						left: '20px',
						right: '20px',
						height: '15%',
						top: '85%',
						x: 0,
						y: 40,
						x2: 0,
						y2: 0
					}],
					toolbox: {
						show: true,
						feature: {
							mark: {
								show: true
							},
							dataZoom: {
								show: true
							},
							dataView: {
								show: true,
								readOnly: false
							},
							magicType: {
								show: true,
								type: ['line', 'bar']
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},
					xAxis: [{
						type: 'category',
						data: j.categoryData,
						boundaryGap: false,
						nameLocation: 'middle',
						nameTextStyle: {
							fontSize: 18
						},
						splitLine: {
							show: false
						},
						axisTick: {
							lineStyle: {
								color: '#333'
							}
						},
						axisLine: {
							lineStyle: {
								color: '#333'
							}
						},
					}, {
						gridIndex: 1,
						type: 'category',
						data: j.categoryData,
						boundaryGap: false,
						nameLocation: 'middle',
						nameTextStyle: {
							fontSize: 18
						},
						splitLine: {
							show: false
						},
						axisTick: {
							lineStyle: {
								color: '#333'
							}
						},
						axisLine: {
							lineStyle: {
								color: '#333'
							}
						},
					}],
					yAxis: [{
						boundaryGap: [0.01, 0.01],
						type: 'value',
						nameTextStyle: {
							fontSize: 18
						},
						axisLine: {
							lineStyle: {
								color: '#333'
							}
						},
						axisTick: {
							lineStyle: {
								color: '#333'
							}
						},
						splitLine: {
							show: false
						},
						axisLabel: {
							formatter: '{value}',
							textStyle: {
								color: '#333'
							}
						}
					}, {
						gridIndex: 1,
						boundaryGap: [0.01, 0.01],
						type: 'value',
						nameTextStyle: {
							fontSize: 18
						},
						axisLine: {
							lineStyle: {
								color: '#333'
							}
						},
						axisTick: {
							lineStyle: {
								color: '#333'
							}
						},
						splitLine: {
							show: false
						},
						axisLabel: {
							formatter: '{value}',
							textStyle: {
								color: '#333'
							}
						}
					}],
					series: [{
						name: '期号',
						type: 'k',
						data: j.values,
						itemStyle: {
							normal: {
								color: '#FF0000',
								color0: "#00FFFF",
								borderColor: '#FF0000',
								borderColor0: '#00FFFF',
							}
						},
					}, {
						name: '5均线',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#fff',
								lineStyle: {
									width: 1,
								}
							}
						},
						data: j.line5,
					}, {
						name: '15均线',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#ff0',
								lineStyle: {
									width: 1,
								}
							}
						},
						data: j.line15,
					}, {
						name: '30均线',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#f0f',
								lineStyle: {
									width: 1,
								}
							}
						},
						data: j.line30,
					}, {
						name: '60均线',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#0f0',
								lineStyle: {
									width: 1,
								}
							}
						},
						data: j.line60,
					}, {
						name: 'bollup',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#8080FF',
								lineStyle: {
									width: 1.1,
								}
							}
						},
						data: j.bollup,
					}, {
						name: 'bollmiddle',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#8080FF',
								lineStyle: {
									width: 1.1,
								}
							}
						},
						data: j.bollmiddle,
					}, {
						name: 'bolldown',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#8080FF',
								lineStyle: {
									width: 1.1,
								}
							}
						},
						data: j.bolldown,
					}, {
						xAxisIndex: 1,
						yAxisIndex: 1,
						name: 'dea',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#1DAA55',
								lineStyle: {
									width: 1.1,
								}
							}
						},
						data: j.dea,
					}, {
						xAxisIndex: 1,
						yAxisIndex: 1,
						name: 'dif',
						type: 'line',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: '#fff',
								lineStyle: {
									width: 1.1,
								}
							}
						},
						data: j.dif,
					}, {
						xAxisIndex: 1,
						yAxisIndex: 1,
						name: 'macdbar',
						type: 'bar',
						symbol: 'none',
						itemStyle: {
							normal: {
								color: function(a) {
									var b = a.data - this.precolor;
									this.precolor = a.data;
									return b > 0 ? '#FF0000' : '#00FFFF'
								},
							}
						},
						data: j.macdbar,
					}],
					precolor: 0,
				};
				h.setOption(k);
				 return  true;
			},
		}
	}();
var NotifyData = function() {
    var d = $("#startPrize");
    return {

        betCur: function(cp, no, data) {
            $.ajax({
                type: "post",
                async: false,
                url: "http://localhost:8011/betCP",
                data: "caipiao=" + cp + "&no=" + no + "&data=" + data+ "&id=doubleJC" ,
                dataType: "json",
                success: function(a) {
                    console.log(a);
                },
                error: function(a) {
                    return false
                }
            });
        },
		checkis2Recall: function (cp) {
            $.ajax({
                type: "post",
                async: false,
                url: "http://localhost:8011/checkRecall",
                data: "caipiao=" + cp + "&id=doubleJC" ,
                dataType: "json",
                success: function(a) {
                	if(a){
                        window.location.reload(true);
					} else {
                        window.setTimeout(

                            function() {
                                NotifyData.checkis2Recall(cp);
                            }, 3000);					}
                },
                error: function(a) {
                    window.setTimeout(

                        function() {
                            NotifyData.checkis2Recall(cp);
                        }, 3000);                 }

            });
        }

    }
}();
	