package org.hibernate.tool.internal.export.repository;

import java.util.Map;

import org.hibernate.tool.internal.export.java.JavaExporter;
import org.hibernate.tool.internal.export.java.POJOClass;

public class RepositoryExporter extends JavaExporter {
    private static final String REPOSITORY_FTL = "repository/Repository.ftl";

    private String sessionFactoryName = "SessionFactory";

    public RepositoryExporter() {
        super();
    }

    protected void init() {
        super.init();
        getProperties().put(TEMPLATE_NAME, REPOSITORY_FTL);
        getProperties().put(FILE_PATTERN, "{package-name}/{class-name}Repository.java");
    }

    protected void exportComponent(Map<String, Object> additionalContext, POJOClass element) {
        // noop - we dont want components
    }

    protected void setupContext() {
        super.setupContext();
    }

    public String getName() {
        return "hbm2repository";
    }


}
