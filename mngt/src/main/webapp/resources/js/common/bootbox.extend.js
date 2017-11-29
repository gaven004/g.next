$(function () {
    bootbox.setLocale("zh_CN");
    bootbox.autoCloseDialog = function (options, sec) {
        if (sec == null) sec = 3;
        var $dia = bootbox.dialog($.extend({onEscape: true}, options));
        var $title = $dia.find(".modal-title");
        var titleHtml = $dia.find(".modal-title").html();
        var remaining = sec;
        var timeout = window.setInterval(function () {
            remaining--;
            var $title = $dia.find(".modal-title");
            $title.html(titleHtml + " （" + (remaining + 1) + "） ");
            if (remaining == 0) {
                window.clearInterval(timeout);
                $dia.modal("hide");
                return;
            }
        }, 1000);
        return $dia;
    }
});