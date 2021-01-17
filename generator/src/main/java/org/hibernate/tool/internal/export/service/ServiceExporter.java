package org.hibernate.tool.internal.export.service;

import java.util.Map;

import org.hibernate.tool.internal.export.java.JavaExporter;
import org.hibernate.tool.internal.export.java.POJOClass;

public class ServiceExporter extends JavaExporter {
    private static final String FTL = "service/Service.ftl";

    private String sessionFactoryName = "SessionFactory";

    public ServiceExporter() {
        super();
    }

    protected void init() {
        super.init();
        getProperties().put(TEMPLATE_NAME, FTL);
        getProperties().put(FILE_PATTERN, "{package-name}/{class-name}Service.java");
    }

    protected void exportComponent(Map<String, Object> additionalContext, POJOClass element) {
        // noop - we dont want components
    }

    protected void setupContext() {
        super.setupContext();
    }

    public String getName() {
        return "hbm2service";
    }


}
