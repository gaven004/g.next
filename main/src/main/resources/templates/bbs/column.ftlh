<#import "/common/base.ftlh" as base>
<@base.page>
    <div class="row">
        <div class="col-md-4">
            <button id="jqgAdd" class="btn btn-success btn-sm" data-url="_rest/c4m/bbsColumns" data-token="${_csrf.token}">
                <i class="ace-icon glyphicon glyphicon-plus"></i> 新建
            </button>
            <button id="jqgDel" class="btn btn-sm btn-danger" data-url="_rest/c4m/bbsColumns" data-token="${_csrf.token}">
                <i class="ace-icon  glyphicon glyphicon-trash "></i> 删除
            </button>
        </div>
        <div class="col-md-8">
            <div class="input-group" style="margin-left:auto;">
                <form id="form" class="form-inline align-right">
                    <input name="id" type="text" placeholder="编码">
                    <input name="name" type="text" placeholder="名称">
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

    <script>
        jQuery(function ($) {
            $("#grid-table").grid({
                url: "_rest/c4m/bbsColumns",
                editUrl: "_rest/c4m/bbsColumns",
                delUrl: "_rest/c4m/bbsColumns",
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
                    editrules: {
                        integer: true,
                    },
                    editoptions: {
                        size: "20",
                        maxlength: "30"
                    }
                }, {
                    label: '图标',
                    name: 'icon',
                    width: 150,
                    editable: true,
                    editoptions: {
                        size: "20",
                        maxlength: "200"
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
    </script>
</@base.page>
