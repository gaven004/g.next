package org.hibernate.tool.internal.export.controller;

import java.util.Map;

import org.hibernate.tool.internal.export.java.JavaExporter;
import org.hibernate.tool.internal.export.java.POJOClass;

public class ControllerExporter extends JavaExporter {
    private static final String FTL = "controller/Controller.ftl";

    private String sessionFactoryName = "SessionFactory";

    public ControllerExporter() {
        super();
    }

    protected void init() {
        super.init();
        getProperties().put(TEMPLATE_NAME, FTL);
        getProperties().put(FILE_PATTERN, "{package-name}/{class-name}Controller.java");
    }

    protected void exportComponent(Map<String, Object> additionalContext, POJOClass element) {
        // noop - we dont want components
    }

    protected void setupContext() {
        super.setupContext();
    }

    public String getName() {
        return "hbm2controller";
    }


}
