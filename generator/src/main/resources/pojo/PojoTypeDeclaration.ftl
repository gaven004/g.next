/**
${pojo.getClassJavaDoc(pojo.getDeclarationName() + " generated by hbm2java", 0)}
 */
<#include "Ejb3TypeDeclaration.ftl"/>
<#include "HibernateTypeDeclaration.ftl"/>
${pojo.getClassModifiers()} ${pojo.getDeclarationType()} ${pojo.getDeclarationName()} <#if pojo.getExtendsDeclaration()?has_content>${pojo.getExtendsDeclaration() + ","}<#else>extends</#if> ${pojo.importType("com.g.commons.model.AbstractEntity")} ${pojo.getImplementsDeclaration()}