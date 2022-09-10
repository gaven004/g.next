<#import "/common/base.ftlh" as base>
<@base.page>
    <div class="row">
        <div class="col-md-4">
            <button id="jqgAdd" class="btn btn-success btn-sm" data-url="api/rest/sysPropertyCategories" data-token="${_csrf.token}">
                <i class="ace-icon glyphicon glyphicon-plus"></i> 新建
            </button>
            <button id="jqgDel" class="btn btn-sm btn-danger" data-url="api/rest/sysPropertyCategories" data-token="${_csrf.token}">
                <i class="ace-icon  glyphicon glyphicon-trash "></i> 删除
            </button>
        </div>
        <div class="col-md-8">
            <div class="input-group" style="margin-left:auto;">
                <form id="form" class="form-inline align-right">
                    <input name="id" type="text" class="input-elm" placeholder="编码">
                    <input name="name" type="text" class="input-elm" placeholder="名称">
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
                url: "api/rest/sysPropertyCategories",
                editUrl: "api/rest/sysPropertyCategories",
                delUrl: "api/rest/sysPropertyCategories",
                token: _csrf,
                colModel: [{
                    key: true,
                    label: '编码',
                    name: 'id',
                    width: 60,
                    editable: true
                }, {
                    label: '名称',
                    name: 'name',
                    width: 150,
                    editable: true,
                    editoptions: {
                        size: "20",
                        maxlength: "30"
                    }
                }, {
                    label: '状态',
                    name: 'status',
                    width: 50,
                    editable: true,
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
