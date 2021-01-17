<#if pojo.isComponent()>
@${pojo.importType("javax.persistence.Embeddable")}
<#else>
@${pojo.importType("org.hibernate.annotations.DynamicInsert")}
@${pojo.importType("org.hibernate.annotations.DynamicUpdate")}
</#if>