<#assign sec = JspTaglibs["http://www.springframework.org/security/tags"]/>
<#import "/common/base.ftlh" as base>
<@base.page>
    <link rel="stylesheet" type="text/css" href="css/message.css">
    <div class="row">
        <div class="col-xs-12">
            <div class="tabbable">
                <ul id="inbox-tabs" class="inbox-tabs nav nav-tabs padding-16 tab-size-bigger tab-space-1">
                    <li class="li-new-mail pull-right">
                        <a data-toggle="tab" href="#write" data-target="write" class="btn-new-mail">
                        <span class="btn btn-purple no-border">
                            <i class="ace-icon fa fa-envelope bigger-130"></i>
                            <span>写邮件</span>
                        </span>
                        </a>
                    </li><!-- /.li-new-mail -->
                    <li class="active">
                        <a data-toggle="tab" href="#inbox" data-target="inbox">
                            <i class="blue ace-icon fa fa-inbox bigger-130"></i>
                            <span>收件箱</span>
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#sent" data-target="sent">
                            <i class="orange ace-icon fa fa-location-arrow bigger-130"></i>
                            <span>发件箱</span>
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#draft" data-target="draft">
                            <i class="green ace-icon fa fa-pencil bigger-130"></i>
                            <span>草稿箱</span>
                        </a>
                    </li>
                </ul>
                <div class="tab-content no-border no-padding">
                    <div id="inbox" class="tab-pane fade in active">
                        <div class="message-container">
                            <div id="id-message-list-navbar" class="message-navbar clearfix">
                                <div class="message-bar">
                                    <div class="message-infobar" id="id-message-infobar">
                                        <span class="blue bigger-150">收件箱</span>
                                        <span class="grey bigger-110">（2条未读信息）</span>
                                    </div>

                                    <div class="message-toolbar hide">
                                        <button type="button" class="btn btn-xs btn-white btn-primary">
                                            <i class="ace-icon fa fa-mail-reply bigger-125 blue"></i>
                                            <span class="bigger-110">回复</span>
                                        </button>
                                        <button type="button" class="btn btn-xs btn-white btn-primary">
                                            <i class="ace-icon fa fa-mail-forward bigger-125 green"></i>
                                            <span class="bigger-110">转发</span>
                                        </button>
                                        <button type="button" class="btn btn-xs btn-white btn-primary">
                                            <i class="ace-icon fa fa-trash-o bigger-125 orange"></i>
                                            <span class="bigger-110">删除</span>
                                        </button>
                                    </div>
                                </div>

                                <div>
                                    <div class="messagebar-item-left">
                                        <label class="inline middle">
                                            <input type="checkbox" id="id-toggle-all" class="ace">
                                            <span class="lbl"></span>
                                        </label>
                                        &nbsp;
                                        <div class="inline position-relative">
                                            <a href="#" data-toggle="dropdown" class="dropdown-toggle">
                                                <i class="ace-icon fa fa-caret-down bigger-125 middle"></i>
                                            </a>
                                            <ul class="dropdown-menu dropdown-lighter dropdown-100">
                                                <li>
                                                    <a id="id-select-message-all" href="#">全选</a>
                                                </li>
                                                <li>
                                                    <a id="id-select-message-none" href="#">取消</a>
                                                </li>
                                                <li class="divider"></li>
                                                <li>
                                                    <a id="id-select-message-unread" href="#">未读</a>
                                                </li>
                                                <li>
                                                    <a id="id-select-message-read" href="#">已读</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="messagebar-item-right">
                                    </div>

                                    <div class="nav-search minimized">
                                        <form id="inbox-form-search" class="form-search">
                                            <input id="inbox-sort" name="sort" type="hidden"/>
                                            <span class="input-icon">
                                                <input id="inbox-subject" name="subject" type="text" autocomplete="off"
                                                       class="input-small nav-search-input" placeholder="搜索 ...">
                                                <i class="ace-icon fa fa-search nav-search-icon"></i>
                                            </span>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div id="id-message-item-navbar" class="message-navbar clearfix hide">
                                <div class="message-bar">
                                    <div class="message-toolbar">
                                        <button type="button" class="btn btn-xs btn-white btn-primary">
                                            <i class="ace-icon fa fa-mail-reply bigger-125 blue"></i>
                                            <span class="bigger-110">回复</span>
                                        </button>
                                        <button type="button" class="btn btn-xs btn-white btn-primary">
                                            <i class="ace-icon fa fa-mail-forward bigger-125 green"></i>
                                            <span class="bigger-110">转发</span>
                                        </button>
                                        <button type="button" class="btn btn-xs btn-white btn-primary">
                                            <i class="ace-icon fa fa-trash-o bigger-125 orange"></i>
                                            <span class="bigger-110">Delete</span>
                                        </button>
                                    </div>
                                </div>

                                <div>
                                    <div class="messagebar-item-left">
                                        <a href="#" class="btn-back-message-list">
                                            <i class="ace-icon fa fa-arrow-left blue bigger-110 middle"></i>
                                            <b class="bigger-110 middle">返回</b>
                                        </a>
                                    </div>
                                </div>
                            </div>

                            <div class="message-list-container">
                                <div class="message-list row" id="message-list">
                                    <div class="col-xs-12 col-md-12">
                                        <div class="widget-grid">
                                            <table id="inbox-grid-table"></table>
                                            <div id="inbox-grid-pager"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="sent" class="tab-pane fade">
                        <div class="message-container">
                            <div id="id-message-list-navbar" class="message-navbar clearfix">
                                <div class="message-bar">
                                    <div class="message-infobar" id="id-message-infobar">
                                        <span class="blue bigger-150">发件箱</span>
                                    </div>
                                </div>

                                <div>
                                    <div class="messagebar-item-left">
                                        <label class="inline middle">
                                            <input type="checkbox" id="id-toggle-all" class="ace">
                                            <span class="lbl"></span>
                                        </label>
                                        &nbsp;
                                        <div class="inline position-relative">
                                            <a href="#" data-toggle="dropdown" class="dropdown-toggle">
                                                <i class="ace-icon fa fa-caret-down bigger-125 middle"></i>
                                            </a>
                                            <ul class="dropdown-menu dropdown-lighter dropdown-100">
                                                <li>
                                                    <a id="id-select-message-all" href="#">全选</a>
                                                </li>
                                                <li>
                                                    <a id="id-select-message-none" href="#">取消</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="messagebar-item-right">
                                    </div>

                                    <div class="nav-search minimized">
                                        <form id="sent-form-search" class="form-search">
                                            <input id="sent-sort" name="sort" type="hidden"/>
                                            <span class="input-icon">
                                                <input id="sent-subject" name="subject" type="text" autocomplete="off"
                                                       class="input-small nav-search-input" placeholder="搜索 ...">
                                                <i class="ace-icon fa fa-search nav-search-icon"></i>
                                            </span>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="message-list-container">
                                <div class="message-list row" id="message-list">
                                    <div class="col-xs-12 col-md-12">
                                        <div class="widget-grid">
                                            <table id="sent-grid-table"></table>
                                            <div id="sent-grid-pager"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="draft" class="tab-pane fade">
                        <div class="message-container">
                            <div id="id-message-list-navbar" class="message-navbar clearfix">
                                <div class="message-bar">
                                    <div class="message-infobar" id="id-message-infobar">
                                        <span class="blue bigger-150">草稿</span>
                                    </div>
                                </div>

                                <div>
                                    <div class="messagebar-item-left">
                                        <label class="inline middle">
                                            <input type="checkbox" id="id-toggle-all" class="ace">
                                            <span class="lbl"></span>
                                        </label>
                                        &nbsp;
                                        <div class="inline position-relative">
                                            <a href="#" data-toggle="dropdown" class="dropdown-toggle">
                                                <i class="ace-icon fa fa-caret-down bigger-125 middle"></i>
                                            </a>
                                            <ul class="dropdown-menu dropdown-lighter dropdown-100">
                                                <li>
                                                    <a id="id-select-message-all" href="#">全选</a>
                                                </li>
                                                <li>
                                                    <a id="id-select-message-none" href="#">取消</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="messagebar-item-right">
                                    </div>

                                    <div class="nav-search minimized">
                                        <form id="draft-form-search" class="form-search">
                                            <input id="draft-sort" name="sort" type="hidden"/>
                                            <span class="input-icon">
                                                <input id="draft-subject" name="subject" type="text" autocomplete="off"
                                                       class="input-small nav-search-input" placeholder="搜索 ...">
                                                <i class="ace-icon fa fa-search nav-search-icon"></i>
                                            </span>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="message-list-container">
                                <div class="message-list row" id="message-list">
                                    <div class="col-xs-12 col-md-12">
                                        <div class="widget-grid">
                                            <table id="draft-grid-table"></table>
                                            <div id="draft-grid-pager"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="write" class="tab-pane fade">
                        <div class="message-container">
                            <div id="id-message-new-navbar" class="message-navbar clearfix">
                                <div class="message-bar">
                                    <div class="message-toolbar">
                                        <button type="button" id="save-as-draft"
                                                class="btn btn-xs btn-white btn-primary">
                                            <i class="ace-icon fa fa-floppy-o bigger-125"></i>
                                            <span class="bigger-110">存为草稿</span>
                                        </button>
                                    </div>
                                </div>

                                <div>
                                    <div class="messagebar-item-left">
                                        <a href="#" class="btn-back-message-list">
                                            <i class="ace-icon fa fa-arrow-left bigger-110 middle blue"></i>
                                            <b class="middle bigger-110">返回</b>
                                        </a>
                                    </div>

                                    <div class="messagebar-item-right">
                                        <span class="inline btn-send-message">
                                            <button type="button" id="send-mail"
                                                    class="btn btn-sm btn-primary no-border btn-white btn-round">
                                                <span class="bigger-110">发送</span>
                                                <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                                            </button>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="message-list-container">
                                <div class="message-list row" id="message-write">
                                </div>
                            </div>
                        </div>
                    </div>
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
    <script src="js/sys/msg/message.js"></script>
