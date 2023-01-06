<#assign sec = JspTaglibs["http://www.springframework.org/security/tags"]/>
<#import "/common/base.ftlh" as base>
<@base.page>
    <div class="row">
        <div class="col-md-4">
            <@sec.authorize access="@webSecurity.check('/sys/action', 'POST')">
            <button id="jqgAdd" class="btn btn-success btn-sm" data-url="sys/action"
                    data-token="${_csrf.token}">
                <i class="ace-icon glyphicon glyphicon-plus"></i> 新建
            </button>
            </@sec.authorize>
            <@sec.authorize access="@webSecurity.check('/sys/action', 'DELETE')">
            <button id="jqgDel" class="btn btn-sm btn-danger" data-url="sys/action"
                    data-token="${_csrf.token}">
                <i class="ace-icon  glyphicon glyphicon-trash "></i> 删除
            </button>
            </@sec.authorize>
        </div>
        <div class="col-md-8">
            <div class="input-group" style="margin-left:auto;">
                <form id="form" class="form-inline align-right">
                    <input name="resource" type="text" class="input-elm" placeholder="资源">
                    <input name="description" type="text" class="input-elm" placeholder="说明">
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
        <@sec.authorize access="@webSecurity.check('/sys/action', 'PATCH')">
        const _edit_permission = true;
        </@sec.authorize>
        <@sec.authorize access="@webSecurity.check('/sys/action', 'DELETE')">
        const _delete_permission = true;
        </@sec.authorize>
    </script>
    <script src="js/sys/sec/action.js"></script>
</@base.page>
