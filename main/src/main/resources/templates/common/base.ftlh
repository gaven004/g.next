<#macro page useJQGrid=true>
<!DOCTYPE html>
<#import "/spring.ftl" as spring />
<#assign sec = JspTaglibs["http://www.springframework.org/security/tags"]>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
    <base href="<@spring.url '/'/>"/>
    <title>G.Next Admin</title>
    <!-- include common vendor stylesheets & fontawesome -->
    <link rel="stylesheet" type="text/css" href="vendors/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="vendors/fontawesome/css/font-awesome.min.css">
    <!-- include fonts -->
    <link rel="stylesheet" type="text/css" href="vendors/opensans/css">
    <#if useJQGrid>
    <!-- include jqgrid -->
    <link rel="stylesheet" type="text/css" href="vendors/jqgrid/css/ui.jqgrid.css">
    </#if>
    <!-- ace.css -->
    <link rel="stylesheet" type="text/css" href="vendors/ace/css/ace.min.css">
    <link rel="stylesheet" type="text/css" href="css/next.css">
    <!-- favicon -->
    <link rel="icon" type="image/png" href="img/favicon.png"/>
</head>
<body class="no-skin">

<!-- include common vendor scripts -->
<script src="vendors/jquery/js/jquery-3.6.1.min.js"></script>
<script src="vendors/jquery/js/jquery.cookie.min.js"></script>
<script src="vendors/bootstrap/js/bootstrap.js"></script>
<script src="vendors/bootbox/bootbox.js"></script>

<!-- include ace.js -->
<script src="vendors/ace/js/ace-elements.min.js"></script>
<script src="vendors/ace/js/ace.min.js"></script>

<div id="navbar" class="navbar navbar-default">
    <div class="navbar-container" id="navbar-container">
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <div class="navbar-header pull-left">
            <div class="navbar-brand">
                <small>
                    <i class="fa fa-leaf" style="padding-right: 1rem"></i>
                    G.Next Admin
                </small>
            </div>
        </div>

        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="purple">
                    <a class="dropdown-toggle" href="notice.view">
                        通知公告&nbsp;<i class="ace-icon fa fa-newspaper-o"></i>
                        <i id="new_notice" class="ace-icon fa fa-star orange2 hidden"></i>
                    </a>
                </li>
                <li class="green dropdown-modal" >
                    <a class="dropdown-toggle" href="r?l=oa/message">
                        员工通信&nbsp;<i class="ace-icon fa fa-comments-o icon-animated-vertical"></i>
                        <span id="messageNumber" class="badge badge-success"></span>
                    </a>
                </li>
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="img/user.svg" width="36px" height="36px" alt="" style="border: 0" />
                        <span class="user-info">
                                <small>您好,</small>
                                <@sec.authentication property="principal.nickname" />
                            </span>
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>
                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="admin/user/profile.view">
                                <i class="ace-icon fa fa-user"></i>
                                个人资料
                            </a>
                        </li>
                        <li>
                            <a href="admin/user/change-pwd.view">
                                <i class="ace-icon fa fa-lock"></i>
                                修改密码
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="javascript:;" onclick="document.getElementById('fLogout').submit();">
                                <i class="ace-icon fa fa-power-off"></i>
                                登出
                            </a>
                            <form id="fLogout" name="fLogout" action="logout" method="POST">
                                <@sec.csrfInput />
                            </form>
                        </li>
                    </ul>
                </li>
                <li class="grey">
                    <a href="templates/user_manual.docx" target="_blank" title="使用手册">
                        使用手册&nbsp;<i class="ace-icon fa fa-question-circle"></i>
                    </a>
                </li>
            </ul>
        </div>
    </div><!-- /.navbar-container -->
</div>

<div class="main-container" id="main-container">
    <#include "./sidebar.ftlh"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs" id="breadcrumbs">
                <ul class="breadcrumb" id="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">首页</a>
                    </li>
                </ul><!-- /.breadcrumb -->
            </div>
            <!-- 内容页 -->
            <div class="page-content" style="padding-top:12px;padding-bottom:12px">
            <#nested>
            </div>
        </div>
    </div>

    <div class="footer">
        <div class="footer-inner">
            <div class="footer-content">
        <span class="bigger-120">
            <span class="blue">G. Company</span>
            &nbsp; &copy; 2022
        </span>
            </div>
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

<#if useJQGrid>
<script src="vendors/jqgrid/js/jquery.jqgrid.src.js"></script>
<script src="vendors/jqgrid/js/grid.locale-cn.js"></script>
<script src="js/common/jquery.jqgrid.custom.js"></script>
</#if>
<script src="js/common/common.js"></script>
<script src="js/common/menu_status.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    const _csrf = "${_csrf.token}";
</script>

</body>
</html>
</#macro>
