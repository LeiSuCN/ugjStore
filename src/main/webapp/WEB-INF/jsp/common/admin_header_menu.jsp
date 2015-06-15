<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta charset="utf-8" />
        <title>优管家 - 打造综合型服务平台</title>

        <meta name="description" content="" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/bootstrap.min.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/font-awesome.min.css" />

        <!-- page specific plugin styles -->

        <!-- text fonts -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-fonts.css" />

        <!-- ace styles -->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace.min.css" />

        <!--[if lte IE 9]>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-part2.min.css" />
        <![endif]-->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-skins.min.css" />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-rtl.min.css" />

        <!--[if lte IE 9]>
          <link rel="stylesheet" href="<%= request.getContextPath() %>/portal/style/css/ace-ie.min.css" />
        <![endif]-->

        <!-- inline styles related to this page -->

        <!-- ace settings handler -->
        <script src="<%= request.getContextPath() %>/portal/js/ace-extra.min.js"></script>

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

        <!--[if lte IE 8]>
        <script src="<%= request.getContextPath() %>/portal/js/html5shiv.js"></script>
        <script src="<%= request.getContextPath() %>/portal/js/respond.min.js"></script>
        <![endif]-->
    </head>

    <body class="no-skin">
        <!-- #section:basics/navbar.layout -->
        <div id="navbar" class="navbar navbar-default">
            <script type="text/javascript">
                try{ace.settings.check('navbar' , 'fixed')}catch(e){}
            </script>

            <div class="navbar-container" id="navbar-container">
                <!-- #section:basics/sidebar.mobile.toggle -->
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <!-- /section:basics/sidebar.mobile.toggle -->
                <div class="navbar-header pull-left">
                    <!-- #section:basics/navbar.layout.brand -->
                    <a href="#" class="navbar-brand">
                        <small>
                            <i class="fa fa-leaf"></i>
                            优管家
                        </small>
                    </a>

                    <!-- /section:basics/navbar.layout.brand -->

                    <!-- #section:basics/navbar.toggle -->

                    <!-- /section:basics/navbar.toggle -->
                </div>

                <!-- #section:basics/navbar.dropdown -->
                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">
                        <li class="grey">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-tasks"></i>
                                <span class="badge badge-grey">0</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-check"></i>
                                    0 个待处理事项
                                </li>
                                <li class="dropdown-footer">
                                    <a href="#">
                                        查看全部事项
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="purple">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                                <span class="badge badge-important">0</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-exclamation-triangle"></i>
                                    0 个系统通知
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        查看全部通知
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="green">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
                                <span class="badge badge-success">0</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-envelope-o"></i>
                                    0 条消息
                                </li>

                                <li class="dropdown-footer">
                                    <a href="inbox.html">
                                        查看全部消息
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <!-- #section:basics/navbar.user_menu -->
                        <li class="light-blue">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="<%= request.getContextPath() %>/portal/style/avatars/avatar2.png" alt="Jason's Photo" />
                                <span class="user-info">
                                    <small></small>
                                </span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-cog"></i>
                                        系统设置
                                    </a>
                                </li>

                                <li>
                                    <a href="<c:url value="/admin/user" />">
                                        <i class="ace-icon fa fa-user"></i>
                                        个人信息
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="<c:url value="/j_spring_security_logout" />">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        退出
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <!-- /section:basics/navbar.user_menu -->
                    </ul>
                </div>

                <!-- /section:basics/navbar.dropdown -->
            </div><!-- /.navbar-container -->
        </div>

        <!-- /section:basics/navbar.layout -->
        <div class="main-container" id="main-container">
            <script type="text/javascript">
                try{ace.settings.check('main-container' , 'fixed')}catch(e){}
            </script>

            <!-- #section:basics/sidebar -->
            <div id="sidebar" class="sidebar                  responsive">
                <script type="text/javascript">
                    try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
                </script>

                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="ace-icon fa fa-signal"></i>
                        </button>

                        <button class="btn btn-info">
                            <i class="ace-icon fa fa-pencil"></i>
                        </button>

                        <!-- #section:basics/sidebar.layout.shortcuts -->
                        <button class="btn btn-warning">
                            <i class="ace-icon fa fa-users"></i>
                        </button>

                        <button class="btn btn-danger">
                            <i class="ace-icon fa fa-cogs"></i>
                        </button>

                        <!-- /section:basics/sidebar.layout.shortcuts -->
                    </div>

                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success"></span>

                        <span class="btn btn-info"></span>

                        <span class="btn btn-warning"></span>

                        <span class="btn btn-danger"></span>
                    </div>
                </div><!-- /.sidebar-shortcuts -->

                <ul class="nav nav-list">

                    <!-- 菜单：订单管理 START -->
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-barcode"></i>
                            <span class="menu-text"> 订单管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>
                        
                        <b class="arrow"></b>

                       <ul class="submenu">

                            <li class="">
                                <a href=" <c:url value="/admin/order/list" />">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    订单查询
                                </a>

                                <b class="arrow"></b>
                            </li>
                            
                            <li class="">
                                <a href="#" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                                      订单统计
                                    <b class="arrow fa fa-angle-down"></b>
                                </a>

                                <b class="arrow"></b>

                                <ul class="submenu">
                                    <li class="">
                                        <a href="<c:url value="/admin/statistics/cainiao" />">
                                            <i class="menu-icon fa fa-laptop"></i>菜鸟驿站
                                        </a>
                                        <b class="arrow"></b>
                                    </li>
                                </ul>
                            </li>
						</ul>
                    </li>
                    <!-- 菜单：订单管理 END -->

                    <!-- 菜单：合同管理 START -->
                    <li class="">
                        <a href="<c:url value="/admin/contract/list" />">
                            <i class="menu-icon fa fa-pencil-square-o"></i>
                            <span class="menu-text"> 合同管理 </span>
                        </a>

                        <b class="arrow"></b>
                    </li>
                    <!-- 菜单：合同管理 END -->

                    <!-- 菜单：门店管理 START -->
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-gavel"></i>
                            <span class="menu-text"> 门店管理 </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">

                            <li class="">
                                <a href=" <c:url value="/admin/store/list" />">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                  	门店查询 
                                </a>
                            </li>
                            <li class="">
                                <a href=" <c:url value="/admin/store/listapply" />">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                  	门店审核 
                                </a>
                            </li>

                        </ul>
                    </li>
                    <!-- 菜单：门店管理 END -->

                </ul><!-- /.nav-list -->

                <!-- #section:basics/sidebar.layout.minimize -->
                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>

                <!-- /section:basics/sidebar.layout.minimize -->
                <script type="text/javascript">
                    try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
                </script>
            </div>

            <!-- /section:basics/sidebar -->
            <div class="main-content">
                <!-- #section:basics/content.breadcrumbs -->
                <div class="breadcrumbs" id="breadcrumbs">
                    <script type="text/javascript">
                        try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                    </script>

                    <ul class="breadcrumb">
                        <li>
                            <i class="ace-icon fa fa-home home-icon"></i>
                            <a href="/">首页</a>
                        </li>
                    </ul><!-- /.breadcrumb -->

                </div>

                <!-- /section:basics/content.breadcrumbs -->
                <div class="page-content">
                    <!-- #section:settings.box -->
                    <div class="ace-settings-container" id="ace-settings-container">
                        <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                            <i class="ace-icon fa fa-cog bigger-150"></i>
                        </div>

                        <div class="ace-settings-box clearfix" id="ace-settings-box">
                            <div class="pull-left width-50">
                                <!-- #section:settings.skins -->
                                <div class="ace-settings-item">
                                    <div class="pull-left">
                                        <select id="skin-colorpicker" class="hide">
                                            <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                            <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                            <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                            <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                                        </select>
                                    </div>
                                    <span>&nbsp; Choose Skin</span>
                                </div>

                                <!-- /section:settings.skins -->

                                <!-- #section:settings.navbar -->
                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
                                    <label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
                                </div>

                                <!-- /section:settings.navbar -->

                                <!-- #section:settings.sidebar -->
                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
                                    <label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
                                </div>

                                <!-- /section:settings.sidebar -->

                                <!-- #section:settings.breadcrumbs -->
                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
                                    <label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
                                </div>

                                <!-- /section:settings.breadcrumbs -->

                                <!-- #section:settings.rtl -->
                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
                                    <label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
                                </div>

                                <!-- /section:settings.rtl -->

                                <!-- #section:settings.container -->
                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
                                    <label class="lbl" for="ace-settings-add-container">
                                        Inside
                                        <b>.container</b>
                                    </label>
                                </div>

                                <!-- /section:settings.container -->
                            </div><!-- /.pull-left -->

                            <div class="pull-left width-50">
                                <!-- #section:basics/sidebar.options -->
                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" />
                                    <label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>
                                </div>

                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" />
                                    <label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>
                                </div>

                                <div class="ace-settings-item">
                                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" />
                                    <label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>
                                </div>

                                <!-- /section:basics/sidebar.options -->
                            </div><!-- /.pull-left -->
                        </div><!-- /.ace-settings-box -->
                    </div><!-- /.ace-settings-container -->
                    <!-- /section:settings.box -->