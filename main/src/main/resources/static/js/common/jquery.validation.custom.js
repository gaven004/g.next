$.fn.validate_extend = function(data) {
    // 设置校验错误信息
    var setting = {
        success: function (l, e) {
            $(e).closest('.form-group').removeClass('has-error');
            $(e).tooltip("hide");
        },
        errorPlacement: function (l, e) {
            $(e).closest('.form-group').addClass('has-error');
            var error = $(l).html();
            if (error) {
                // 由于tooltip会缓存，改变title只能改变缓存的值
                var cache = $(e).data('bs.tooltip');
                if (cache) {
                    cache.options.title = error;
                } else {
                    $(e).addClass("tooltip-error"); // 红色
                    $(e).tooltip({
                        trigger: "manual",
                        placement: "top",
                        title: error
                    });
                }
                // 显示错误提示信息
                $(e).tooltip("show");
                setTimeout(function () {
                    $(e).tooltip("hide")    // 自动隐藏
                }, 4000);
            } else {
                $(e).tooltip("hide");
            }
        }
    };
    $.extend(setting, data || {});
    $(this).validate(setting);
};
