<#import "/common/base.ftlh" as base>
<@base.page>
    <div class="row">
        <div class="col-md-4">
            <button id="jqgAdd" class="btn btn-success btn-sm" data-url="api/rest/sysProperties"
                    data-token="${_csrf.token}">
                <i class="ace-icon glyphicon glyphicon-plus"></i> 新建
            </button>
            <button id="jqgDel" class="btn btn-sm btn-danger" data-url="api/rest/sysProperties"
                    data-token="${_csrf.token}">
                <i class="ace-icon  glyphicon glyphicon-trash "></i> 删除
            </button>
        </div>
        <div class="col-md-8">
            <div class="input-group" style="margin-left:auto;">
                <form id="form" class="form-inline align-right">
                    <input name="category" type="text" class="input-elm" placeholder="参数类型">
                    <input name="name" type="text" class="input-elm" placeholder="参数名">
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
                url: "api/rest/sysProperties",
                editUrl: "api/rest/sysProperties",
                delUrl: "api/rest/sysProperties",
                token: _csrf,
                colModel: [{
                    key: true,
                    label: '',
                    name: 'id',
                    hidden: true,
                    editable : true,
                }, {
                    label: '参数类型',
                    name: 'category',
                    width: 100,
                    editable: true,
                    edittype: "select",
                    editoptions: {
                        dataUrl: "api/rest/sysPropertyCategories?status=VALID&page=0&size=1000&sort=name,asc",
                        buildSelect: function (data, xhr, cm, col) {
                            return jqgBuildSelect(data, xhr, cm, col, 'id', 'name');
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
                    editable: true,
                    editrules: {
                        required: true,
                    },
                    edittype: "select",
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
    </script>
</@base.page>
