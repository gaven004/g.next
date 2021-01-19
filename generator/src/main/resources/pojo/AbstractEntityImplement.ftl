<#if !pojo.isComponent() && pojo.hasIdentifierProperty() && pojo.identifierProperty.name != "id">
    @Override
    @${pojo.importType("javax.persistence.Transient")}
    @${pojo.importType("com.fasterxml.jackson.annotation.JsonIgnore")}
    public ${pojo.getJavaTypeName(clazz.identifierProperty, jdk5)} getId() {
        return this.${c2j.keyWordCheck(clazz.identifierProperty.name)};
    }
</#if>