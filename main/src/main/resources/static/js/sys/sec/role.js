jQuery(function ($) {
    $("#grid-table").grid({
        url: "sys/roles",
        editUrl: (typeof _edit_permission !== 'undefined') && _edit_permission ? "sys/roles" : "",
        delUrl: (typeof _delete_permission !== 'undefined') && _delete_permission ? "sys/roles" : "",
        formDataHeight: "450px",
        token: _csrf,
        beforeEditSubmit: function (postdata, formid) {
            if (postdata && postdata.authorities) {
                const words = postdata.authorities.split(',');
                Object.assign(postdata, {authorities: words.map(x => ({'id': x}))});
            }
            return [true, ""];
        },
        colModel: [{
            key: true,
            label: '',
            name: 'id',
            hidden: true,
            editable: true,
        }, {
            label: '角色',
            name: 'name',
            width: 100,
            editable: true,
            editrules: {
                required: true,
            },
            editoptions: {
                size: "20",
                maxlength: "60"
            }
        }, {
            label: '功能权限',
            name: 'authorities',
            width: 300,
            editable: true,
            edittype: "select",
            editrules: {
                required: true,
            },
            editoptions: {
                dataUrl: "sys/action/options",
                buildSelect: function (data, xhr, cm, col) {
                    let html = jqgBuildSelect(data, xhr, cm, col, 'value', 'label', false);
                    window.setTimeout(function () {
                        $("select#roles.chosen-select.FormElement").trigger("chosen:updated");
                    }, 500);
                    return html;
                },
                multiple: true,
                size: 5,
                class: "chosen-select",
                dataInit: function (element) {
                    window.setTimeout(function () {
                        $(element).chosen({
                            width: "90%",
                            no_results_text: "没有符合条件的数据！",
                            placeholder_text_multiple: "请选择...",
                            placeholder_text_single: "请选择..."
                        });
                        $(element).trigger("chosen:updated");
                    }, 200);
                },
            },
            formatter: function (cellvalue, options, rowObject) {
                let ids = '', names = '';
                if (cellvalue) {
                    cellvalue.forEach(element => {
                        ids += element.id + ',';
                        names += element.description + '，'
                    });
                    if (ids !== '') {
                        ids = ids.substring(0, ids.length - 1);
                    }
                    if (names !== '') {
                        names = names.substring(0, names.length - 1);
                    }
                }
                return '<span data-id="' + ids + '">' + names + '</span>';
            },
            unformat: function (cellvalue, options, cellobject) {
                return $('span', cellobject).attr('data-id');
            }
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
