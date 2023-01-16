jQuery(function ($) {
    // 如果有多个上传的组件，需要传入flag以区分每个上传组件
    Handlebars.registerHelper("attachments", function (files, readonly, flag) {
        if (arguments.length === 3 || flag === undefined) {
            flag = "";
        }
        getAttachment(flag).init();  // 必须重置idx
        let result = "";
        if (files && files.length > 0) {
            $.each(files, function (index, file) {
                file.readonly = readonly;
                result += getAttachment(flag).create(file);
            })
        } else {
            result = getAttachment(flag).create({readonly: readonly});
        }
        result += "<div class='tips has-warning help-block col-sm-offset-2 col-sm-9 inline' style='color: #8a6d3b;'>" +
            "注意：附件请勿使用带!、@、#、$、+、%等特殊符号的文件名；目前支持单个文件最大200M，超过则无法上传。" +
            "</div>"
        return result;
    });
});

const _attachments = {};

function getAttachment(flag) {
    if (_attachments[flag] !== undefined) {
        return _attachments[flag];
    }
    const attachment = (function (flag) {
        let index = 0;
        let total = 0;
        const _flag = flag;
        return {
            init: function () {
                index = 0;
                total = 0;
            },
            create: function (data) {
                const d = {idx: index, flag: _flag};
                $.extend(d, data);
                index++;
                total++;
                return $("#attachmentTemplate").template(d).prop("outerHTML");
            },
            remove: function (index) {
                if (total === 0) {
                    return;
                }
                $("#divAttachmentRow" + _flag + index).remove();
                total--;
                // 删除第一行或者已经全部删除，重新生成首行
                if (total === 0 || index === 0) {
                    var data = {idx: 0};
                    this.addRow(data);
                }
            },
            addRow: function (data) {
                if (data && data.idx === 0) {
                    $("#divAttachment" + _flag).prepend($(this.create(data)));
                } else {
                    $(this.create(data)).insertBefore($("#divAttachment" + _flag + " > .tips"));
                }
            }
        }
    })(flag);
    _attachments[flag] = attachment;
    return attachment;
}

// 点击浏览
function browseClick(flag, idx) {
    $("#file" + flag + idx).click();
}

// 选择文件后
function fileChange(flag, idx) {
    $("#inputFile" + flag + idx).val($("#file" + flag + idx).val());
}

// 点击下载
function download(id) {
    window.open("sys/attachment/download/" + id);
}

