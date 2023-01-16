<#assign sec = JspTaglibs["http://www.springframework.org/security/tags"]/>
<#import "/common/base.ftlh" as base>
<@base.page>
    <style>
        .column > li + li:before {
            font-size: 14px;
            content: "|";
            color: #B2B6BF;
            padding: 0;
            margin: 0 8px 0 0;
            position: relative;
            top: 1px;
        }

        .ace-thumbnails > li {
            float: left;
            overflow: hidden;
            margin: 6px 12px 2px 2px;
            border: 2px solid #dddddd;
        }

        .ui-jqgrid tr.jqgrow {
            cursor: pointer;
        }
    </style>

    <div class="widget-box transparent" id="recent-box">
        <div class="widget-header">
            <ul class="nav nav-tabs" id="mainColumnTab" style="padding-left: 10px; min-height: 34px">
            </ul>
            <div class="tab-content padding-8 no-border" id="subColumnTab" style="min-height: 42px">
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main" style="padding: 16px 0;">
                <div class="row">
                    <div class="col-xs-12 col-md-12">
                        <form id="form">
                            <input type="hidden" name="columnId" id="columnId"/>
                            <input type="hidden" name="status" value="PUBLISH"/>
                            <input type="hidden" name="sort" value="mtime,desc"/>
                            <div class="input-group col-xs-offset-0 col-xs-12 col-md-offset-8 col-md-4"
                                 style="margin-bottom: 10px">
                                <input type="text" name="title" class="form-control search-query" placeholder="搜索...">
                                <span class="input-group-btn">
                                <button type="button" class="btn btn-inverse btn-white" id="btnSearch">
                                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                </button>
                            </span>
                            </div>
                        </form>
                        <div class="widget-grid">
                            <table id="grid-table"></table>
                            <div id="grid-pager"></div>
                        </div>
                    </div>
                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div>

    <script src="vendors/handlebars/handlebars.js"></script>
    <script src="js/common/handlebars.js"></script>
    <script>
        const uid = '[@sec.authentication property="principal.id" /]';
        const readItemKey = 'bbs:' + uid + ':read';
    </script>
    <script src="js/bbs/show.js"></script>
</@base.page>

<div id="article-modal" class="modal fade" tabindex="-1">
</div>

<script id="template_article" type="text/x-handlebars-template">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="center">{{title}}</h3>
                <h6 class="grey" style="margin-top: 24px">
                    <span>作者：{{author}}</span>
                    <span style="padding-left: 24px">发布时间：{{mtime}}</span>
                </h6>
            </div>
            <div class="modal-body">
                {{{content}}}
            </div>
            {{#if attachments}}
            <div class="modal-body">
                <hr/>
                <h6 class="inline bolder">
                    <span class="margin-6">附件：</span>
                </h6>
                {{#each attachments}}
                <span class="margin-6">
                    <a href="sys/attachment/download/{{id}}" target="_blank">{{path}}</a>，
                </span>
                {{/each}}
            </div>
            {{/if}}
            <div class="modal-footer">
                <button class="btn btn-sm btn-danger pull-right" data-dismiss="modal">
                    <i class="ace-icon fa fa-times"></i>
                    关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</script>

<script id="template_submenu" type="text/x-handlebars-template">
    {{#each this}}
    <div id="submenu{{id}}" class="tab-pane fade {{#eq @index 0}}in active{{/eq}}">
        <ul class="breadcrumb column">
            {{#each children}}
            <li>
                <a href="javascript:void(0)" class="bbs-column-menu" column-id="{{id}}">{{name}}</a>
            </li>
            {{/each}}
        </ul><!-- /.breadcrumb -->
    </div>
    {{/each}}
</script>

<script id="template_menu" type="text/x-handlebars-template">
    {{#each this}}
    <li class="{{#eq @index 0}}active{{/eq}}">
        <a data-toggle="tab" href="#submenu{{id}}" class="bbs-column-menu" column-id="{{id}}">
            <i class="{{icon}}"></i>
            {{name}}
        </a>
    </li>
    {{/each}}
</script>
