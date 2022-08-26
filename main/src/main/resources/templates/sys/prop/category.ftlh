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

    <link rel="stylesheet" type="text/css" href="./vendors/jqgrid/css/ui.jqgrid.min.css">

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
                <button type="button" class="btn btn-burger burger-arrowed static collapsed ml-2 d-flex flex-shrink-0 d-xl-none"
                        data-toggle-mobile="sidebar" data-target="#sidebar" aria-controls="sidebar"
                        aria-expanded="false" aria-label="Toggle sidebar">
                    <span class="bars"></span>
                </button><!-- mobile sidebar toggler button -->

                <div class="navbar-brand text-white">
                    <i class="fa fa-leaf"></i>
                    <span class="d-none d-xl-inline-flex ml-2">
                        <span>G.Next</span>
                        <span>App</span>
                    </span>
                    <span class="text-lg d-inline-flex d-xl-none">
                        <span class="ml-2">设置 - 系统参数分类</span>
                    </span>
                </div><!-- /.navbar-brand -->

                <button type="button" class="btn btn-burger mr-2 d-none d-xl-flex" data-toggle="sidebar"
                        data-target="#sidebar" aria-controls="sidebar" aria-expanded="true" aria-label="Toggle sidebar">
                    <span class="bars"></span>
                </button><!-- sidebar toggler button -->
            </div><!-- /.navbar-intro -->

            <div class="navbar-content d-none d-xl-flex">
                <div class="text-lg text-white ml-3">
                    <i class="fa fa-chevron-right"></i>
                    <span class="ml-2">设置 - 系统参数分类</span>
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

                        <li class="nav-item dropdown dropdown-mega bgc-pink-d3">
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

                        <li class="nav-item dd-backdrop dropdown dropdown-mega bgc-success-d3">
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
                                    <span class="nav-user-name"><@security.authentication property="principal.nickname" /></span>
                                </span>
                                <i class="caret fa fa-angle-down d-none d-xl-block"></i>
                                <i class="caret fa fa-angle-left d-block d-lg-none"></i>
                            </a>

                            <div class="dropdown-menu dropdown-caret dropdown-menu-right dropdown-animated brc-primary-m3 py-1">
                                <div class="d-none d-lg-block d-xl-none">
                                    <div class="dropdown-header">
                                        您好，<@security.authentication property="principal.nickname" />
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
                                    <@security.csrfInput />
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

                        <li class="nav-item active open">
                            <a href="" class="nav-link dropdown-toggle collapsed">
                                <i class="nav-icon fa fa-cogs"></i>
                                <span class="nav-text fadeable">
                                    <span>设置</span>
                                </span>
                                <b class="caret fa fa-angle-left rt-n90"></b>
                            </a>

                            <div class="hideable submenu collapse show">
                                <ul class="submenu-inner">
                                    <li class="nav-item active">
                                        <a href="" class="nav-link">
                                            <span class="nav-text">
                                                <span>系统参数</span>
                                            </span>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="" class="nav-link">
                                            <span class="nav-text">
                                                <span>系统功能</span>
                                            </span>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="" class="nav-link">
                                            <span class="nav-text">
                                                <span>系统菜单</span>
                                            </span>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="" class="nav-link">
                                            <span class="nav-text">
                                                <span>用户</span>
                                            </span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <b class="sub-arrow"></b>
                        </li>
                    </ul>
                </div><!-- /.sidebar scroll -->
            </div>
        </div>

        <div role="main" class="main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-12">
                        <table id="grid-table" class="table border-0 table-bordered brc-black-tp11 bgc-white">
                            <!-- grid -->
                        </table>
                        <div id="grid-pager"></div>
                    </div>
                </div>
            </div>

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
<script src="./vendors/jqgrid/js/grid.locale-cn.js"></script>
<script src="./vendors/jqgrid/js/jquery.jqgrid.src.min.js"></script>

<!-- include ace.js -->
<script src="./vendors/ace/ace.min.js"></script>

