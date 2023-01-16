const COMM = {
    fetch: function (url, method, body, option) {
        option = Object.assign(option || {}, {
            method: method,
            headers: {
                Accept: 'application/json',
                "X-CSRF-TOKEN": _csrf
            },
            body: body
        });
        return fetch(url, option)
            .then(response => response.json());
    },
    fetchAndMessage: function (url, method, body, option, message) {
        return this.fetch(url, method, body, option)
            .then(data => {
                if (data.result === 'SUCCESS') {
                    let dialog = bootbox.dialog({
                        message: message || '操作成功',
                        backdrop: true
                    });
                    window.setTimeout(function () {
                        dialog.modal('hide');
                    }, 3000);
                } else {
                    bootbox.dialog({
                        message: data.message,
                        title: '系统异常',
                        backdrop: true
                    });
                }
                return data;
            })
            .catch(error => {
                bootbox.dialog({
                    message: error,
                    title: '系统异常',
                    backdrop: true
                });
            });
    },
};


(function ($) {
    /**
     * form数据转成对象
     */
    $.fn.serializeObject = function () {
        "use strict";

        const result = {};
        const extend = function (i, element) {
            const node = result[element.name];

            // If node with same name exists already, need to convert it to an array as it
            // is a multi-value field (i.e., checkboxes)

            if ('undefined' !== typeof node && node !== null) {
                if ($.isArray(node)) {
                    node.push(element.value);
                } else {
                    result[element.name] = [node, element.value];
                }
            } else {
                result[element.name] = element.value;
            }
        };

        $.each(this.serializeArray(), extend);
        return result;
    };
})(jQuery);
