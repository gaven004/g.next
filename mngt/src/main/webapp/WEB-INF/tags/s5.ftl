<#ftl strip_whitespace=true>
<#--
 * s5.ftl
 *
 * 自定义FreeMarker标签库
 *
 -->

<#--
 * showMsg，信息显示通用标签
 *
 * 后台使用Request.addAttribute("successMsg", "")、Request.addAttribute("errorMsg", "")
 * 传入相关的操作成功或失败信息，前台页面以对应的样式展示给用户
 * 
-->
<#macro showMsg>
    <#if Request["ERROR_MESSAGE"]??>
		<div class="alert alert-danger">
            <button type="button" class="close" data-dismiss="alert">
                <i class="ace-icon fa fa-times"></i>
            </button>
            <p>${Request["ERROR_MESSAGE"]}
            </p>
        </div>
    <#else>
        <#if Request["SUCCESS_MESSAGE"]??>
			<div class="alert alert-block alert-success">
                <button type="button" class="close" data-dismiss="alert">
                    <i class="ace-icon fa fa-times"></i>
                </button>
                <p>${Request["SUCCESS_MESSAGE"]}</p>
            </div>
        </#if>
    </#if>
</#macro>
