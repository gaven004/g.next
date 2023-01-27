async function showDetail(rowid) {
    const data = {};
    if (rowid) {
        $.extend(data, $("#grid-table").jqGrid('getRowData', rowid));
        if (data && data.id) {
            // 查询附件信息
            const promise = COMM.fetch("sys/attachment?module=BBS_ATTACHMENT&srcRecode=" + data.id);
            await promise.then(response => {
                if (response.result === 'SUCCESS' && response.body) {
                    $.extend(data, {files: response.body, readonly: false});
                }
            });
        }
    }
    $("#mainList").showDetail(data);
}

function remove(rowid) {
    $("#grid-table").jqGrid('setSelection', rowid);
    $("#jqgDel").click();
}

function publish(rowid) {
    const promise = COMM.fetchAndMessage('bbs/article/' + rowid + '/publish', 'POST');
    promise.then(data => {
        $("#grid-table").trigger("reloadGrid")
    });
}

function withdraw(rowid) {
    const promise = COMM.fetchAndMessage('bbs/article/' + rowid + '/withdraw', 'POST');
    promise.then(data => {
        $("#grid-table").trigger("reloadGrid")
    });
}


jQuery(function ($) {
    $.fn.extend({
        showDetail: function (data) {
            const parent = $(this);

            // 替换parent内容
            parent.children().hide();
            $("#detailTemplate").template(data).appendTo(parent);

            const myeditor = CKEDITOR.replace("content", {
                filebrowserImageUploadUrl: 'upload/BBS_IMG?_csrf=' + _csrf,
                height: '40vh'
            });

            $("#formDetail > div:nth-child(2) > div.col-sm-8 > select").chosen({});

            // 校验初始化
            $('form#formDetail').validate({
                focusInvalid: true,
                ignore: ":hidden:not(select)",
                rules: {
                    columnId: {
                        required: true,
                        // digits: true,
                        // min: 1
                    },
                    title: {
                        required: true
                    }
                },
                messages: {
                    columnId: "请选择栏目",
                    title: "请输入标题",
                },

                highlight: function (e) {
                    $(e).closest('.form-group').addClass('has-error');
                },

                success: function (e) {
                    $(e).closest('.form-group').removeClass('has-error');
                    $(e).closest('.form-group').find('div.help-block').html('');
                },

                errorPlacement: function (error, element) {
                    const block = $(element).closest('.form-group').find('div.help-block');
                    block.html('');
                    error.appendTo(block);
                }
            });

            // 点击返回
            $("button.back").click(function () {
                $("#divDetail").remove();
                parent.children().show();
                $("#grid-table").trigger("reloadGrid");
                $(window).triggerHandler('resize.jqGrid');
            });

            // 点击保存
            $("#btnDetailSave").click(function () {
                $('#content').val(myeditor.getData());

                if (!$('#formDetail').valid()) {
                    return;
                }

                const id = $('#formDetail > input#id').val();
                const formData = new FormData($("#formDetail")[0]);
                const promise = COMM.fetchAndMessage(
                    id ? 'bbs/article/' + id : 'bbs/article', id ? 'PATCH' : 'POST', formData);
                promise.then(data => {
                    if (data.result === 'SUCCESS') {
                        $('#formDetail > input#id').val(data.body.id);
                    }
                });
            });

            // 点击发布
            $("#btnDetailPublish").click(function () {
                $('#content').val(myeditor.getData());
                if (!$('#formDetail').valid()) {
                    return;
                }

                const id = $('#formDetail > input#id').val();
                const formData = new FormData($("#formDetail")[0]);
                const promise = COMM.fetchAndMessage('bbs/article/' + id + '/publish', 'PATCH', formData);
                promise.then(data => {
                    $("#btnDetailBack").click();
                });
            });

            // 点击回收
            $("#btnDetailWithdraw").click(function () {
                const id = $('#formDetail > input#id').val();
                const promise = COMM.fetchAndMessage('bbs/article/' + id + '/withdraw', 'POST');
                promise.then(data => {
                    $("#btnDetailBack").click();
                });
            });
        }
    });

    $(".chosen-select").chosen({});

    $("#form").bind("reset", function () {
        window.setTimeout(function () {
            $(".chosen-select").trigger("chosen:updated");
        }, 200);
    });

    $("#grid-table").grid({
        url: "bbs/article",
        // editUrl: (typeof _edit_permission !== 'undefined') && _edit_permission ? "bbs/article" : "",
        // delUrl: (typeof _delete_permission !== 'undefined') && _delete_permission ? "bbs/article" : "",
        token: _csrf,
        multiselect: true,
        multiboxonly: true,
        colModel: [{
            key: true,
            label: 'ID',
            name: 'id',
            width: 60,
            hidden: true,
            editable: true,
        }, {
            label: '操作',
            name: 'action',
            index: '',
            width: 92,
            align: 'center',
            fixed: true,
            resize: false,
            sortable: false,
            formatter: function (cellvalue, options, rowObject) {
                const data = {
                    rowid: rowObject.id,
                    published: rowObject.status === 'PUBLISH'
                };
                return $("#actionButtonTemplate").template(data).prop("outerHTML");
            },
        }, {
            label: '栏目',
            name: 'columnId',
            width: 100,
            formatter: 'select',
            editable: true,
            edittype: "select",
            editrules: {
                required: true,
            },
            editoptions: {
                value: _bbs_columns_value
            }
        }, {
            label: '标题',
            name: 'title',
            width: 200,
            editable: true,
            editoptions: {
                size: "20",
                maxlength: "200"
            },
        }, {
            label: '作者',
            name: 'author',
            width: 50,
            editable: false,
        }, {
            label: '修改时间',
            name: 'mtime',
            width: 50,
            align: 'center',
            editable: false,
            formatter: 'date',
            formatoptions: {
                srcformat: 'ISO8601Short',
            },
        }, {
            label: '状态',
            name: 'status',
            width: 50,
            align: 'center',
            formatter: function (cellvalue) {
                switch (cellvalue) {
                    case 'DRAFT':
                        return '<span src="' + cellvalue + '" class="label label-white middle">草稿</span>';
                    case 'PUBLISH':
                        return '<span src="' + cellvalue + '" class="label label-success label-white middle">发布</span>';
                    case 'WITHDRAWAL':
                        return '<span src="' + cellvalue + '" class="label label-danger label-white middle">回收</span>';
                    default:
                        return cellvalue;
                }
            },
            unformat: function (cellvalue, options, cellobject) {
                return $('span', cellobject).attr('src');
            },
            editable: false,
        }, {
            name: 'content',
            hidden: true
        }]
    });
});
