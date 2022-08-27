<#import "/common/base.ftlh" as base>
<@base.page>
    <div class="row">
        <div class="col-xs-4">
            <button id="jqg_add" class="btn btn-success btn-sm" data-url="rest/admin/user/save" data-token="${_csrf.token}">
                <i class="ace-icon glyphicon glyphicon-plus"></i> 新建
            </button>
            <button id="jqg_del" class="btn btn-sm btn-danger" data-url="rest/admin/user/del_m" data-token="${_csrf.token}">
                <i class="ace-icon  glyphicon glyphicon-trash "></i> 删除
            </button>
        </div>
        <div class="col-xs-8 align-right">
            <div class="input-group">
                <form id="form" class="form-inline">
                    <input name="username" type="text" class="input-elm" placeholder="用户名">
                    <input name="account" type="text" class="input-elm" placeholder="账号">
                    <input name="email" type="text" class="input-elm" placeholder="Email">
                    <button class="btn btn-sm btn-info" type="button" id="btnSearch">
                        <i class="ace-icon glyphicon glyphicon-search"></i> 查找
                    </button>
                    <button class="btn btn-sm" type="button" id="btnReset">
                        <i class="ace-icon glyphicon glyphicon-refresh"></i> 重置
                    </button>
                </form>
            </div>
        </div>
    </div>

</@base.page>
