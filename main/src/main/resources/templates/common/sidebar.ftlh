<#macro menu>
    <div id="sidebar" class="sidebar responsive">
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'fixed')
            } catch (e) {
            }
        </script>
        <ul class="nav nav-list">
            <#nested>
        </ul>
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
               data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'collapsed')
            } catch (e) {
            }
        </script>
    </div>
</#macro>

<!-- 顶层菜单，可以设置icon -->
<#macro menuTop name="菜单" icon="" url="" authority="">
    <@sec.authorize access="@webSecurity.check('" + authority + "')">
        <li class="">
            <#if url != "">
                <a href="${url}" onclick="MENU.save(this)">
                    <i class="menu-icon ${icon}"></i>
                    <span class="menu-text"> ${name} </span>
                </a>
                <b class="arrow"></b>
            <#else>
                <a href="javascript:void(0)" class="dropdown-toggle">
                    <#if icon != "">
                        <i class="menu-icon ${icon}"></i>
                    </#if>
                    <span class="menu-text"> ${name} </span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    <#nested>
                </ul>
            </#if>
        </li>
    </@sec.authorize>
</#macro>

<!-- 子菜单，可以嵌套 -->
<#macro menuSub name="菜单" authority="">
    <@sec.authorize access="@webSecurity.check('" + authority + "')">
        <li class="">
            <a href="javascript:void(0)" class="dropdown-toggle">
                <i class="menu-icon fa fa-caret-right"></i>
                ${name}
                <b class="arrow fa fa-angle-down"></b>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <#nested>
            </ul>
        </li>
    </@sec.authorize>
</#macro>

<!-- 最底层菜单，不可以再嵌套 -->
<#macro menuItem url="javascript:void(0)">
    <#if url != "javascript:void(0)">
        <@sec.authorize access="@webSecurity.check('" + url + "')">
            <li class="">
                <a href="${url}" onclick="MENU.save(this)">
                    <i class="menu-icon fa fa-caret-right"></i>
                    <#nested>
                </a>
                <b class="arrow"></b>
            </li>
        </@sec.authorize>
    <#else>
        <li class="">
            <a href="${url}" onclick="MENU.save(this)">
                <i class="menu-icon fa fa-caret-right"></i>
                <#nested>
            </a>
            <b class="arrow"></b>
        </li>
    </#if>
</#macro>

<@menu>
    <@menuTop name="Dashboard" icon="fa fa-tachometer" url="blank" authority="/blank" />
    <@menuTop name="基础资料管理" icon="fa fa-cogs" authority="/view/sys">
        <@menuItem url="/view/sys/prop/category">参数类型</@menuItem>
        <@menuItem url="/view/sys/prop/property">系统参数</@menuItem>
        <@menuItem url="/view/sys/sec/action">系统功能</@menuItem>
        <@menuItem url="/view/sys/sec/role">角色管理</@menuItem>
        <@menuItem url="/view/sys/sec/user">用户管理</@menuItem>
    </@menuTop>
    <@menuTop name="通知公告" icon="fa fa-clipboard" authority="/view/bbs">
        <@menuItem url="/view/bbs/column">栏目管理</@menuItem>
        <@menuItem url="/view/bbs/article">文章编辑</@menuItem>
    </@menuTop>
</@menu>
