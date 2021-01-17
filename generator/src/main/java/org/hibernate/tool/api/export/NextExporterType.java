package org.hibernate.tool.api.export;

public enum NextExporterType {

    ENTITY("org.hibernate.tool.internal.export.entity.EntityExporter"),
    REPOSITORY("org.hibernate.tool.internal.export.repository.RepositoryExporter"),
    SERVICE("org.hibernate.tool.internal.export.service.ServiceExporter"),
    CONTROLLER("org.hibernate.tool.internal.export.controller.ControllerExporter");

    private String className;

    NextExporterType(String className) {
        this.className = className;
    }

    public String className() {
        return className;
    }


}
