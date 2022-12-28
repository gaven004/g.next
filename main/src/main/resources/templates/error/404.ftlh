<!doctype html>
<#import "/spring.ftl" as spring />
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
    <link rel="stylesheet" type="text/css" href="vendors/opensans/opensans.css">
    <!-- ace.css -->
    <link rel="stylesheet" type="text/css" href="vendors/ace/css/ace.min.css">
    <link rel="stylesheet" type="text/css" href="css/next.css">
    <!-- favicon -->
    <link rel="icon" type="image/png" href="img/favicon.png"/>
</head>

<body>
<div class="body-container">
    <!-- Navbar -->
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
        </div><!-- /.navbar-container -->
    </div>

    <div class="main-container" id="main-container">
        <!-- Main Content -->
        <div class="main-content">
            <!-- Page Content -->
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1"
                     style="height: 80vh; min-height: 540px; display: flex; justify-content: center; align-items: center;">
                    <div class="py-3 px-1 py-lg-4 px-lg-5">
                        <div class="text-center fa-4x">
                            <span class="text-110 text-dark-m3 d-none d-sm-inline">
                                ¯\_(ツ)_/¯
                            </span>
                        </div>
                        <div class="text-center fa-4x orange" style="padding: 0.25em; letter-spacing: 8px">
                            404
                        </div>
                        <div class="text-center">
                            <span class="bigger-150" style="padding-right: 0.5em">
                                O!
                            </span>
                            <span class="bigger-125">
                                没有您访问的页面，是否输入了错误的地址？
                            </span>
                        </div>
                        <hr>
                        <div class="text-center mt-4">
                            <a href="javascript:history.back()"
                               class="btn btn-grey" style="letter-spacing: 4px">
                                <i class="fa fa-arrow-left bigger-125"></i>
                                返回
                            </a>
                            <a href="" class="btn btn-primary" style="letter-spacing: 4px">
                                <i class="fa fa-home bigger-125"></i>
                                首页
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="footer">
            <div class="footer-inner">
                <div class="footer-content">
                    <span class="bigger-120">
                        <span class="blue">G. Company</span> &copy; 2022
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- include common vendor scripts -->
<script src="vendors/jquery/js/jquery-3.6.1.min.js"></script>
<script src="vendors/jquery/js/jquery.cookie.min.js"></script>
<script src="vendors/bootstrap/js/bootstrap.js"></script>
<script src="vendors/bootbox/bootbox.js"></script>

<!-- include ace.js -->
<script src="vendors/ace/js/ace-elements.min.js"></script>
<script src="vendors/ace/js/ace.min.js"></script>

</body>

</html>
