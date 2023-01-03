<#import "/common/base.ftlh" as base>
<#import "/common/common.ftlh" as common>
<#import "/spring.ftl" as spring />

<#assign frm=JspTaglibs["http://www.springframework.org/tags/form"]/>
<@base.page>
    <div class="page-header">
        <h1>
            系统管理
            <small>
                <i class="ace-icon fa fa-angle-double-right"></i>
                &nbsp;&nbsp;修改密码
            </small>
        </h1>
    </div><!-- /.page-header -->

    <@common.showMsg/>

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <@frm.form id="frmUser" name="frmUser" action="sys/users/save-pwd" method="POST" modelAttribute="User" cssClass="form-horizontal">
                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="oldpwd">原密码：</label>
                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input id="oldpwd" name="oldpwd" class="width-100" title="原密码" type="password" maxlength="50"/>
                        </div>
                    </div>
                    <div class="help-block col-xs-12 col-sm-reset inline"></div>
                </div>

                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="newpwd">新密码：</label>
                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input id="newpwd" name="newpwd" class="width-100" title="新密码" type="password" maxlength="50"/>
                        </div>
                    </div>
                    <div class="help-block col-xs-12 col-sm-reset inline"></div>
                </div>

                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="newpwdcnfm">确认新密码：</label>
                    <div class="col-xs-12 col-sm-4">
                        <div class="clearfix">
                            <input id="newpwdcnfm" name="newpwdcnfm" class="width-100" title="确认新密码" type="password" maxlength="50"/>
                        </div>
                    </div>
                    <div class="help-block col-xs-12 col-sm-reset inline"></div>
                </div>

                <div class="space-2"></div>

                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn btn-info" type="submit">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            保 存
                        </button>

                        &nbsp; &nbsp; &nbsp;

                        <button class="btn" type="reset" id="btnCancel">
                            <i class="ace-icon fa fa-undo bigger-110"></i>
                            取 消
                        </button>
                    </div>
                </div>
            </@frm.form>

            <!-- PAGE CONTENT ENDS -->
        </div><!-- /.col -->
    </div><!-- /.row -->

    <script src="vendors/jquery-validation/jquery.validate.min.js"></script>
    <script src="vendors/jquery-validation/additional-methods.min.js"></script>
    <script src="vendors/jquery-validation/localization/messages_zh.min.js"></script>
    <script src="js/common/jquery.validation.custom.js"></script>

    <script>
        jQuery(function ($) {
            $("#breadcrumb > li:not(:first-child)").remove();

            // 取消按钮
            $("button#btnCancel").click(function () {
                window.location.href = "<@spring.url '/'/>";
            });

            $('form#frmUser div.form-group div.help-block').each(function (i, e) {
                if ($.trim($(e).text()) != '') {
                    $(e).closest('.form-group').addClass('has-error');
                }
            });

            $('form#frmUser').validate({
                focusInvalid: true,
                rules: {
                    oldpwd: {
                        required: true
                    },
                    newpwd: {
                        required: true,
                        minlength: 6
                    },
                    newpwdcnfm: {
                        required: true,
                        minlength: 6,
                        equalTo: "#newpwd"
                    }
                },

                messages: {
                    oldpwd: "请输入原密码",
                    newpwd: {
                        required: "请输入新密码",
                        minlength: "密码至少为6位"
                    },
                    newpwdcnfm: {
                        required: "请再次输入新密码",
                        minlength: "密码至少为6位",
                        equalTo: "两次输入的密码必须相同"
                    }
                },

                highlight: function (e) {
                    $(e).closest('.form-group').addClass('has-error');
                },

                success: function (e) {
                    $(e).closest('.form-group').removeClass('has-error');
                    $(e).closest('.form-group').find('div.help-block').html('');
                },

                errorPlacement: function (error, element) {
                    var block = $(element).closest('.form-group').find('div.help-block');
                    block.html('');
                    error.appendTo(block);
                }
            });
        })
    </script>
</@base.page>
