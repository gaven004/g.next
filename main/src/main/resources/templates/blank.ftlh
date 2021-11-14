<!doctype html>
<#import "/spring.ftl" as spring />
<#assign security = JspTaglibs["http://www.springframework.org/security/tags"]>
<html lang="zh">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
    <base href="<@spring.url '/'/>"/>

    <title>Blank - G.Next Admin</title>

    <!-- include common vendor stylesheets & fontawesome -->
    <link rel="stylesheet" type="text/css" href="./vendors/bootstrap/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/regular.min.css">
    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/brands.min.css">
    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/solid.min.css">

    <!-- include fonts -->
    <link rel="stylesheet" type="text/css" href="./vendors/opensans/css">

    <!-- ace.css -->
    <link rel="stylesheet" type="text/css" href="./vendors/ace/ace.min.css">

    <!-- favicon -->
    <link rel="icon" type="image/png" href="./img/favicon.png"/>
</head>

<body>
<div class="body-container">
    <nav class="navbar navbar-expand-lg navbar-fixed navbar-blue">
        <div class="navbar-inner">

            <div class="navbar-intro justify-content-xl-between">
                <button type="button" class="btn btn-burger burger-arrowed static collapsed ml-2 d-flex d-xl-none"
                        data-toggle-mobile="sidebar" data-target="#sidebar" aria-controls="sidebar"
                        aria-expanded="false" aria-label="Toggle sidebar">
                    <span class="bars"></span>
                </button><!-- mobile sidebar toggler button -->

                <a class="navbar-brand text-white" href="#">
                    <i class="fa fa-leaf"></i>
                    <span class="d-none d-xl-inline-flex ml-2">
                        <span>G.Next</span>
                        <span>App</span>
                    </span>
                    <span class="text-lg d-inline-flex d-xl-none">
                        <span class="ml-2">Blank - 空白页</span>
                    </span>
                </a><!-- /.navbar-brand -->

                <button type="button" class="btn btn-burger mr-2 d-none d-xl-flex" data-toggle="sidebar"
                        data-target="#sidebar" aria-controls="sidebar" aria-expanded="true" aria-label="Toggle sidebar">
                    <span class="bars"></span>
                </button><!-- sidebar toggler button -->
            </div><!-- /.navbar-intro -->

            <div class="navbar-content d-none d-xl-flex">
                <div class="text-lg text-white ml-3">
                    <i class="fa fa-chevron-right"></i>
                    <span class="ml-2">Blank - 空白页</span>
                </div>
            </div><!-- .navbar-content -->

            <!-- mobile #navbarMenu toggler button -->
            <button class="navbar-toggler ml-1 mr-2 px-1" type="button" data-toggle="collapse" data-target="#navbarMenu"
                    aria-controls="navbarMenu" aria-expanded="false" aria-label="Toggle navbar menu">
                <span class="pos-rel">
                    <img width="36" src="/img/user.svg" alt="">
                    <span class="bgc-warning radius-round border-2 brc-white p-1 position-tr mr-n1px mt-n1px"></span>
                </span>
            </button>

            <div class="navbar-menu collapse navbar-collapse navbar-backdrop" id="navbarMenu">

                <div class="navbar-nav">
                    <ul class="nav">

                        <li class="nav-item dropdown dropdown-mega bgc-pink-d1">
                            <a class="nav-link dropdown-toggle pl-lg-3 pr-lg-4" href="#" role="button">
                                <i class="fa fa-bell text-110 icon-animated-bell mr-lg-2"></i>

                                <span class="d-inline-block d-lg-none ml-2">Notifications</span>
                                <!-- show only on mobile -->
                                <span id="id-navbar-badge1"
                                      class="badge badge-sm bgc-warning-d2 text-white radius-round text-85 border-1 brc-white-tp5">3</span>

                                <i class="caret fa fa-angle-left d-block d-lg-none"></i>
                                <div class="dropdown-caret brc-white"></div>
                            </a>
                        </li>

                        <li class="nav-item dd-backdrop dropdown dropdown-mega bgc-success-d1">
                            <a class="nav-link dropdown-toggle pl-lg-3 pr-lg-4" href="#" role="button">
                                <i class="fa fa-flask text-110 icon-animated-vertical mr-lg-1"></i>

                                <span class="d-inline-block d-lg-none ml-2">Tasks</span><!-- show only on mobile -->
                                <span id="id-navbar-badge2" class="badge badge-sm text-95 text-yellow-l4">+2</span>

                                <i class="caret fa fa-angle-left d-block d-lg-none"></i>
                                <div class="dropdown-caret brc-warning-l2"></div>
                            </a>
                        </li>

                        <li class="nav-item dropdown order-first order-lg-last">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"
                               role="button" aria-haspopup="true" aria-expanded="false">
                                <img id="id-navbar-user-image"
                                     class="d-none d-lg-inline-block brc-white-tp1 mr-2 w-6"
                                     src="/img/user.svg" alt="">
                                <span class="d-inline-block d-lg-none d-xl-inline-block">
                                    <span class="text-90" id="id-user-welcome">您好，</span>
                                    <span class="nav-user-name">管理员</span>
                                </span>
                                <i class="caret fa fa-angle-down d-none d-xl-block"></i>
                                <i class="caret fa fa-angle-left d-block d-lg-none"></i>
                            </a>

                            <div class="dropdown-menu dropdown-caret dropdown-menu-right dropdown-animated brc-primary-m3 py-1">
                                <div class="d-none d-lg-block d-xl-none">
                                    <div class="dropdown-header">
                                        Hello, World
                                    </div>
                                    <div class="dropdown-divider"></div>
                                </div>

                                <a class="mt-1 dropdown-item btn btn-outline-grey bgc-h-primary-l3 btn-h-light-primary btn-a-light-primary"
                                   href="#">
                                    <i class="fa fa-user text-primary-m1 text-105 mr-1"></i>
                                    个人信息
                                </a>

                                <a class="dropdown-item btn btn-outline-grey bgc-h-success-l3 btn-h-light-success btn-a-light-success"
                                   href="#" data-toggle="modal" data-target="#id-ace-settings-modal">
                                    <i class="fa fa-cog text-success-m1 text-105 mr-1"></i>
                                    设置
                                </a>

                                <div class="dropdown-divider brc-primary-l2"></div>

                                <a class="dropdown-item btn btn-outline-grey bgc-h-secondary-l3 btn-h-light-secondary btn-a-light-secondary"
                                   href="javascript:;" onclick="document.getElementById('fLogout').submit();">
                                    <i class="fa fa-power-off text-warning-d1 text-105 mr-1"></i>
                                    登出
                                </a>
                                <form id="fLogout" name="fLogout" action="logout" method="POST" class="d-none">
                                    [@sec.csrfInput /]
                                </form>
                            </div>
                        </li><!-- /.nav-item:last -->

                    </ul><!-- /.navbar-nav menu -->
                </div><!-- /.navbar-nav -->

            </div><!-- /#navbarMenu -->

        </div><!-- /.navbar-inner -->
    </nav>
    <div class="main-container bgc-white">
        <div id="sidebar" class="sidebar sidebar-fixed expandable sidebar-light">
            <div class="sidebar-inner">

                <div class="ace-scroll flex-grow-1" data-ace-scroll="{}">

                    <div class="sidebar-section my-2">
                        <!-- the shortcut buttons -->
                        <div class="sidebar-section-item fadeable-left">
                            <div class="fadeinable sidebar-shortcuts-mini">
                                <!-- show this small buttons when collapsed -->
                                <span class="btn btn-success p-0 opacity-1"></span>
                                <span class="btn btn-info p-0 opacity-1"></span>
                                <span class="btn btn-orange p-0 opacity-1"></span>
                                <span class="btn btn-danger p-0 opacity-1"></span>
                            </div>

                            <div class="fadeable">
                                <!-- show this small buttons when not collapsed -->
                                <div class="sub-arrow"></div>
                                <div>
                                    <button class="btn px-25 py-2 text-95 btn-success opacity-1">
                                        <i class="fa fa-signal f-n-hover"></i>
                                    </button>

                                    <button class="btn px-25 py-2 text-95 btn-info opacity-1">
                                        <i class="fa fa-edit f-n-hover"></i>
                                    </button>

                                    <button class="btn px-25 py-2 text-95 btn-orange opacity-1">
                                        <i class="fa fa-users f-n-hover"></i>
                                    </button>

                                    <button class="btn px-25 py-2 text-95 btn-danger opacity-1">
                                        <i class="fa fa-cogs f-n-hover"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <hr class="border-dotted m-0">

                    <ul class="nav has-active-border active-on-right">
                        <li class="nav-item">
                            <a href="dashboard.html" class="nav-link">
                                <i class="nav-icon fa fa-tachometer-alt"></i>
                                <span class="nav-text fadeable"><span>Dashboard</span></span>
                            </a>
                            <b class="sub-arrow"></b>
                        </li>
                    </ul>

                </div><!-- /.sidebar scroll -->

            </div>
        </div>

        <div role="main" class="main-content">
            <div class="page-content container container-plus"></div>

            <footer class="footer d-none d-sm-block">
                <div class="footer-inner bgc-white-tp1">
                    <div class="pt-3 border-none border-t-3 brc-grey-l2 border-double">
                        <span class="text-primary-m1 font-bolder text-120">G.Next</span>
                        <span class="text-grey">Application &copy; 2021</span>
                    </div>
                </div><!-- .footer-inner -->

                <!-- `scroll to top` button inside footer (for example when in boxed layout) -->
                <div class="footer-tools">
                    <a href="#" class="btn-scroll-up btn btn-dark mb-2 mr-2">
                        <i class="fa fa-angle-double-up mx-2px text-95"></i>
                    </a>
                </div>
            </footer>
        </div>
    </div>
</div>

<!-- include common vendor scripts used in demo pages -->
<script src="./vendors/jquery/jquery.min.js"></script>
<script src="./vendors/popper/popper.min.js"></script>
<script src="./vendors/bootstrap/bootstrap.min.js"></script>

<!-- include ace.js -->
<script src="./vendors/ace/ace.min.js"></script>

</body>

</html>