</@base.page>

<#include "/common/attachment.ftlh"/>

<script id="messageFormTemplate" type="text/x-handlebars-template">
    <form id="id-message-form" class="form-horizontal message-form col-xs-12" enctype="multipart/form-data">
        <input type="hidden" id="id" name="id" value="{{id}}">
        <input type="hidden" id="replyTo" name="replyTo" value="{{replyTo}}">
        <div class="form-group">
            <label class="col-sm-2 control-label no-padding-right">收件人：</label>
            <div class="col-sm-8">
                <select id="recipientId" name="recipientId" class="col-sm-12 chosen-select" data-placeholder="请选择..."
                        multiple>
                    <#if _SYS_USERS ??>
                        <#list _SYS_USERS as e>
                            <option value="${e.id?c}" {{#includes recipients '${e.id?c}'}}selected='selected'{{/includes}}>${e.username!}[${e.account!}]</option>
                        </#list>
                    </#if>
                </select>
            </div>
            <div class="help-block col-sm-reset inline"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label no-padding-right">主题：</label>
            <div class="col-sm-8">
                <input type="text" class="col-sm-12" id="subject" name="subject" value="{{subject}}">
            </div>
            <div class="help-block col-sm-reset inline"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label no-padding-right">内容：</label>
            <div class="col-sm-8">
                <textarea name="message" id="message" required>{{message}}</textarea>
            </div>
            <div class="help-block col-sm-reset inline"></div>
        </div>
        <div id="divAttachment">
            {{{attachments files readonly}}}
        </div>
        <div class="form-group">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </div>
    </form>
</script>

<script id="messageContentTemplate" type="text/x-handlebars-template">
    <div class="message-content" id="id-message-content">
    </div>
</script>
