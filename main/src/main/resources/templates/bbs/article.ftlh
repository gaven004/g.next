<#assign sec = JspTaglibs["http://www.springframework.org/security/tags"]/>
<#import "/common/base.ftlh" as base>
<@base.page>
    <style>
        .ui-jqgrid .ui-icon-share {
            color: #69AA46
        }

        .ui-jqgrid .ui-icon-share:before {
            content: "\f064"
        }

        .ui-jqgrid .ui-icon-reply {
            color: #A069C3
        }

        .ui-jqgrid .ui-icon-reply:before {
            content: "\f112"
        }
    </style>

    <div id="mainList">
        <div class="row">
            <div class="col-md-4">
                <@sec.authorize access="@webSecurity.check('/bbs/article', 'POST')">
                    <button id="btnAdd" class="btn btn-success btn-sm" onclick="showDetail()">
                        <i class="ace-icon glyphicon glyphicon-plus"></i> 新建
                    </button>
                </@sec.authorize>
                <@sec.authorize access="@webSecurity.check('/bbs/article', 'DELETE')">
                    <button id="jqgDel" class="btn btn-sm btn-danger" data-url="bbs/article"
                            data-token="${_csrf.token}">
                        <i class="ace-icon  glyphicon glyphicon-trash "></i> 删除
                    </button>
                </@sec.authorize>
            </div>
            <div class="col-md-8">
                <div class="input-group" style="margin-left:auto;">
                    <form id="form" class="form-inline fix-input-size">
                        <select id="columnId" name="columnId" data-placeholder="栏目" class="chosen-select">
                            <option value=""></option>
                            <#if _BBS_COLUMNS ??>
                                <#list _BBS_COLUMNS as c>
                                    <option value="${c.id?c}">${c.name!}</option>
                                </#list>
                            </#if>
                        </select>
                        <input name="title" type="text" placeholder="标题">
                        <select id="status" name="status" data-placeholder="状态" class="chosen-select">
                            <option value=""></option>
                            <#if _ARTICLE_STATUS ??>
                                <#list _ARTICLE_STATUS as s>
                                    <option value="${s!}">${s.getDescription()!}</option>
                                </#list>
                            </#if>
                        </select>
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
    </div>

    <script src="vendors/ckeditor/ckeditor.js"></script>
    <script src="vendors/handlebars/handlebars.js"></script>
    <script src="js/common/handlebars.js"></script>
    <script src="vendors/jquery-validation/jquery.validate.min.js"></script>
    <script src="vendors/jquery-validation/additional-methods.min.js"></script>
    <script src="vendors/jquery-validation/localization/messages_zh.min.js"></script>
    <script src="js/common/jquery.validation.custom.js"></script>

    <script type="text/javascript">
        <@sec.authorize access="@webSecurity.check('bbs/article', 'PATCH')">
        const _edit_permission = true;
        </@sec.authorize>
        <@sec.authorize access="@webSecurity.check('bbs/article', 'DELETE')">
        const _delete_permission = true;
        </@sec.authorize>
        <#if _BBS_COLUMNS ??>
        const _bbs_columns_value = "<#list _BBS_COLUMNS as c>${c.id?c}:${c.name!};</#list>";
        </#if>
    </script>
    <script src="js/bbs/article.js"></script>
</@base.page>

<#include "/common/attachment.ftlh"/>

<script id="detailTemplate" type="text/x-handlebars-template">
    <div id="divDetail">
        <div style="margin-top:15px; padding: 0 10px 5px">
            <button type="button" class="close back">×</button>
        </div>

        <div class="space-10"></div>

        <form id="formDetail" class="form-horizontal" enctype="multipart/form-data">
            <input type="hidden" id="id" name="id" value="{{id}}">
            <div class="form-group">
                <label class="col-sm-2 control-label no-padding-right">所属栏目：</label>
                <div class="col-sm-8">
                    <select name="columnId" class="col-sm-12 chosen-select" data-placeholder="请选择..." required>
                        <option value="">请选择...</option>
                        <#if _BBS_COLUMNS ??>
                            <#list _BBS_COLUMNS as c>
                                <option value="${c.id?c}" {{#eq columnId '${c.id?c}'}}selected='selected'{{/eq}}>${c.name!}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="help-block col-sm-reset inline"></div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label no-padding-right">标题：</label>
                <div class="col-sm-8">
                    <input type="text" class="col-sm-12" name="title" value="{{title}}" required>
                </div>
                <div class="help-block col-sm-reset inline"></div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label no-padding-right">文章内容：</label>
                <div class="col-sm-8">
                    <textarea name="content" id="content" required>{{content}}</textarea>
                </div>
                <div class="help-block col-sm-reset inline"></div>
            </div>
            <div id="divAttachment">
                {{{attachments files readonly}}}
            </div>
            <div class="form-group">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
            </div>
            <hr/>
            <div class="text-center">
                <button id="btnDetailBack" type="button" class="btn btn-default back">
                    <i class="ace-icon fa fa-arrow-left"></i>返回
                </button>
                <button id="btnDetailSave" type="button" class="btn btn-primary">
                    <i class="ace-icon fa fa-floppy-o"></i>保存
                </button>
                {{#eq status 'DRAFT'}}
                <button id="btnDetailPublish" type="button" class="btn btn-success">
                    <i class="ace-icon fa fa-share"></i>发布
                </button>
                {{/eq}}
                {{#eq status 'WITHDRAWAL'}}
                <button id="btnDetailPublish" type="button" class="btn btn-success">
                    <i class="ace-icon fa fa-share"></i>发布
                </button>
                {{/eq}}
                {{#eq status 'PUBLISH'}}
                <button id="btnDetailWithdraw" type="button" class="btn btn-purple">
                    <i class="ace-icon fa fa-reply"></i>回收
                </button>
                {{/eq}}
            </div>
        </form>
    </div>
</script>

<script id="actionButtonTemplate" type="text/x-handlebars-template">
    <div class="ui-widget-content ui-jqgrid-actions" style="white-space: nowrap; height: 23px">
        <div title="编辑所选记录" class="ui-corner-all ui-pg-div" onclick="showDetail('{{rowid}}')">
            <span class="ui-icon ui-icon-pencil"></span>
        </div>
        <div title="删除所选记录" class="ui-corner-all ui-pg-div" onclick="remove('{{rowid}}')">
            <span class="ui-icon ui-icon-trash"></span>
        </div>
        {{#unless published}}
        <div title="发布所选记录" class="ui-corner-all ui-pg-div" onclick="publish('{{rowid}}')">
            <span class="ui-icon ui-icon-share"></span>
        </div>
        {{/unless}}
        {{#if published}}
        <div title="回收所选记录" class="ui-corner-all ui-pg-div" onclick="withdraw('{{rowid}}')">
            <span class="ui-icon ui-icon-reply"></span>
        </div>
        {{/if}}
    </div>
</script>
