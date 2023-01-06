jQuery(function ($) {
    $("#grid-table").grid({
        url: "sys/action",
        editUrl: (typeof _edit_permission !== 'undefined') && _edit_permission ? "sys/action" : "",
        delUrl: (typeof _delete_permission !== 'undefined') && _delete_permission ? "sys/action" : "",
        token: _csrf,
        colModel: [{
            key: true,
            label: '',
            name: 'id',
            hidden: true,
            editable: true,
        }, {
            label: '资源',
            name: 'resource',
            width: 100,
            editable: true,
            editrules: {
                required: true,
            },
            editoptions: {
                size: "20",
                maxlength: "250"
            }
        }, {
            label: '请求方法',
            name: 'method',
            width: 50,
            editable: true,
            edittype: "select",
            editoptions: {
                dataUrl: "enums/ActionMethod",
                buildSelect: function (data, xhr, cm, col) {
                    return jqgBuildSelect(data, xhr, cm, col, 'value', 'label');
                },
            },
            editrules: {
                required: true,
            },
        }, {
            label: '说明',
            name: 'description',
            width: 100,
            editable: true,
            editrules: {
                required: true,
            },
            editoptions: {
                size: "20",
                maxlength: "250"
            }
        }, {
            label: '权限控制',
            name: 'permitAll',
            width: 50,
            editable: true,
            editrules: {
                required: true,
            },
            edittype: "select",
            editoptions: {
                value: "false:是;true:否"
            },
            formatter: 'select',
        }, {
            label: '状态',
            name: 'status',
            width: 50,
            editable: true,
            editrules: {
                required: true,
            },
            edittype: "select",
            editoptions: {
                value: "VALID:启用;INVALID:停用"
            },
            formatter: 'select',
        }]
    });
});
