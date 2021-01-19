<#assign classbody>
    <#include "PojoTypeDeclaration.ftl"/> {

    <#if !pojo.isInterface()>
        <#include "PojoFields.ftl"/>

        <#include "PojoConstructors.ftl"/>

        <#include "PojoPropertyAccessors.ftl"/>

        <#include "AbstractEntityImplement.ftl"/>

        <#include "PojoToString.ftl"/>

        <#include "PojoEqualsHashcode.ftl"/>

    <#else>
        <#include "PojoInterfacePropertyAccessors.ftl"/>

    </#if>
    <#include "PojoExtraClassCode.ftl"/>

    }
</#assign>

// Generated ${date} by Hibernate Tools ${version}

${pojo.getPackageDeclaration()}

${pojo.generateImports()}
${classbody}

