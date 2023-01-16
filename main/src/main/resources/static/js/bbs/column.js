jQuery(function ($) {
    $("#grid-table").grid({
        url: "bbs/column",
        editUrl: (typeof _edit_permission !== 'undefined') && _edit_permission ? "bbs/column" : "",
        delUrl: (typeof _delete_permission !== 'undefined') && _delete_permission ? "bbs/column" : "",
        token: _csrf,
        colModel: [{
            key: true,
            label: 'ID',
            name: 'id',
            width: 60,
            hidden: true,
            editable: true,
        }, {
            label: '栏目名称',
            name: 'name',
            width: 150,
            editable: true,
            editrules: {
                required: true,
            },
            editoptions: {
                size: "20",
                maxlength: "30"
            }
        }, {
            label: '父栏目',
            name: 'parentId',
            width: 150,
            editable: true,
            edittype: "select",
            editoptions: {
                dataUrl: "bbs/column/options",
                buildSelect: function (data, xhr, cm, col) {
                    let html = jqgBuildSelect(data, xhr, cm, col, 'value', 'label');
                    window.setTimeout(function () {
                        $("select#parentId.chosen-select.FormElement").trigger("chosen:updated");
                    }, 500);
                    return html;
                },
                class: "chosen-select",
                dataInit: function (element) {
                    window.setTimeout(function () {
                        $(element).chosen({
                            width: "90%",
                            no_results_text: "没有符合条件的数据！",
                            placeholder_text_multiple: "请选择...",
                            placeholder_text_single: "请选择..."
                        });
                    }, 200);
                },
            },
            editrules: {
                integer: true,
            },
            formatter: function (cellvalue, options, rowObject) {
                return '<span src="' + cellvalue + '">' + rowObject.parentName + '</span>';
            },
            unformat: function (cellvalue, options, cellobject) {
                return $('span', cellobject).attr('src');
            }
        }, {
            label: '图标',
            name: 'icon',
            width: 150,
            editable: true,
            editoptions: {
                size: "20",
                maxlength: "200"
            },
            formatter: function (cellvalue, options, rowObject) {
                return '<span src="' + cellvalue + '">  <i class="' + cellvalue + '"></i>  ' + cellvalue + '</span>';
            },
            unformat: function (cellvalue, options, cellobject) {
                return $('span', cellobject).attr('src');
            }
        }, {
            label: '排序',
            name: 'sort',
            width: 50,
            editable: true,
            editrules: {
                integer: true,
            },
            editoptions: {
                size: "20",
                maxlength: "200"
            }
        }, {
            label: '状态',
            name: 'status',
            width: 50,
            formatter: 'select',
            editable: true,
            edittype: "select",
            editrules: {
                required: true,
            },
            editoptions: {
                value: "ENABLE:启用;DISABLE:停用"
            }
        }]
    });
});
