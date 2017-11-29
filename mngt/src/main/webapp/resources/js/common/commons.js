var comm = window.comm || {};
comm.get = function(url, data) {
	var retJson = $.ajax({
		url: url,
		type: "get",
		data: data,
		async: false
	});
	if($.trim(retJson.responseText)!=='') {
		return JSON.parse(retJson.responseText);
	}
	return null;
};
comm.ajax = function(url, data) {
	var retJson = $.ajax({
		url : url,
		type : "post",
		data : data,
		async : false
	});
	if($.trim(retJson.responseText)!=='') {
		return JSON.parse(retJson.responseText);
	}
	return null;
};
comm.ajax2msg = function(url, data) {
	var result = comm.ajax(url, data);
	if (result && result.msg) {
		var icon = (result.code == 1 ? 1 : 0);
		layer.msg(result.msg, {
			icon : icon
		});
	}
	return result;
};
comm.onSubmit = function(url, fmName) {
	//	url += '?userId=' + keys.getUser().id + '&token=' + keys.getUser().token;
	var data = $('#' + fmName).serialize();
	var result = comm.ajax(url, data);
	if(result && result.msg) {
		var icon = (result.code == 1 ? 1 : 0);
		layer.msg(result.msg, {
			icon: icon
		});
	}
	return result;
};

/**
 * 弹出确认框，并异步调用请求
 * @param setting
 * {
 * message: 询问信息
 * url: 异步请求地址
 * data: 异步请求数据
 * doneCallback: 成功后回调方法
 * failCallback: 出错后回调方法
 * completeCallback: 调用结束回调方法（成功出错都回调）
 * }
 */
comm.confirm = function(setting) {
    bootbox.confirm(setting.message || "确认吗？", function (result) {
        if (!result) {
            return;
        }
        comm.ajaxMessage(setting);
    });
};

/**
 * 异步调用请求，并弹出请求结果
 * @param setting
 * {
 * url: 异步请求地址
 * data: 异步请求数据
 * doneCallback: 成功后回调方法
 * failCallback: 出错后回调方法
 * completeCallback: 调用结束回调方法（成功出错都回调）
 * }
 */
comm.ajaxMessage = function(setting) {
    var data = {
        type: "GET",
        dataType: "json"
    };
    if (setting.data instanceof FormData) {
        data.cache = false;
        data.processData = false;   // 使用FormData必须指定为false
        data.contentType = false;   // 使用FormData必须指定为false
    }
    $.extend(data, setting);
    $.ajax(data).done(function (response) {
        if (response.code == "1") {
            var msg = response.msg || "成功";
            bootbox.autoCloseDialog({message: msg, title: "提示"});
            if (setting.doneCallback) {
                setting.doneCallback();
            }
        } else {
            if (response.msg) {
                bootbox.alert({message: response.msg, title: "提示"});
            }
        }
        if (setting.completeCallback) {
            setting.completeCallback();
        }
    }).fail(function(response) {
        var msg = response.responseJSON.msg || "出错";
        bootbox.alert({message: msg, title: "提示"});
        if (setting.failCallback) {
            setting.failCallback();
        }
        if (setting.completeCallback) {
            setting.completeCallback();
        }
    });
};

// form数据转成对象
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;

};

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		// month
		"d+" : this.getDate(),
		// day
		"h+" : this.getHours(),
		// hour
		"m+" : this.getMinutes(),
		// minute
		"s+" : this.getSeconds(),
		// second
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		// quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format) || /(Y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

/** 
 * 简单HashMap 
 */
function HashMap() {
	var data = {};
	var size = 0;
	
	//清除所有的属性  
	this.clear = function() {
		data = {};
	}

	/** 
	 * 判断key是否存在 
	 * @param key 
	 * @return Boolean  
	 */
	this.containsKey = function() {
		return Boolean(arguments[0] in data);
	}
	
	/** 
	 * 判断值是否存在 
	 * @param value 
	 * @return Boolean 
	 */
	this.containsValue = function() {
		var str = data.toSource();
		return str.indexOf(arguments[0]) == -1 ? false : true;
	}
	
	/** 
	 * 返回key对应的v 
	 */
	this.get = function() {
		return data[arguments[0]];
	}
	
	/** 
	 * 判断是否为空 
	 * @return Boolean 
	 */
	this.isEmpty = function() {
		return size == 0 ? true : false;
	}
	
	/** 
	 * 取出所有的key 
	 * @return Array() 
	 */
	this.keySet = function() {
		var arr = new Array();
		for ( var i in data) {
			arr.push(i);
		}
		return arr;
	}
	
	/** 
	 * 将key，value放入对象 
	 * @param key  
	 * @param value 
	 */
	this.put = function() {
		data[arguments[0]] = [ arguments[1] ];
		if (!this.containsKey(arguments[0])) {
			size++;
		}

	}
	
	/** 
	 * 将另外一个HashMap 复制到此Map 
	 * @param map 
	 */
	this.putAll = function() {
		data = arguments[0].getData();
	}
	
	/** 
	 * 删除key对应的value 
	 * @param key 
	 * @return value与 key 关联的旧值 
	 */
	this.remove = function() {
		var o = this.get(arguments[0]);
		if (o) {
			delete data[o];
			return o;
		} else {
			return null;
		}
	}
	
	/** 
	 * 返回此HashMap的大小 
	 * @reaturn Int 
	 */
	this.size = function() {
		return size;
	}
	
	/** 
	 * 返回此map所有的value集合 
	 * @return Connections 
	 */
	this.values = function() {
		var arr = new Array();
		for ( var i in data) {
			arr.push(data[i])
		}
		return arr;
	}
	
	this.getData = function() {
		return data;
	}
}