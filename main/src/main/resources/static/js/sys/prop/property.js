jQuery(function ($) {
    $("#grid-table").grid({
        url: "sys/properties",
        editUrl: (typeof _edit_permission !== 'undefined') && _edit_permission ? "sys/properties" : "",
        delUrl: (typeof _delete_permission !== 'undefined') && _delete_permission ? "sys/properties" : "",
        token: _csrf,
        colModel: [{
            key: true,
            label: '',
            name: 'id',
            hidden: true,
            editable: true,
        }, {
            label: '参数类型',
            name: 'category',
            width: 100,
            editable: true,
            edittype: "select",
            editoptions: {
                dataUrl: "sys/property/categories/options",
                buildSelect: function (data, xhr, cm, col) {
                    return jqgBuildSelect(data, xhr, cm, col, 'value', 'label');
                },
            },
            editrules: {
                required: true,
            },
        }, {
            label: '参数名',
            name: 'name',
            width: 100,
            editable: true,
            editrules: {
                required: true,
            },
            editoptions: {
                size: "20",
                maxlength: "30"
            }
        }, {
            label: '参数值',
            name: 'value',
            width: 100,
            editable: true,
            editrules: {
                required: true,
            },
            editoptions: {
                size: "20",
                maxlength: "30"
            }
        }, {
            label: '扩展属性',
            name: 'properties',
            width: 150,
            sortable: false,
            editable: true,
            edittype: "textarea",
            editoptions: {
                rows: "2",
                cols: "10"
            }
        }, {
            label: '排序',
            name: 'sortOrder',
            width: 50,
            editable: true,
            editoptions: {
                size: "20",
                maxlength: "6"
            },
            editrules: {
                integer: true
            }
        }, {
            label: '状态',
            name: 'status',
            width: 50,
            formatter: "select",
            editable: true,
            edittype: "select",
            editrules: {
                required: true,
            },
            editoptions: {
                value: "VALID:启用;INVALID:停用"
            }
        }, {
            label: '说明',
            name: 'note',
            width: 150,
            sortable: false,
            editable: true,
            edittype: "textarea",
            editoptions: {
                rows: "2",
                cols: "10"
            }
        }]
    });
});
