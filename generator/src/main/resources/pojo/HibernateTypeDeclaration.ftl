<#if !pojo.isComponent()>
@${pojo.importType("org.hibernate.annotations.DynamicInsert")}
@${pojo.importType("org.hibernate.annotations.DynamicUpdate")}
</#if>