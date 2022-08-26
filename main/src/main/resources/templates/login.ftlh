<!DOCTYPE html>
<#import "/spring.ftl" as spring />
<#assign sec = JspTaglibs["http://www.springframework.org/security/tags"]>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
    <base href="<@spring.url '/'/>"/>

    <title>Login - G.Next Admin</title>

    <!-- include common vendor stylesheets & fontawesome -->
    <link rel="stylesheet" type="text/css" href="./vendors/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/font-awesome.min.css">

    <!-- include fonts -->
    <link rel="stylesheet" type="text/css" href="./vendors/opensans/css">

    <!-- ace.css -->
    <link rel="stylesheet" type="text/css" href="./vendors/ace/css/ace.min.css">

    <!-- favicon -->
    <link rel="icon" type="image/png" href="./img/favicon.png"/>
</head>

<body class="login-layout">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1" style="height: 80vh; display: flex; justify-content: center; align-items: center;">
                <div class="login-container">
                    <div class="space-18"></div>
                    <div class="center">
                        <h1>
                            <i class="ace-icon fa fa-leaf green"></i>
                            <span class="red"> G. </span>
                            <span class="white" id="id-text2">Next</span>
                        </h1>
                        <div class="space-6"></div>
                        <h4 class="blue" id="id-company-text">&copy; G. Company</h4>
                        <div class="space-6"></div>
                    </div>
                    <div class="space-18"></div>
                    <div class="position-relative">
                        <div id="login-box"
                             class="login-box widget-box no-border <#if springMacroRequestContext.requestUri?index_of('/reset-pwd.view') < 0>visible</#if>">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        请输入您的登录信息
                                    </h4>
                                    <div class="space-6"></div>
                                    <#if RequestParameters.error??>
                                        <div class="alert alert-danger">
                                            <i class="ace-icon fa fa-exclamation-triangle"></i>
                                            &nbsp;${Session["SPRING_SECURITY_LAST_EXCEPTION"].message}
                                            <br/>
                                        </div>
                                    </#if>
                                    <#if RequestParameters.logout??>
                                        <div class="alert alert-warning">
                                            <i class="ace-icon fa fa-check"></i>
                                            &nbsp;您已退出系统，继续使用请重新登录
                                            <br/>
                                        </div>
                                    </#if>
                                    <#if RequestParameters.invalidSession??>
                                        <div class="alert alert-warning">
                                            <i class="ace-icon fa fa-check"></i>
                                            &nbsp;您的登录已失效，请重新登录
                                            <br/>
                                        </div>
                                    </#if>
                                    <form name="f" action="form-login" method="POST">
                                        <fieldset>
                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <input type="text" name="username" class="form-control"
                                                           placeholder="账号" value="" required/>
                                                    <i class="ace-icon fa fa-user"></i>
                                                </span>
                                            </label>
                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <input type="password" name="password" class="form-control"
                                                           placeholder="密码" value="" required/>
                                                    <i class="ace-icon fa fa-lock"></i>
                                                </span>
                                            </label>
                                            <div class="space"></div>
                                            <div class="clearfix">
                                                <label class="inline">
                                                    <input type="checkbox" name="remember-me" class="ace"/>
                                                    <span class="lbl"> 保持登录状态</span>
                                                </label>
                                                <button name="submit" type="submit"
                                                        class="width-35 pull-right btn btn-sm btn-primary">
                                                    <i class="ace-icon fa fa-key"></i>
                                                    <span class="bigger-110">登 录</span>
                                                </button>
                                            </div>
                                            <@sec.csrfInput />
                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>
                                </div><!-- /.widget-main -->

                                <div class="toolbar clearfix">
                                    <div>
                                        <a href="#" data-target="#forgot-box" class="forgot-password-link">
                                            <i class="ace-icon fa fa-arrow-left"></i>
                                            忘记密码？
                                        </a>
                                    </div>
                                </div>
                            </div><!-- /.widget-body -->
                        </div><!-- /.login-box -->

                        <div id="forgot-box" class="forgot-box widget-box no-border <#if springMacroRequestContext.requestUri?index_of('/reset-pwd.view') gte 0>visible</#if>">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger">
                                        <i class="ace-icon fa fa-key"></i>
                                        密码找回
                                    </h4>
                                    <div class="space-6"></div>
                                    <p>
                                        请输入您绑定的邮箱
                                    </p>
                                    <#if Request["ERROR_MESSAGE"]??>
                                        <div class="alert alert-danger">
                                            <i class="ace-icon fa fa-exclamation-triangle"></i>
                                            &nbsp;${Request["ERROR_MESSAGE"]}
                                            <br/>
                                        </div>
                                    <#elseif Request["SUCCESS_MESSAGE"]??>
                                        <div class="alert alert-warning">
                                            <i class="ace-icon fa fa-check"></i>
                                            &nbsp;${Request["SUCCESS_MESSAGE"]}
                                            <br/>
                                        </div>
                                    </#if>
                                    <form id="frmResetPwd" name="frmResetPwd" action="admin/user/reset-pwd.view"
                                          onSubmit="return false;" method="POST">
                                        <fieldset>
                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <input name="email" type="email" class="form-control"
                                                           placeholder="Email" required/>
                                                    <i class="ace-icon fa fa-envelope"></i>
                                                </span>
                                            </label>
                                            <div class="clearfix">
                                                <button id="btnResetPwd" type="button"
                                                        class="width-35 pull-right btn btn-sm btn-danger">
                                                    <i class="ace-icon fa fa-lightbulb-o"></i>
                                                    <span class="bigger-110">发送邮件!</span>
                                                </button>
                                            </div>
                                            <@sec.csrfInput />
                                        </fieldset>
                                    </form>
                                </div><!-- /.widget-main -->
                                <div class="toolbar center">
                                    <a href="#" data-target="#login-box" class="back-to-login-link">
                                        返回登录
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div><!-- /.widget-body -->
                        </div><!-- /.forgot-box -->
                    </div><!-- /.position-relative -->
                </div>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.main-content -->
</div><!-- /.main-container -->

<!-- include common vendor scripts -->
<script src="./vendors/jquery/jquery.min.js"></script>
<script src="./vendors/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='./vendors/jquery/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>

<!-- include ace.js -->
<script src="./vendors/ace/js/ace.min.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    jQuery(function ($) {
        $(document).on('click', '.toolbar a[data-target]', function (e) {
            e.preventDefault();
            let target = $(this).data('target');
            $('.widget-box.visible').removeClass('visible');//hide others
            $(target).addClass('visible');//show target
        });

        $('button#btnResetPwd').bind('click', function () {
            let param = $("form#frmResetPwd").serialize();
            comm.ajax2msg("rest/admin/user/reset-pwd", param);
        });
    });

    function resetPwd() {
        let param = $("form#frmResetPwd").serialize();
        comm.ajax2msg("rest/admin/user/reset-pwd", param);
    }
</script>
</body>
</html>
