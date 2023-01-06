jQuery(function ($) {
    $("#grid-table").grid({
        url: "sys/property/categories",
        editUrl: (typeof _edit_permission !== 'undefined') && _edit_permission ? "sys/property/categories" : "",
        delUrl: (typeof _delete_permission !== 'undefined') && _delete_permission ? "sys/property/categories" : "",
        token: _csrf,
        colModel: [{
            key: true,
            label: '编码',
            name: 'id',
            width: 60,
            editable: true,
            editrules: {
                required: true,
            },
        }, {
            label: '名称',
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
