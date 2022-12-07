<#import "/common/base.ftlh" as base>
<@base.page>
    <div class="row">
        <div class="col-md-4">
            <button id="jqgAdd" class="btn btn-success btn-sm" data-url="_rest/c4m/sysUsers"
                    data-token="${_csrf.token}">
                <i class="ace-icon glyphicon glyphicon-plus"></i> 新建
            </button>
            <button id="jqgDel" class="btn btn-sm btn-danger" data-url="_rest/c4m/sysUsers"
                    data-token="${_csrf.token}">
                <i class="ace-icon  glyphicon glyphicon-trash "></i> 删除
            </button>
        </div>
        <div class="col-md-8">
            <div class="input-group" style="margin-left:auto;">
                <form id="form" class="form-inline align-right">
                    <input name="account" type="text" placeholder="用户账号">
                    <input name="username" type="text" placeholder="用户名">
                    <input name="email" type="text" placeholder="邮箱">
                    <button class="btn btn-sm btn-info" type="button" id="jqgSearch">
                        <i class="ace-icon glyphicon glyphicon-search"></i> 查找
                    </button>
                    <button class="btn btn-sm" type="reset" id="btnReset">
                        <i class="ace-icon glyphicon glyphicon-refresh"></i> 重置
                    </button>
                </form>
            </div>
        </div>
    </div>
    <div class="space-6"></div>
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="widget-grid">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
        </div>
    </div>

    <link rel="stylesheet" type="text/css" href="vendors/chosen/chosen.min.css">
    <script src="vendors/chosen/chosen.jquery.min.js"></script>

    <script>
        jQuery(function ($) {
            function initChosen(element) {
                window.setTimeout(function () {
                    $(element).chosen({
                        width: "90%",
                        no_results_text: "没有符合条件的数据！",
                        placeholder_text_multiple: "请选择...",
                        placeholder_text_single: "请选择..."
                    });
                    $(element).trigger("chosen:updated");
                }, 200);
            }

            $("#grid-table").grid({
                url: "_rest/c4m/sysUsers",
                editUrl: "_rest/c4m/sysUsers",
                delUrl: "_rest/c4m/sysUsers",
                token: _csrf,
                colModel: [{
                    key: true,
                    label: '',
                    name: 'id',
                    hidden: true,
                    editable: true,
                }, {
                    label: '用户账号',
                    name: 'account',
                    width: 100,
                    editable: true,
                    editrules: {
                        required: true,
                    },
                    editoptions: {
                        size: "20",
                        maxlength: "15"
                    }
                }, {
                    label: '用户名',
                    name: 'username',
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
                    label: '邮箱',
                    name: 'email',
                    width: 100,
                    editable: true,
                    editrules: {
                        required: true,
                        email: true
                    },
                    editoptions: {
                        size: "20",
                        maxlength: "60"
                    }
                }, {
                    label: '所属角色',
                    name: 'roles',
                    width: 200,
                    editable: true,
                    edittype: "select",
                    editrules: {
                        required: true,
                    },
                    editoptions: {
                        dataUrl: "_rest/c4m/sysRoles?status=VALID&page=0&size=1000&sort=name,asc",
                        buildSelect: function (data, xhr, cm, col) {
                            return jqgBuildSelect(data, xhr, cm, col, 'id', 'name', false);
                        },
                        multiple: true,
                        size: 5,
                        class: "chosen-select",
                        dataInit: initChosen,
                    },
                    formatter: function (cellvalue, options, rowObject) {
                        let ids = '', names = '';
                        if (cellvalue) {
                            cellvalue.forEach(element => {
                                ids += element.id + ',';
                                names += element.name + '，'
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
                    edittype: "select",
                    editrules: {
                        required: true,
                    },
                    editoptions: {
                        value: "VALID:启用;INVALID:停用"
                    },
                    formatter: 'select',
                }]
            });
        });
    </script>
</@base.page>
