<!doctype html>
<#import "/spring.ftl" as spring />
<#assign security = JspTaglibs["http://www.springframework.org/security/tags"]>
<html lang="zh">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
    <base href="<@spring.url '/'/>"/>

    <title>Login - G.Next Admin</title>

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

    <!-- "Login" page styles, specific to this page for demo only -->
    <style>
        .body-container {
            background-image: linear-gradient(#6baace, #264783);
            background-attachment: fixed;
            background-repeat: no-repeat;
        }

        .carousel-item > div {
            height: 100%;
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
        }

        /* these rules are used to make sure in mobile devices, tab panes are not all the same height (for example 'forgot' pane is not as tall as 'signup' pane) */

        @media (max-width: 1199.98px) {
            .tab-sliding .tab-pane:not(.active) {
                max-height: 0 !important;
            }

            .tab-sliding .tab-pane.active {
                min-height: 80vh;
                max-height: none !important;
            }
        }
    </style>
</head>

<body>
<div class="body-container">

    <div class="main-container container bgc-transparent">

        <div class="main-content minh-100 justify-content-center">
            <div class="p-2 p-md-4">
                <div class="row" id="row-1">
                    <div class="col-12 col-xl-10 offset-xl-1 bgc-white shadow radius-1 overflow-hidden">

                        <div class="row" id="row-2">

                            <div id="id-col-intro" class="col-lg-5 d-none d-lg-flex border-r-1 brc-default-l3 px-0">
                                <div class="carousel-item active minw-100 h-100">
                                    <div style="background-image: url(/img/login-bg-1.svg);"
                                         class="px-3 bgc-blue-l4 d-flex flex-column align-items-center justify-content-center">
                                        <div class="mt-5 mb-2">
                                            <i class="fa fa-leaf text-success-m2 fa-3x"></i>
                                        </div>

                                        <h2 class="text-primary-d1">
                                            G. <span class="text-80 text-dark-l1">Next Application</span>
                                        </h2>

                                        <div class="mt-5 mx-4 text-dark-tp3">
                                            <span class="text-secondary-m1">
                                               新一代业务管理系统，由心创造，不断进步！
                                            </span>
                                            <hr class="mb-1 brc-black-tp10"/>
                                        </div>

                                        <div class="w-50 h-75"></div>

                                        <div class="mt-auto mb-4 text-dark-tp2">
                                            G Company &copy; 2021
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="id-col-main" class="col-12 col-lg-7 py-lg-5 bgc-white px-0">
                                <div class="tab-content tab-sliding border-0 p-0" data-swipe="right">
                                    <div class="tab-pane active show mh-100 px-3 px-lg-0 pb-3" id="id-tab-login">
                                        <!-- show this in desktop -->
                                        <div class="d-none d-lg-block col-md-6 offset-md-3 mt-lg-4 px-0">
                                            <h4 class="text-dark-tp4 border-b-1 brc-secondary-l2 pb-1 text-130">
                                                <i class="fa fa-coffee text-orange-m1 mr-1"></i>
                                                欢 迎
                                            </h4>
                                        </div>

                                        <!-- show this in mobile device -->
                                        <div class="d-lg-none text-secondary-m1 my-4 text-center">
                                            <div>
                                                <i class="fa fa-leaf text-success-m2 text-200 mb-4"></i>
                                            </div>
                                            <h1 class="text-170">
                                                <span class="text-blue-d1">
                                                    G. <span class="text-80 text-dark-tp3">Next Application</span>
                                                </span>
                                            </h1>
                                            欢 迎
                                        </div>


                                        <form autocomplete="off" class="form-row mt-4">

                                            <@security.csrfInput />

                                            <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3">
                                                <div class="d-flex align-items-center input-floating-label text-blue brc-blue-m2">
                                                    <input placeholder="账号" type="text"
                                                           class="form-control pr-4 shadow-none"
                                                           id="id-login-username"/>
                                                    <i class="fa fa-user text-grey-m2 ml-n4"></i>
                                                    <label class="floating-label text-grey-l1 ml-n3"
                                                           for="id-login-username">
                                                        账号
                                                    </label>
                                                </div>
                                            </div>


                                            <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-2 mt-md-1">
                                                <div class="d-flex align-items-center input-floating-label text-blue brc-blue-m2">
                                                    <input placeholder="密码" type="password"
                                                           class="form-control pr-4 shadow-none"
                                                           id="id-login-password"/>
                                                    <i class="fa fa-key text-grey-m2 ml-n4"></i>
                                                    <label class="floating-label text-grey-l1 ml-n3"
                                                           for="id-login-password">
                                                        密码
                                                    </label>
                                                </div>
                                            </div>


                                            <div class="col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 text-right text-md-right mt-n2 mb-2">
                                                <a href="#" class="text-primary-m1 text-95"
                                                   data-toggle="tab" data-target="#id-tab-forgot">
                                                    忘记密码?
                                                </a>
                                            </div>


                                            <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3">
                                                <label class="d-inline-block mt-3 mb-0 text-dark-l1 text-95">
                                                    <input type="checkbox" class="mr-1" id="id-remember-me"/>
                                                    保持登录状态
                                                </label>

                                                <button type="button"
                                                        class="btn btn-primary btn-block px-4 btn-bold mt-2 mb-4">
                                                    登 录
                                                </button>
                                            </div>
                                        </form>
                                    </div>

                                    <div class="tab-pane mh-100 px-3 px-lg-0 pb-3" id="id-tab-forgot"
                                         data-swipe-prev="#id-tab-login">
                                        <div class="position-tl ml-3 mt-2">
                                            <a href="#"
                                               class="btn btn-light-default btn-h-light-default btn-a-light-default btn-bgc-tp"
                                               data-toggle="tab" data-target="#id-tab-login">
                                                <i class="fa fa-arrow-left"></i>
                                            </a>
                                        </div>


                                        <div class="col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-5 px-0">
                                            <h4 class="pt-4 pt-md-0 text-dark-tp4 border-b-1 brc-grey-l2 pb-1 text-130">
                                                <i class="fa fa-key text-brown-m1 mr-1"></i>
                                                重置密码
                                            </h4>
                                        </div>


                                        <form autocomplete="off" class="form-row mt-4">
                                            <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3">
                                                <label class="text-secondary-d3 mb-3 text-95">
                                                    请输入您绑定的邮箱，我们将发送新的密码到您的邮箱：
                                                </label>
                                                <div class="d-flex align-items-center">
                                                    <input type="email"
                                                           class="form-control pr-4 shadow-none"
                                                           id="id-recover-email" placeholder="Email"/>
                                                    <i class="fa fa-envelope text-grey-m2 ml-n4"></i>
                                                </div>
                                            </div>

                                            <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-1">
                                                <button type="button"
                                                        class="btn btn-orange btn-block px-4 btn-bold mt-2 mb-4">
                                                    发 送
                                                </button>
                                            </div>
                                        </form>


                                        <div class="form-row w-100">
                                            <div class="col-12 col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 d-flex flex-column align-items-center justify-content-center">

                                                <hr class="brc-default-l2 mt-0 mb-2 w-100"/>

                                                <div class="p-0 px-md-2 text-dark-tp4 my-3">
                                                    <a class="text-blue-d1 text-600 btn-text-slide-x" data-toggle="tab"
                                                       data-target="#id-tab-login" href="#">
                                                        <i class="btn-text-2 fa fa-arrow-left text-110 align-text-bottom mr-2"></i>
                                                        返回登录
                                                    </a>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div><!-- .tab-content -->
                            </div>
                        </div><!-- /.row -->
                    </div><!-- /.col -->
                </div><!-- /.row -->

                <div class="d-lg-none my-3 text-white-tp1 text-center">
                    <i class="fa fa-leaf text-success-l3 mr-1 text-110"></i> G Company &copy; 2021
                </div>
            </div>
        </div>
    </div>
</div>

<!-- include common vendor scripts used in demo pages -->
<script src="./vendors/jquery/jquery.min.js"></script>
<script src="./vendors/popper/popper.min.js"></script>
<script src="./vendors/bootstrap/bootstrap.min.js"></script>

<!-- include ace.js -->
<script src="./vendors/ace/ace.min.js"></script>

<script>
    jQuery(function($) {
        // because "Login Here" and "Signup now" links are not inside a "UL" or ".nav", they preserve "active" class
        // and we should remove that, to be able to move between tab panes
        $('a[data-toggle="tab"]')
            .on('click', function() {
                $('a[data-toggle="tab"]').removeClass('active')
            })
    })
</script>
</body>

</html>