<!-- page script -->
<script>
    jQuery(function ($) {
        let grid_selector = "#grid-table"
        let pager_selector = "#grid-pager"
        let grid_box = '#gbox_grid-table'


        // resize to fit page size
        let parent_column = $(grid_selector).closest('.col-12')
        $(window)
            .on('resize.jqGrid', function() {
                $(grid_selector).jqGrid('setGridWidth', parent_column.width())
            })

        //resize on sidebar collapse/expand
        $('.sidebar')
            .on('expand.ace.sidebar', function() {
                $(grid_box).hide()
            })
            .on('collapsed.ace.sidebar expanded.ace.sidebar', function() {
                $(grid_selector).jqGrid('setGridWidth', parent_column.width())
                $(grid_box).show()
            })


        // update zIndex for jqGrid modals, to appear above other elements
        $.extend($.jgrid.jqModal, {
            zIndex: 1100
        })


        // set custom classes
        $.jgrid.guiStyles.bootstrap4ace = {
            baseGuiStyle: "bootstrap4",

            gBox: "",
            gView: "",

            hDiv: "border-y-1 brc-grey-l2 mt-n1px bgc-secondary-l3",
            hTable: "text-uppercase text-80 text-dark-tp3",
            colHeaders: "pl-2 pr-1 text-left",


            gridTitle: "bgc-primary-d1 text-white text-125 p-25",
            grid: "table table-hover table-bordered text-dark-m1 text-95 border-0 brc-grey-l2",
            titleButton: "btn btn-primary border-0 mr-2 px-2 w-auto radius-1",

            gridRow: "bgc-h-success-l3",

            actionsButton: "action-btn mx-1 px-2px float-none border-0",

            states: {
                select: "bgc-success-l2 bgc-h-success-l1",
                th: "bgc-yellow-l1 text-blue-d2",
                //hoverTh: "bgc-default-l2 text-default-d3",
                hoverTh: "bgc-yellow-l1 text-dark-m3",

                error: "alert bgc-danger-l3",
                //active: "active",
                //textOfClickable: ""
            },


            //dialogs
            overlay: "modal-backdrop",
            dialog: {
                header: "modal-header bgc-default-l4 text-blue-m1 py-2 px-3",
                window: "modal mw-100",
                document: "modal-dialog mw-none",

                content: "modal-content p-0",
                body: "modal-body px-2 py-25 text-130",
                footer: "modal-footer",

                closeButton: "mr-1 mt-n25 px-2 py-1 w-auto h-auto border-1 brc-h-warning-m1 bgc-h-warning-l1 text-danger radius-round",
                fmButton: "btn btn-sm btn-default",

                viewLabel: "control-label py-2",
                dataField: "form-control my-2 ml-1 w-auto",
                viewCellLabel: "text-right w-4 pr-2",
                viewData: "text-left text-secondary-d2 d-block border-1 brc-grey-l2 p-2 radius-1 my-2 ml-1"
            },

            searchDialog: {
                elem: "form-control w-95",
                operator: "form-control w-95",
                label: "form-control w-95",

                addRuleButton: "btn btn-xs btn-outline-primary radius-round btn-bold px-2 mx-1 text-110",
                addGroupButton: "btn btn-xs btn-primary mx-1 text-110",
                deleteRuleButton: "h-4 px-2 pt-0 text-150 mr-1 btn btn-xs btn-outline-danger border-0",
                deleteGroupButton: "h-4 px-2 pt-0 text-150 mr-1 btn btn-xs btn-outline-danger border-0",
            },

            navButton: "action-btn border-0 text-110 mx-1",
            pager: {
                pager: "py-3 px-1 px-md-2 bgc-primary-l4 border-t-1 brc-default-l2 mt-n1px",
                pagerInput: "form-control form-control-sm text-center d-inline-block",
                pagerSelect: "form-control w-6 px-1",
                pagerButton: "p-0 m-0 border-0 radius-round text-110",
            },

            subgrid: {
                button: "", //don't remove
                row: "bgc-secondary-l4 p-0",
            },

            loading: "alert bgc-primary-l3 brc-primary-m2 text-dark-tp3 text-120 px-4 py-3",
        }

        // use the following icons
        let _pageBtn = "btn w-4 h-4 px-0 mx-2px btn-outline-lightgrey btn-h-outline-primary btn-a-outline-primary radius-round bgc-white"
        $.jgrid.icons.icons4ace = {
            baseIconSet: "fontAwesome5",
            common: "fas",
            actions: {
                edit: "fa-pencil-alt text-blue",
                del: "fa-trash-alt text-danger-m1",
                save: "fa-check text-success",
                cancel: "fa-times text-orange-d2"
            },

            pager: {
                first: "fa-angle-double-left " + _pageBtn,
                prev: "fa-angle-left " + _pageBtn,
                next: "fa-angle-right " + _pageBtn,
                last: "fa-angle-double-right " + _pageBtn
            },

            gridMinimize: {
                visible: "fa-chevron-up",
                hidden: "fa-chevron-down"
            },

            sort: {
                common: "far",
                asc: "fa-caret-up",
                desc: "fa-caret-down"
            },

            form: {
                close: "fa-times my-2px",
                prev: "fa-chevron-left",
                next: "fa-chevron-right",
                save: "fa-check",
                undo: "fa-times",
                del: "fa-trash-alt",
                cancel: "fa-times",

                resizableLtr: "fa-rss fa-rotate-270 text-orange-d1 text-105"
            },
        }


        // initiate plugin
        $(grid_selector).jqGrid({
            //direction: "rtl",

            iconSet: "icons4ace",
            guiStyle: "bootstrap4ace",

            multiselectWidth: 36,

            datatype: "local",

            //sortable: true,// requires jQuery UI Sortable

            // colNames: [' ', 'ID', 'Last Sales', 'Name', 'Stock', 'Ship via', 'Notes'],
            colModel: [{
                resizable: false,
                name: '',
                index: '',
                width: 80,
                fixed: true,
                sortable: false,
                formatter: 'actions',
                formatoptions: {
                    keys: true,
                    //delbutton: false,//disable delete button
                    delOptions: {
                        recreateForm: true,
                        beforeShowForm: style_delete_form
                    },
                    //editformbutton:true,
                    //editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
                }
            },
                {
                    name: '编码',
                    index: 'id',
                    width: 60,
                    sorttype: "int",
                    editable: true
                },
                {
                    name: '分类名称',
                    index: 'name',
                    width: 150,
                    editable: true,
                    editoptions: {
                        size: "20",
                        maxlength: "30"
                    }
                },
                {
                    name: '状态',
                    index: 'status',
                    width: 50,
                    editable: true,
                    edittype: "select",
                    editoptions: {
                        value: "FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"
                    }
                },
                {
                    name: '说明',
                    index: 'note',
                    width: 150,
                    sortable: false,
                    editable: true,
                    edittype: "textarea",
                    editoptions: {
                        rows: "2",
                        cols: "10"
                    }
                }
            ],


            altRows: true,
            altclass: 'bgc-secondary-l5',

            viewrecords: true,
            rowNum: 10,
            rowList: [10, 20, 30],

            pager: pager_selector,
            //toppager: true,

            multiselect: true,
            multiboxonly: true,
            //multikey: "ctrlKey",

            loadComplete: function() {
                let table = this;
                setTimeout(function() {
                    $(window).triggerHandler('resize.jqGrid'); //trigger window resize to make the grid get the correct size
                    enableTooltips(table);
                }, 0);
            },

            editurl: null, //nothing is saved

            //autowidth: true, shrinkToFit: true,
            autowidth: true,
            shrinkToFit: $(window).width() > 600,
            forceFit: true,

            grouping: false,
            groupingView: {
                groupField: ['name'],
                groupDataSorted: true,
                plusicon: 'fa fa-chevron-down px-2 w-auto text-primary-m3 bgc-h-primary-l2 py-1 mx-1 radius-1',
                minusicon: 'fa fa-chevron-up px-2 w-auto text-primary-m3 bgc-h-primary-l2 py-1 mx-1 radius-1'
            },

            // resize grid after pagination
            onPaging: function(pgButton) {
                setTimeout(function() {
                    $(grid_box).hide();
                    $(grid_selector).jqGrid('setGridWidth', parent_column.width());
                    $(grid_box).show();
                }, 0);
            },

        })


        // enable search/filter toolbar
        // jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
        // jQuery(grid_selector).filterToolbar({})


        // navButtons
        $(grid_selector)
            .jqGrid('navGrid', pager_selector, { //navbar options
                add: true,
                addicon: 'fa fa-plus-circle text-purple text-100',

                edit: true,
                editicon: 'fa fa-edit text-blue text-100',

                del: true,
                delicon: 'fa fa-trash text-danger-m1 text-100',

                search: true,
                searchicon: 'fa fa-search text-orange-d1 text-100',

                refresh: true,
                refreshicon: 'fa fa-sync text-success-m1 text-100',

                view: true,
                viewicon: 'fa fa-search-plus text-grey-d1 text-100',
            }, {
                // edit record form
                // closeAfterEdit: true,
                width: 320,
                recreateForm: true,
                beforeShowForm: function(e) {
                    style_edit_form(e[0]);
                }
            }, {
                // new record form
                width: 320,
                closeAfterAdd: true,
                recreateForm: true,
                viewPagerButtons: false,
                beforeShowForm: function(e) {
                    style_edit_form(e[0]);
                }
            }, {
                // delete record form
                recreateForm: true,
                beforeShowForm: function(e) {
                    style_delete_form(e[0]);
                },
                onClick: function(e) {}
            }, {
                // search form
                recreateForm: true,
                afterShowSearch: function(e) {
                    style_search_form(e[0]);
                },
                afterRedraw: function(e) {},
                multipleSearch: true,

                // multipleGroup:true,
                // showQuery: true
            }, {
                // view record form
                recreateForm: true,
                beforeShowForm: function(e) {
                    style_edit_form(e[0]);
                    e[0].querySelector('tr[data-rowpos="1"]').classList.add('d-none');
                }
            })


        // navGrid buttons don't work on touch devices, so trigger them on 'touch'
        $(pager_selector).find('.navtable .action-btn').on('touchend', function() {
            $(this).triggerHandler('click')
        })


        ////////////////////////////////

        // change buttons colors in modals
        function style_edit_form(form) {
            form = $(form)
            // enable datepicker on "sdate" field
            form.find('input[name=sdate]').attr('type', 'date')
            form.find('input[name=stock]').attr('style', 'width: 1.25rem !important;')

            // update buttons classes
            let buttons = form.parent().next().find('.EditButton .fm-button').attr('href', '#') // to disable for Bootstrap's "a:not([href])" style
            buttons.eq(0).removeClass('btn-default').addClass('btn-lighter-success border-2 text-600')
            buttons.eq(1).removeClass('btn-default').addClass('btn-lighter-grey border-2')

            //update next / prev buttons
            form.parent().next()
                .find('.navButton .fm-button')
                .removeClass('btn-default')
                .addClass('px-25 mx-2px btn-outline-secondary btn-h-outline-primary btn-a-outline-primary radius-round')
        }

        function style_delete_form(form) {
            form = $(form)
            let buttons = form.parent().next().find('.EditButton .fm-button').attr('href', '#')
            buttons.eq(0).removeClass('btn-default').addClass('btn-lighter-danger border-2 text-600')
            buttons.eq(1).removeClass('btn-default').addClass('btn-lighter-grey border-2')
        }

        function style_search_form(form) {
            form = $(form)

            let dialog = form.closest('.ui-jqdialog')
            let buttons = dialog.find('.EditTable').addClass('text-white')

            buttons.find('.EditButton a').removeClass('btn-default')
            buttons.find('.EditButton a[id*="_reset"]').addClass('btn-default')
            buttons.find('.EditButton a[id*="_query"]').addClass('btn-grey')
            buttons.find('.EditButton a[id*="_search"]').addClass('btn-primary')
        }


        // enable tooltips
        function enableTooltips(table) {
            $('.navtable .ui-pg-button').tooltip({
                container: 'body',
                trigger: 'hover'
            })
            $(table).find('.ui-pg-div').tooltip({
                container: 'body',
                trigger: 'hover'
            })
        }

    });
</script>

</body>

</html>
