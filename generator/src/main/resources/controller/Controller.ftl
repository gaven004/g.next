${pojo.getPackageDeclaration()}

<#assign declarationName = pojo.importType(pojo.getDeclarationName())>
/**
 * Controller for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g.commons.web.GenericController;
<#if entityPackageName?has_content>
import ${entityPackageName}.*;
</#if>
<#if repositoryPackageName?has_content>
import ${repositoryPackageName}.*;
</#if>
<#if servicePackageName?has_content>
import ${servicePackageName}.*;
</#if>

@RestController
@RequestMapping("${(requestMapping!) + declarationName}")
public class ${declarationName}Controller
    extends GenericController<${declarationName + "Service"}, ${declarationName + "Repository"}, ${declarationName}, ${pojo.getJavaTypeName(clazz.identifierProperty, jdk5)}> {
}