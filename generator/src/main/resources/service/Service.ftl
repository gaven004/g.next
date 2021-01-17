${pojo.getPackageDeclaration()}

<#assign declarationName = pojo.importType(pojo.getDeclarationName())>
/**
 * Service for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */

import org.springframework.stereotype.Service;

import com.g.commons.service.GenericService;
<#if entityPackageName?has_content>
import ${entityPackageName}.*;
</#if>
<#if repositoryPackageName?has_content>
import ${repositoryPackageName}.*;
</#if>

@Service
public class ${declarationName}Service
    extends GenericService<${declarationName + "Repository"}, ${declarationName}, ${pojo.getJavaTypeName(clazz.identifierProperty, jdk5)}> {
}