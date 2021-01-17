package org.hibernate.tool.maven;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;

import java.io.File;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.hibernate.tool.api.export.Exporter;
import org.hibernate.tool.api.export.ExporterConstants;
import org.hibernate.tool.api.export.ExporterFactory;
import org.hibernate.tool.api.export.NextExporterType;
import org.hibernate.tool.api.metadata.MetadataDescriptor;

@Mojo(name = "hbm2entity", defaultPhase = GENERATE_SOURCES)
public class GenerateEntityMojo extends AbstractGenerationMojo {

    /**
     * The directory into which the DAOs will be generated.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/")
    private File outputDirectory;

    /**
     * Code will contain EJB 3 features, e.g. using annotations from javax.persistence
     * and org.hibernate.annotations.
     */
    @Parameter(defaultValue = "false")
    private boolean ejb3;

    /**
     * Code will contain JDK 5 constructs such as generics and static imports.
     */
    @Parameter(defaultValue = "false")
    private boolean jdk5;

    /**
     * Base Class of the entity
     */
    @Parameter
    private String baseClass;

    /**
     * A path used for looking up user-edited templates.
     */
    @Parameter
    private String templatePath;

    protected void executeExporter(MetadataDescriptor metadataDescriptor) {
        Exporter exporter = ExporterFactory.createExporter(NextExporterType.ENTITY.className());
        exporter.getProperties().put(ExporterConstants.METADATA_DESCRIPTOR, metadataDescriptor);
        exporter.getProperties().put(ExporterConstants.DESTINATION_FOLDER, outputDirectory);
        if (templatePath != null) {
            getLog().info("Setting template path to: " + templatePath);
            exporter.getProperties().put(ExporterConstants.TEMPLATE_PATH, new String[]{templatePath});
        }
        exporter.getProperties().setProperty("ejb3", String.valueOf(ejb3));
        exporter.getProperties().setProperty("jdk5", String.valueOf(jdk5));
        if (baseClass != null && baseClass.trim().length() > 0) {
            exporter.getProperties().setProperty("baseClass", baseClass.trim());
        }
        getLog().info("Starting Entity export to directory: " + outputDirectory + "...");
        exporter.start();
    }

}
