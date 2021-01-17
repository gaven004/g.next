${pojo.getPackageDeclaration()}

<#assign declarationName = pojo.importType(pojo.getDeclarationName())>
/**
 * Repository for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

<#if entityPackageName?has_content>
import ${entityPackageName}.*;
</#if>

public interface ${declarationName}Repository extends
    PagingAndSortingRepository<${declarationName}, ${pojo.getJavaTypeName(clazz.identifierProperty, jdk5)}>, QuerydslPredicateExecutor<${declarationName}> {
}